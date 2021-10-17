package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.repositories.user.UserRepository;
import pwr.pracainz.services.keycloak.KeycloakServiceInterface;

import javax.validation.Valid;

import static pwr.pracainz.utils.UserAuthorizationUtilities.getIdOfCurrentUser;

@RestController
@RequestMapping("/auth")
public class UserController {
	private final KeycloakServiceInterface keycloakService;
	private final UserRepository userRepository;

	@Autowired
	UserController(KeycloakServiceInterface keycloakService, UserRepository userRepository) {
		this.keycloakService = keycloakService;
		this.userRepository = userRepository;
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
	public AuthenticationTokenDTO register(@RequestBody @Valid RegistrationBodyDTO registrationBodyDTO) {
		return keycloakService.register(registrationBodyDTO);
	}

	@PostMapping("/refreshToken")
	public AuthenticationTokenDTO refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
		return keycloakService.refreshTokens(refreshTokenDTO);
	}

	@GetMapping("/ach")
	public ResponseEntity aaa() {
		User user = userRepository.findById(getIdOfCurrentUser()).get();

		System.out.println(user);

		return ResponseEntity.ok(user);
	}
}
