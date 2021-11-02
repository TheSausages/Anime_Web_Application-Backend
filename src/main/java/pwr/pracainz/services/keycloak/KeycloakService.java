package pwr.pracainz.services.keycloak;

import com.mysql.cj.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.configuration.properties.KeycloakClientServerProperties;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.userauthentification.AuthenticationToken;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.exceptions.exceptions.RegistrationException;
import pwr.pracainz.repositories.user.UserRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.i18n.I18nServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Optional;

@Log4j2
@Service
public class KeycloakService implements KeycloakServiceInterface {
	private final WebClient client;
	private final Keycloak keycloak;
	private final UserRepository userRepository;
	private final DTOConversionInterface dtoConversion;
	private final KeycloakClientServerProperties keycloakProperties;
	private final I18nServiceInterface i18nService;

	@Autowired
	KeycloakService(KeycloakClientServerProperties keycloakProperties,
	                @Qualifier("keycloakWebClient") WebClient client,
	                Keycloak keycloak,
	                UserRepository userRepository,
	                DTOConversionInterface dtoConversion,
	                I18nServiceInterface i18nService) {
		this.client = client;
		this.keycloak = keycloak;
		this.userRepository = userRepository;
		this.dtoConversion = dtoConversion;
		this.keycloakProperties = keycloakProperties;
		this.i18nService = i18nService;
	}

	@Override
	public ResponseBodyWithMessageDTO logout(RefreshTokenDTO logoutRequestBody, String accessToken, HttpServletRequest request) {
		if (StringUtils.isEmptyOrWhitespaceOnly(logoutRequestBody.getRefreshToken()) || StringUtils.isEmptyOrWhitespaceOnly(accessToken)) {
			log.warn("Could not log out - missing information!");

			throw new AuthenticationException("The credentials are not of correct structure");
		}

		return client
				.post()
				.uri("/logout")
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					httpHeaders.set("Authorization", accessToken);
				})
				.body(BodyInserters
						.fromFormData("client_id", keycloakProperties.getRealm())
						.with("client_secret", keycloakProperties.getClientSecret())
						.with("refresh_token", logoutRequestBody.getRefreshToken()))
				.retrieve()
				.toBodilessEntity()
				.map(res -> new ResponseBodyWithMessageDTO("Logout was successful!"))
				.doOnSuccess(s -> log.info("Logged Out Successfully"))
				.doOnError(s -> log.info("Logged Out was not successful"))
				.onErrorMap(throwable -> new AuthenticationException("Logout was not successful"))
				.block();
	}

	@Override
	public AuthenticationTokenDTO login(LoginCredentialsDTO credentials, HttpServletRequest request) {
		return client
				.post()
				.uri("/token")
				.headers(httpHeaders -> httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE)))
				.body(BodyInserters
						.fromFormData("client_id", keycloakProperties.getRealm())
						.with("client_secret", keycloakProperties.getClientSecret())
						.with("scope", keycloakProperties.getScope())
						.with("username", credentials.getUsername())
						.with("password", credentials.getPassword())
						.with("grant_type", keycloakProperties.getGrantType().getLogin()))
				.retrieve()
				.bodyToMono(AuthenticationToken.class)
				.map(dtoConversion::convertToDTO)
				.doOnSuccess(s -> log.info("Log In was Successful for Username:" + credentials.getUsername()))
				.doOnError(e -> log.info("Log In was not successful for Username:" + credentials.getUsername()))
				.onErrorMap(throwable -> new AuthenticationException(i18nService.getTranslation("credentials.error", request)))
				.block();
	}

	@Override
	public AuthenticationTokenDTO register(RegistrationBodyDTO registrationBody, HttpServletRequest request) {
		log.info("Attempt registration for user: {}, with email: {}", registrationBody.getUsername(), registrationBody.getEmail());

		if (!registrationBody.getPassword().equals(registrationBody.getMatchingPassword())) {
			throw new AuthenticationException("The Passwords are not matching");
		}

		CredentialRepresentation password = new CredentialRepresentation();
		password.setTemporary(false);
		password.setType(CredentialRepresentation.PASSWORD);
		password.setValue(registrationBody.getPassword());

		UserRepresentation user = new UserRepresentation();
		user.setEnabled(true);
		user.setUsername(registrationBody.getUsername());
		user.setEmail(registrationBody.getEmail());
		user.setCredentials(Collections.singletonList(password));

		Response response = keycloak.realm(keycloakProperties.getMainRealm())
				.users().create(user);

		if (response.getStatus() > 100 && response.getStatus() < 300) {
			log.info("Registration was successful. Attempt login for user: {}", registrationBody.getUsername());

			Optional<UserRepresentation> newUser = keycloak.realm(keycloakProperties.getMainRealm())
					.users().search(registrationBody.getUsername())
					.stream().filter(userRep -> userRep.getEmail().equalsIgnoreCase(registrationBody.getEmail())).findAny();

			if (newUser.isEmpty()) {
				throw new RegistrationException("An error occurred while registration was underway - please contact the administration!");
			}

			userRepository.save(new User(
					newUser.get().getId(), registrationBody.getUsername(), 0, 0, 0, null, null, null, null, null, null
			));

			LoginCredentialsDTO login = new LoginCredentialsDTO(
					registrationBody.getUsername(),
					registrationBody.getPassword()
			);

			return login(login, request);
		}

		if (response.getStatus() == 409) {
			throw new RegistrationException("This username and/or email are already taken!");
		}

		throw new AuthenticationException("Registration was not successful!");
	}

	@Override
	public AuthenticationTokenDTO refreshTokens(RefreshTokenDTO refreshTokenDTO, HttpServletRequest request) {
		return client
				.post()
				.uri("/token")
				.headers(httpHeaders -> httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE)))
				.body(BodyInserters
						.fromFormData("client_id", keycloakProperties.getRealm())
						.with("client_secret", keycloakProperties.getRealm())
						.with("scope", keycloakProperties.getScope())
						.with("refresh_token", refreshTokenDTO.getRefreshToken())
						.with("grant_type", keycloakProperties.getGrantType().getRefresh()))
				.retrieve()
				.bodyToMono(AuthenticationToken.class)
				.map(dtoConversion::convertToDTO)
				.doOnSuccess(s -> log.info("Tokens has been successful refreshed"))
				.doOnError(e -> log.info("Tokens was not refreshed"))
				.onErrorMap(throwable -> new AuthenticationException("Wrong refresh token!"))
				.block();
	}
}
