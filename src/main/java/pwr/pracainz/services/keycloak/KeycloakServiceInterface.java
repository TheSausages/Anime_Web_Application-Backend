package pwr.pracainz.services.keycloak;

import pwr.pracainz.DTO.SimpleMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.configuration.configuration.WebClientsConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for a Keycloak Service. Used to interact with the keycloak server. Each implementation must use this interface.
 * It's recommended to use {@link WebClientsConfiguration#anilistWebClient()} in order to connect to keycloak.
 *
 * <p>
 *
 * The <i>request</i> parameter for each method is used to retrieve the {@link org.springframework.http.HttpHeaders#ACCEPT_LANGUAGE}
 * header from the request for a {@link pwr.pracainz.services.i18n.I18nServiceInterface} implementation to use.
 */
public interface KeycloakServiceInterface {
	/**
	 * Log in a given user in keycloak using its credentials.
	 * @param credentials Credentials used to log in a user.
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return The tokens used to authenticate a user. Also has information for refreshing the access token.
	 */
	AuthenticationTokenDTO login(LoginCredentialsDTO credentials, HttpServletRequest request);

	/**
	 * Log out a user from keycloak.
	 * @param logoutRequestBody Information needed to log out a user from keycloak.
	 * @param accessToken The access token used to authenticate the user when connecting to the endpoint. Used for logging out.
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return Response body with a message about being logged out.
	 */
	SimpleMessageDTO logout(RefreshTokenDTO logoutRequestBody, String accessToken, HttpServletRequest request);

	/**
	 * Register a user in keycloak.
	 * @param registrationBody Data used to register a user
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return The tokens used to authenticate a user. Also has information for refreshing the authentication token.
	 */
	AuthenticationTokenDTO register(RegistrationBodyDTO registrationBody, HttpServletRequest request);

	/**
	 * Refresh the authentication token and send the new token back.
	 * @param refreshTokenDTO Data used to refresh the access token
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return New token for the user.
	 */
	AuthenticationTokenDTO refreshTokens(RefreshTokenDTO refreshTokenDTO, HttpServletRequest request);
}
