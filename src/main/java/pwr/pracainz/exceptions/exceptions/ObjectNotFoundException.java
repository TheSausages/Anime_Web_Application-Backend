package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error occurs when an object coulnt be found in the database.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class ObjectNotFoundException extends CustomException {
	private static final String logMessage = "Object Not Found Exception, no message given";

	public ObjectNotFoundException(String userMessage) {
		super(userMessage, logMessage);
	}

	public ObjectNotFoundException(String userMessage, String logMessage) { super(userMessage, logMessage); }
}
