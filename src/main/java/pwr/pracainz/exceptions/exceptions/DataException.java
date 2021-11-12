package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is a generic data error. For example, can occur when data holds unexpected or incorrect information.
 */
@ResponseStatus(HttpStatus.CONFLICT)
@Getter
public class DataException extends CustomException {
	private static final String logMessage = "Data Exception, no message given";

	public DataException(String userMessage) {
		super(userMessage, logMessage);
	}

	public DataException(String userMessage, String logMessage) { super(userMessage, logMessage); }
}
