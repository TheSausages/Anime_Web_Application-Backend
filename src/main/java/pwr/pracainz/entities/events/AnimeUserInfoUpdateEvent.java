package pwr.pracainz.entities.events;

import org.springframework.context.ApplicationEvent;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;

public class AnimeUserInfoUpdateEvent extends ApplicationEvent {
	public AnimeUserInfoUpdateEvent(AnimeUserInfo animeUserInfo) { super(animeUserInfo); }
}
