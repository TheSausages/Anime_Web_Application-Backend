package pwr.pracainz.services.Keycloak;

import com.mysql.cj.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.LogoutRequestBodyDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.Exceptions.Exceptions.AuthenticationException;
import pwr.pracainz.entities.userauthentification.AuthenticationToken;
import pwr.pracainz.services.DTOConversionService;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Log4j2
@Service
public class KeycloakService implements KeycloakServiceInterface {
    private final WebClient client;
    private final Keycloak keycloak;
    private final DTOConversionService dtoConversionService;

    @Autowired
    KeycloakService(Keycloak keycloak, DTOConversionService dtoConversionService) {
        client = WebClient.create("http://localhost:8180/auth/realms/PracaInz/protocol/openid-connect");
        this.keycloak = keycloak;
        this.dtoConversionService = dtoConversionService;
    }

    public ResponseEntity<ResponseBodyWithMessageDTO> logout(LogoutRequestBodyDTO logoutRequestBody, String accessToken) {
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
                        .fromFormData("client_id", "ClientServer")
                        .with("client_secret", "cbf2b2ff-6fc9-442b-a80a-61df84886f00")
                        .with("refresh_token", logoutRequestBody.getRefreshToken()))
                .retrieve()
                .toBodilessEntity()
                .map(res -> ResponseEntity.status(HttpStatus.OK).body(new ResponseBodyWithMessageDTO("Logout was successful!")))
                .doOnSuccess(s -> log.info("Logged Out Successfully"))
                .doOnError(s -> log.info("Logged Out was not successful"))
                .onErrorMap(throwable -> new AuthenticationException("Logout was not successful"))
                .block();
    }

    public ResponseEntity<AuthenticationTokenDTO> login(LoginCredentialsDTO credentials) {
        return client
                .post()
                .uri("/token")
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE)))
                .body(BodyInserters
                        .fromFormData("client_id", "ClientServer")
                        .with("client_secret", "cbf2b2ff-6fc9-442b-a80a-61df84886f00")
                        .with("scope", "openid")
                        .with("username", credentials.getUsername())
                        .with("password", credentials.getPassword())
                        .with("grant_type", "password"))
                .retrieve()
                .bodyToMono(AuthenticationToken.class)
                .map(res -> ResponseEntity.status(HttpStatus.OK).body(dtoConversionService.convertAuthenticationTokenToDTO(res)))
                .doOnSuccess(s -> log.info("Log In was Successful for Username:" + credentials.getUsername()))
                .doOnError(e -> log.info("Log In was not successful for Username:" + credentials.getUsername()))
                .onErrorMap(throwable -> new AuthenticationException("The Credentials are not correct!"))
                .block();
    }

    // TODO: update when front is ready
    public ResponseEntity<ResponseBodyWithMessageDTO> register(RegistrationBodyDTO registrationBody) {
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

        Response response = keycloak.realm("PracaInz").users().create(user);

        if (response.getStatus() > 100 && response.getStatus() < 300) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseBodyWithMessageDTO("Registration was successful!"));
        }

        throw new AuthenticationException("Registration was not successful!");
    }
}
