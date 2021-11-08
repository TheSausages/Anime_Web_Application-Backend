package pwr.pracainz.services.achievement;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pwr.pracainz.entities.events.AchievementEarnedEvent;


public interface AchievementServiceInterface {
	SseEmitter subscribe();

	void cancel();

	void emitAchievement(AchievementEarnedEvent event);
}
