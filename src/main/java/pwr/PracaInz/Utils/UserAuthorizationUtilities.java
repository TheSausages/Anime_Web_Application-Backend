package pwr.PracaInz.Utils;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

/**
 * Interface that has small Utility methods
 */
public interface UserAuthorizationUtilities {

    /**
     * Return the KeycloakAuthenticationToken (Authorization Information) of the current user
     * @return Authorization Information of Current User in form of KeycloakAuthenticationToken
     */
    static KeycloakAuthenticationToken getAuthorizationInfoOfCurrentUser() {
        return (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Return the Principal of the Current User - for keycloak it will be the ID (or UUID) of the User on the Keycloak Server
     * @return ID (UUID) of the Logged User in the form of a Principal Object
     */
    static Principal getPrincipalOfCurrentUser() {
        KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (Principal) principal.getPrincipal();
    }
}
