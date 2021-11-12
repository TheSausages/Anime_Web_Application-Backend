package pwr.pracainz.exceptions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pwr.pracainz.entities.events.AchievementEarnedEvent;

/**
 * This exception occurs during achievement emission ({@link pwr.pracainz.services.achievement.AchievementService#emitAchievement(AchievementEarnedEvent)}).
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AchievementException extends RuntimeException {
	public AchievementException(String message) {
		super(message);
	}
}
