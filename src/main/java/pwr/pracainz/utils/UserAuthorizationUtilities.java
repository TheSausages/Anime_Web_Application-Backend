package pwr.pracainz.utils;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

/**
 * Interface possessing small static security utility methods.
 */
public interface UserAuthorizationUtilities {

	/**
	 * Return the Principal of the currently authenticated user - for keycloak it will be the ID (or UUID) of the User on the Keycloak Server.
	 * @return ID (UUID) of the Logged User in the form of a Principal Object
	 */
	static Principal getPrincipalOfCurrentUser() {
		KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		return (Principal) principal.getPrincipal();
	}

	/**
	 * Return the id of the currently authenticated user.
	 * @see #getPrincipalOfCurrentUser()
	 * @return Id of the currently authenticated user
	 */
	static String getIdOfCurrentUser() {
		return getPrincipalOfCurrentUser().toString();
	}

	/**
	 * Check if the request is from an authenticated user or anonymous.
	 * @return true - the user is authenticated, false - it's anonymous
	 */
	static boolean checkIfLoggedUser() {
		return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}
}
