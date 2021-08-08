package pwr.pracainz.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.LogoutBodyDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.Exceptions.AuthenticationException;
import pwr.pracainz.entities.userauthentification.AuthenticationToken;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Objects;

@Log4j2
@Service
public class KeycloakService {
    private final WebClient client;
    private final Keycloak keycloak;
    private final ObjectMapper mapper;
    private final DTOConversionService dtoConversionService;

    @Autowired
    KeycloakService(Keycloak keycloak, DTOConversionService dtoConversionService, ObjectMapper mapper) {
        client = WebClient.create("http://localhost:8180/auth");
        this.keycloak = keycloak;
        this.dtoConversionService = dtoConversionService;
        this.mapper = mapper;
    }

    public ResponseEntity<ObjectNode> logout(LogoutBodyDTO logoutBodyDTO, String accessToken) {
        String refreshToken = logoutBodyDTO.getRefreshToken();

        if (StringUtils.isEmptyOrWhitespaceOnly(refreshToken) || StringUtils.isEmptyOrWhitespaceOnly(accessToken)) {
            log.warn("Could not log out - missing information!");

            throw new AuthenticationException("The credentials are not of correct structure");
        }

        ResponseEntity<Void> response = client
                .post()
                .uri("/realms/PracaInz/protocol/openid-connect/logout")
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    httpHeaders.set("Authorization", accessToken);
                })
                .body(BodyInserters
                        .fromFormData("client_id", "ClientServer")
                        .with("client_secret", "cbf2b2ff-6fc9-442b-a80a-61df84886f00")
                        .with("refresh_token", refreshToken))
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(s -> log.info("Logged Out Successfully"))
                .doOnError(s -> log.info("Logged Out was not successful"))
                .block();

        if (Objects.requireNonNull(response).getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(response.getStatusCode()).body(getMessage("Logout was successful"));
        }

        return ResponseEntity.status(response.getStatusCode()).body(getMessage("Logout was not successful"));
    }

    public ResponseEntity<AuthenticationTokenDTO> login(LoginCredentialsDTO credentials) {
        return client
                .post()
                .uri("/realms/PracaInz/protocol/openid-connect/token")
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

    public Response register(RegistrationBodyDTO registrationBodyDTO) {
        if (!registrationBodyDTO.getPassword().equals(registrationBodyDTO.getMatchingPassword())) {
            throw new AuthenticationException("The Passwords are not matching");
        }

        CredentialRepresentation password = new CredentialRepresentation();
        password.setTemporary(false);
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue(registrationBodyDTO.getPassword());

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(registrationBodyDTO.getUsername());
        user.setEmail(registrationBodyDTO.getEmail());
        user.setCredentials(Collections.singletonList(password));

        return keycloak.realm("PracaInz").users().create(user);
    }

    private ObjectNode getMessage(String message) {
        return mapper.createObjectNode().put("message", message);
    }
}
