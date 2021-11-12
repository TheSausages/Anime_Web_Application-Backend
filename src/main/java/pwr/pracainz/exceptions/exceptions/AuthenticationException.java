package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception occurs when an authenticated action was tried by a non-logged in User.
 * It is also used when an error with Keycloak in {@link pwr.pracainz.services.keycloak.KeycloakService} happens.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class AuthenticationException extends CustomException {
	private final static String logMessage = "Authentication Exception, no message given";

	public AuthenticationException(String userMessage) {
		super(userMessage, logMessage);
	}

	public AuthenticationException(String userMessage, String logMessage) { super(userMessage, logMessage); }
}
