package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class AuthenticationException extends RuntimeException {
	private String logMessage = "Authentication Exception, no message given";

	public AuthenticationException(String userMessage) {
		super(userMessage);
	}

	public AuthenticationException(String userMessage, String logMessage) {
		super(userMessage);
		this.logMessage = logMessage;
	}
}
