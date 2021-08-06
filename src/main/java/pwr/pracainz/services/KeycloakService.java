package pwr.pracainz.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
import pwr.pracainz.DTO.LoginCredentials;
import pwr.pracainz.DTO.LogoutBody;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Objects;

@Log4j2
@Service
public class KeycloakService {
    private final WebClient client;
    private final Gson gson;
    private final Keycloak keycloak;

    @Autowired
    KeycloakService(Keycloak keycloak) {
        client = WebClient.create("http://localhost:8180/auth");
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        this.keycloak = keycloak;
    }

    public ResponseEntity<JsonObject> logout(LogoutBody logoutBody, String accessToken) {
        String refreshToken = logoutBody.getRefreshToken();

        if (StringUtils.isEmptyOrWhitespaceOnly(refreshToken) || StringUtils.isEmptyOrWhitespaceOnly(accessToken)) {
            log.warn("Could not log out - missing information!");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("The credentials are not of correct structure"));
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

    public ResponseEntity<JsonObject> login(LoginCredentials credentials) {
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
                .bodyToMono(String.class)
                .map(body -> gson.fromJson(body, JsonObject.class))
                .map(res -> ResponseEntity.status(HttpStatus.OK).body(res))
                .doOnSuccess(s -> log.info("Log In was Successful for Username:" + credentials.getUsername()))
                .doOnError(e -> log.info("Log In was not successful for Username:" + credentials.getUsername()))
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("Given Credentials are not correct!")))
                .block();
    }

    public Response register() {
        //Działa jak poda się unikalne dane
        CredentialRepresentation password = new CredentialRepresentation();
        password.setTemporary(false);
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue("testToSeeIfSetIdWorks");

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setId("08319102-f703-11eb-9a03-0242ac130003");
        user.setUsername("testToSeeIfSetIdWorks");
        user.setFirstName("testToSeeIfSetIdWorks");
        user.setLastName("testToSeeIfSetIdWorks");
        user.setEmail("testToSeeIfSetIdWorks@test.com");
        user.setCredentials(Collections.singletonList(password));

        return keycloak.realm("PracaInz").users().create(user);
    }

    private JsonObject getMessage(String message) {
        JsonObject error = new JsonObject();
        error.addProperty("message", message);

        return error;
    }
}
