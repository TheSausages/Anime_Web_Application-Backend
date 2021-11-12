package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;

/**
 * Abstract class for custom errors that don't extend another exception.
 * The {@link CustomException#detailMessage} is returned to the user.
 */
@Getter
public abstract class CustomException extends RuntimeException {
	private final String logMessage;

	public CustomException(String userMessage, String logMessage) {
		super(userMessage);
		this.logMessage = logMessage;
	}
}
