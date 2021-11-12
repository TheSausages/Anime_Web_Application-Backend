package pwr.pracainz.exceptions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception occurs when a request to the Anilist Api occurs.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class AnilistException extends CustomException {
	private final static String logMessage = "Anilist Exception, no message given";

	public AnilistException(String userMessage) {
		super(userMessage, logMessage);
	}

	public AnilistException(String userMessage, String logMessage) { super(userMessage, logMessage); }
}
