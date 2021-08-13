package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.LogoutRequestBodyDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.services.keycloak.KeycloakService;
import pwr.pracainz.services.keycloak.KeycloakServiceInterface;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final KeycloakServiceInterface keycloakService;

    @Autowired
    LoginController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokenDTO> login(@RequestBody @Valid LoginCredentialsDTO credentials) {
        return keycloakService.login(credentials);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseBodyWithMessageDTO> logout(@RequestBody LogoutRequestBodyDTO logoutRequestBodyDTO, @RequestHeader("authorization") String accessToken) {
        return keycloakService.logout(logoutRequestBodyDTO, accessToken);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseBodyWithMessageDTO> register(@RequestBody @Valid RegistrationBodyDTO registrationBodyDTO) {
        return keycloakService.register(registrationBodyDTO);
    }

    @PostMapping("/liked")
    public void register(@RequestBody String o) {
        System.out.println(o);
    }
}
