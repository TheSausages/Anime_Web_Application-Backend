package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter
public class RegistrationException extends RuntimeException {
	private String logMessage = "Registration Exception, no message given";

	public RegistrationException(String userMessage) {
		super(userMessage);
	}

	public RegistrationException(String userMessage, String logMessage) {
		super(userMessage);
		this.logMessage = logMessage;
	}
}
