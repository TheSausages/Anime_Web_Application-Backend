package pwr.pracainz.exceptions.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
@Getter
public class AnilistException extends RuntimeException {
	private String logMessage = "Anilist Exception, no message given";

	public AnilistException(String userMessage) {
		super(userMessage);
	}

	public AnilistException(String userMessage, String logMessage) {
		super(userMessage);
		this.logMessage = logMessage;
	}
}
