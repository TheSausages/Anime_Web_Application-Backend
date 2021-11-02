package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.services.keycloak.KeycloakServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {
	private final KeycloakServiceInterface keycloakService;

	@Autowired
	UserController(KeycloakServiceInterface keycloakService) {
		this.keycloakService = keycloakService;
	}

	@PostMapping("/login")
	public AuthenticationTokenDTO login(@RequestBody @Valid LoginCredentialsDTO credentials, HttpServletRequest request) {
		return keycloakService.login(credentials, request);
	}

	@PostMapping("/logout")
	public ResponseBodyWithMessageDTO logout(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO, @RequestHeader("authorization") String accessToken, HttpServletRequest request) {
		return keycloakService.logout(refreshTokenDTO, accessToken, request);
	}

	@PostMapping("/register")
	public AuthenticationTokenDTO register(@RequestBody @Valid RegistrationBodyDTO registrationBodyDTO, HttpServletRequest request) {
		return keycloakService.register(registrationBodyDTO, request);
	}

	@PostMapping("/refreshToken")
	public AuthenticationTokenDTO refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO, HttpServletRequest request) {
		return keycloakService.refreshTokens(refreshTokenDTO, request);
	}
}
