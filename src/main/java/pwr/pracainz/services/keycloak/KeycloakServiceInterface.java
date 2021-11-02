package pwr.pracainz.services.keycloak;

import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;

import javax.servlet.http.HttpServletRequest;

public interface KeycloakServiceInterface {
	AuthenticationTokenDTO login(LoginCredentialsDTO credentials, HttpServletRequest request);

	ResponseBodyWithMessageDTO logout(RefreshTokenDTO logoutRequestBody, String accessToken, HttpServletRequest request);

	AuthenticationTokenDTO register(RegistrationBodyDTO registrationBody, HttpServletRequest request);

	AuthenticationTokenDTO refreshTokens(RefreshTokenDTO refreshTokenDTO, HttpServletRequest request);
}
