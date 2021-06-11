package pwr.PracaInz.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    public String login(@RequestBody LoginCredentials credentials) {
        return keycloakService.login(credentials);
    }

}
