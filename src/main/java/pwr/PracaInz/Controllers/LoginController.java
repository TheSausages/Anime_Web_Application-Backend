package pwr.PracaInz.Controllers;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pwr.PracaInz.Entities.LoginCredentials;
import pwr.PracaInz.Services.KeycloakService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    private final KeycloakService keycloakService;

    @Autowired
    LoginController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginCredentials credentials) {
        return keycloakService.login(credentials);
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<Object> logout(@RequestBody String refreshToken, @RequestHeader("Authorization") String accessToken) {
        return ResponseEntity.status(keycloakService.logout(refreshToken, accessToken)).body(null);
    }
}
