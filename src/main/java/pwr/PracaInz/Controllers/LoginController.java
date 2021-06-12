package pwr.PracaInz.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void logout(@RequestBody String refreshToken, @RequestHeader("Authorization") String accessToken) {
        keycloakService.logout(refreshToken, accessToken);
    }

    /*
    Small mapping used to test if authorized paths are available
     */
    @GetMapping(value = "/aa")
    public String aa() {
        System.out.println("Authorized controller ok");
        return "{\n\"aa\": \"aa\"\n}";
    }
}
