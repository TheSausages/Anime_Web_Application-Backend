package pwr.pracainz.entities;

import org.springframework.context.ApplicationEvent;
import pwr.pracainz.entities.databaseerntities.user.Achievement;

public class AchievementEarnedEvent extends ApplicationEvent {
	public AchievementEarnedEvent(Achievement source) {
		super(source);
	}
}