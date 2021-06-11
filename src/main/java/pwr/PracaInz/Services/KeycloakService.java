package pwr.PracaInz.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.PracaInz.Entities.LoginCredentials;
import reactor.core.publisher.Mono;

@Service
public class KeycloakService {
    private final WebClient client;

    @Autowired
    KeycloakService() {
        client = WebClient.create("http://localhost:8180/auth/realms/PracaInz-realm/protocol/openid-connect/token");
    }

    public String login(LoginCredentials credentials) {
        Mono<String> response = client
                .post()
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
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    private Mono<String> evaluateClientResponse(ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(String.class);
        } else {
            System.out.println(response.bodyToMono(String.class));
            return response.createException()
                    .flatMap(Mono::error);
        }
    }
}
