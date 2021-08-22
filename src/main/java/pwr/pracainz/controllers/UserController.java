package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.services.keycloak.KeycloakService;
import pwr.pracainz.services.keycloak.KeycloakServiceInterface;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final KeycloakServiceInterface keycloakService;

    @Autowired
    UserController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping("/login")
    public AuthenticationTokenDTO login(@RequestBody @Valid LoginCredentialsDTO credentials) {
        return keycloakService.login(credentials);
    }

    @PostMapping("/logout")
    public ResponseBodyWithMessageDTO logout(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO, @RequestHeader("authorization") String accessToken) {
        return keycloakService.logout(refreshTokenDTO, accessToken);
    }

    @PostMapping("/register")
    public ResponseBodyWithMessageDTO register(@RequestBody @Valid RegistrationBodyDTO registrationBodyDTO) {
        return keycloakService.register(registrationBodyDTO);
    }

    @PostMapping("/refreshToken")
    public AuthenticationTokenDTO refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
        return keycloakService.refreshTokens(refreshTokenDTO);
    }
}
