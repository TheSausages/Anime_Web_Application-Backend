package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class ObjectNotFoundException extends RuntimeException {
	private String logMessage;

	public ObjectNotFoundException(String userMessage) {
		super(userMessage);
	}

	public ObjectNotFoundException(String userMessage, String logMessage) {
		super(userMessage);
		this.logMessage = logMessage;
	}
}
