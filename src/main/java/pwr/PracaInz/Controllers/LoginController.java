package pwr.PracaInz.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.PracaInz.Entities.LoginCredentials;
import pwr.PracaInz.Services.KeycloakService;

@RestController
public class LoginController {
    private final KeycloakService keycloakService;

    @Autowired
    LoginController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginCredentials credentials) {
        return keycloakService.login(credentials);
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<Object> logout(@RequestBody String refreshToken, @RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.status(keycloakService.logout(refreshToken, accessToken)).body(null);
    }
}
