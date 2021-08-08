package pwr.pracainz.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.LogoutBodyDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.services.KeycloakService;

import javax.validation.Valid;
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
    public ResponseEntity<AuthenticationTokenDTO> login(@RequestBody @Valid LoginCredentialsDTO credentials) {
        return keycloakService.login(credentials);
    }

    @PostMapping("/logout")
    public ResponseEntity<ObjectNode> logout(@RequestBody LogoutBodyDTO logoutBodyDTO, @RequestHeader("authorization") String accessToken) {
        return keycloakService.logout(logoutBodyDTO, accessToken);
    }

    @PostMapping("/register")
    public Response register(@RequestBody @Valid RegistrationBodyDTO registrationBodyDTO) {
        return keycloakService.register(registrationBodyDTO);
    }

    @PostMapping("/liked")
    public void register(@RequestBody String o) {
        System.out.println(o);
    }
}
