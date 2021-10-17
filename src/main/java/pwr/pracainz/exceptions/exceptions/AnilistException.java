package pwr.pracainz.exceptions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class AnilistException extends RuntimeException {
	public AnilistException(String message) {
		super(message);
	}
}
