package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This error only occurs during user registration. Should not be used anywhere else!
 */
@ResponseStatus(HttpStatus.CONFLICT)
@Getter
public class RegistrationException extends CustomException {
	private static final String logMessage = "Registration Exception, no message given";

	public RegistrationException(String userMessage) {
		super(userMessage, logMessage);
	}

	public RegistrationException(String userMessage, String logMessage) { super(userMessage, logMessage); }
}
