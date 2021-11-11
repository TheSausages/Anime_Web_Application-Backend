package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.user.CompleteUserDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.services.keycloak.KeycloakServiceInterface;
import pwr.pracainz.services.user.UserServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestControllerWithBasePath
public class UserController {
	private final KeycloakServiceInterface keycloakService;
	private final UserServiceInterface userService;

	@Autowired
	UserController(KeycloakServiceInterface keycloakService, UserServiceInterface userService) {
		this.keycloakService = keycloakService;
		this.userService = userService;
	}

	@GetMapping("/user/{userId}")
	public CompleteUserDTO getUserInformation(@PathVariable String userId) {
		return userService.getUserInformationById(userId);
	}

	@GetMapping("/user/current")
	public CompleteUserDTO getUserInformation() {
		return userService.getCurrentUserInformation();
	}

	@PostMapping("/auth/login")
	public AuthenticationTokenDTO login(@RequestBody @Valid LoginCredentialsDTO credentials, HttpServletRequest request) {
		return keycloakService.login(credentials, request);
	}

	@PostMapping("/auth/logout")
	public ResponseBodyWithMessageDTO logout(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO, @RequestHeader("authorization") String accessToken, HttpServletRequest request) {
		return keycloakService.logout(refreshTokenDTO, accessToken, request);
	}

	@PostMapping("/auth/register")
	public AuthenticationTokenDTO register(@RequestBody @Valid RegistrationBodyDTO registrationBodyDTO, HttpServletRequest request) {
		return keycloakService.register(registrationBodyDTO, request);
	}

	@PostMapping("/auth/refreshToken")
	public AuthenticationTokenDTO refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO, HttpServletRequest request) {
		return keycloakService.refreshTokens(refreshTokenDTO, request);
	}
}
