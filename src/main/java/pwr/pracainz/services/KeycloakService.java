package pwr.pracainz.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mysql.cj.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.pracainz.entities.LoginCredentials;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Log4j2
@Service
public class KeycloakService {
    private final WebClient client;
    private final Gson gson;

    @Autowired
    KeycloakService() {
        client = WebClient.create("http://localhost:8180/auth/realms/PracaInz/protocol/openid-connect");
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public HttpStatus logout(String refreshToken, String accessToken) {
        if (StringUtils.isEmptyOrWhitespaceOnly(refreshToken) || StringUtils.isEmptyOrWhitespaceOnly(accessToken)) {
            log.warn("Could not log out - missing information!");

            return HttpStatus.UNAUTHORIZED;
        }

        Mono<ResponseEntity<Void>> response = client
                .post()
                .uri("/logout")
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    httpHeaders.set("Authorization", accessToken);
                })
                .body(BodyInserters
                        .fromFormData("client_id", "ClientServer")
                        .with("client_secret", "2cd84b1b-8fd3-4cba-ab72-dce64d366ac3")
                        .with("refresh_token", refreshToken.substring(17, refreshToken.length() - 2)))
                .retrieve()
                .toBodilessEntity();

        log.info("Logged Out Successfully");
        return Objects.requireNonNull(response.block()).getStatusCode();
    }

    public ResponseEntity<JsonObject> login(LoginCredentials credentials) {
        return client
                .post()
                .uri("/token")
                .headers(httpHeaders -> httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE)))
                .body(BodyInserters
                        .fromFormData("client_id", "ClientServer")
                        .with("client_secret", "2cd84b1b-8fd3-4cba-ab72-dce64d366ac3")
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
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getErrorMessage("Given Credentials are not correct!")))
                .block();
    }

    private JsonObject getErrorMessage(String message) {
        JsonObject error = new JsonObject();
        error.addProperty("message", "Anilist Server did not Respond");

        return error;
    }
}
