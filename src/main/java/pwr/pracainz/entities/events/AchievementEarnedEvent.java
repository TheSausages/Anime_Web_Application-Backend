package pwr.pracainz.entities.events;

import org.springframework.context.ApplicationEvent;
import pwr.pracainz.entities.databaseerntities.user.Achievement;

/**
 * Event used when an achievement is earned by a user.
 */
public class AchievementEarnedEvent extends ApplicationEvent {
	public AchievementEarnedEvent(Achievement source) { super(source); }
}