package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Getter
public class DataException extends RuntimeException {
	private String logMessage = "Data Exception, no message given";

	public DataException(String userMessage) {
		super(userMessage);
	}

	public DataException(String userMessage, String logMessage) {
		super(userMessage);
		this.logMessage = logMessage;
	}
}
