package pwr.PracaInz.Services;

import com.mysql.cj.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.function.ServerResponse;
import pwr.PracaInz.Entities.LoginCredentials;
import reactor.core.publisher.Mono;

import javax.security.auth.login.LoginException;
import java.util.Objects;

@Log4j2
@Service
public class KeycloakService {
    private final WebClient client;

    @Autowired
    KeycloakService() {
        client = WebClient.create("http://localhost:8180/auth/realms/PracaInz/protocol/openid-connect");
    }

    public HttpStatus logout(String refreshToken, String accessToken) {
        log.info("Attempt to Log Out");

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

    public ResponseEntity<String> login(LoginCredentials credentials) {
        log.info("Attempt to Log In");

        return client
                .post()
                .uri("/token")
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
                })
                .body(BodyInserters
                        .fromFormData("client_id", "ClientServer")
                        .with("client_secret", "2cd84b1b-8fd3-4cba-ab72-dce64d366ac3")
                        .with("scope", "openid")
                        .with("username", credentials.getUsername())
                        .with("password", credentials.getPassword())
                        .with("grant_type", "password"))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(s -> log.info("Log In was Successful for Username:" + credentials.getUsername()))
                .map(res -> ResponseEntity.status(HttpStatus.OK).body(res))
                .doOnError(e -> log.info("Log In was not successful for Username:" + credentials.getUsername()))
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Given Credentials are not correct!"))
                .block();
    }

}
