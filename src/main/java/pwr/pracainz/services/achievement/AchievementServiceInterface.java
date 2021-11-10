package pwr.pracainz.services.achievement;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pwr.pracainz.entities.events.AchievementEarnedEvent;

/**
 * Interface for an Achievement Service. Each implementation must use this interface.
 */
public interface AchievementServiceInterface {
	/**
	 * Allow an authenticated user to subscribe to the achievement service. Used when the user logs in.
	 * @return An {@link SseEmitter} used to emit the achievement.
	 */
	SseEmitter subscribe();

	/**
	 * Allow an authenticated user to cancel their achievement service subscription. Used when the user logs out.
	 */
	void cancel();

	/**
	 * Emit an achievement for an authenticated user. Don't do anything if the user is not logged in.
	 * @param event Event containing the earned achievement.
	 */
	void emitAchievement(AchievementEarnedEvent event);
}
