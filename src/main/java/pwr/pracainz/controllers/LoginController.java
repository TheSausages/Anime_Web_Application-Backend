package pwr.pracainz.controllers;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.LoginCredentials;
import pwr.pracainz.DTO.LogoutBody;
import pwr.pracainz.services.KeycloakService;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<JsonObject> logout(@RequestBody LogoutBody logoutBody, @RequestHeader("authorization") String accessToken) {
        return keycloakService.logout(logoutBody, accessToken);
    }

    @GetMapping("/registerUser")
    public Response register() {
        return keycloakService.register();
    }
}
