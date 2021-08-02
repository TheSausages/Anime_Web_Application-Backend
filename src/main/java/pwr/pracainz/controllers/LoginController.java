package pwr.pracainz.controllers;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pwr.pracainz.entities.LoginCredentials;
import pwr.pracainz.services.KeycloakService;

@RestController
public class LoginController {
    private final KeycloakService keycloakService;

    @Autowired
    LoginController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/login")
    public ResponseEntity<JsonObject> login(@RequestBody LoginCredentials credentials) {
        return keycloakService.login(credentials);
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<?> logout(@RequestBody String refreshToken, @RequestHeader("authorization") String accessToken) {
        return ResponseEntity.status(keycloakService.logout(refreshToken, accessToken)).build();
    }
}
