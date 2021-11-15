package pwr.pracainz.utils;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * Interface possessing small static security utility methods.
 */
public interface UserAuthorizationUtilities {

	/**
	 * Return the id of the currently authenticated user - for keycloak it will be the ID (or UUID) of the User on the Keycloak Server.
	 * <p>
	 * Should only be used when sure that the user is logged in!
	 * @return Id of the currently authenticated user in form of UUID
	 */
	static String getIdOfCurrentUser() {
		KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		//In tests the principal is string, in normal run mode it isn't, and we need to .toString() it
		if (principal.getPrincipal() instanceof String) {
			return (String) principal.getPrincipal();
		} else {
			return principal.getPrincipal().toString();
		}
	}

	/**
	 * Check if the request is from an authenticated user or anonymous.
	 * @return true - the user is authenticated, false - it's anonymous
	 */
	static boolean checkIfLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return Objects.nonNull(authentication) && authentication instanceof KeycloakAuthenticationToken;
	}
}
