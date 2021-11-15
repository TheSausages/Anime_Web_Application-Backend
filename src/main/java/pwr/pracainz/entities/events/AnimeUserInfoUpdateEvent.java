package pwr.pracainz.entities.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;

@Getter
public class AnimeUserInfoUpdateEvent extends ApplicationEvent {
	int numberOfReviews;

	public AnimeUserInfoUpdateEvent(AnimeUserInfo animeUserInfo, int numberOfReviews) {
		super(animeUserInfo);
		this.numberOfReviews = numberOfReviews;
	}
}
