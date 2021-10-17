package pwr.pracainz.services.achievement;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pwr.pracainz.entities.databaseerntities.user.Achievement;

public interface AchievementServiceInterface {
	SseEmitter achievementEmitter();

	void emitAchievement(Achievement achievement);
}
