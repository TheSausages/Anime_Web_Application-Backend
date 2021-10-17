package pwr.pracainz.exceptions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AchievementException extends RuntimeException {
	public AchievementException(String message) {
		super(message);
	}
}
