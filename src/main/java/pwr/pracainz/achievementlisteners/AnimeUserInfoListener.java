package pwr.pracainz.achievementlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.AchievementIdEnum;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.events.AchievementEarnedEvent;
import pwr.pracainz.entities.events.AnimeUserInfoUpdateEvent;
import pwr.pracainz.repositories.user.AchievementRepository;

@Component
public class AnimeUserInfoListener {
	private final AchievementRepository achievementRepository;
	private final ApplicationEventPublisher publisher;

	@Autowired
	AnimeUserInfoListener(AchievementRepository achievementRepository, ApplicationEventPublisher publisher) {
		this.achievementRepository = achievementRepository;
		this.publisher = publisher;
	}

	@AchievementListener
	public void NrOfReviewsListener(AnimeUserInfoUpdateEvent event) {
		User user = ((AnimeUserInfo) event.getSource()).getAnimeUserInfoId().getUser();

		long numberOfReviews = user.getAnimeUserInfo().stream()
				.filter(AnimeUserInfo::isDidReview)
				.count();

		//Cases are one less that necessary, as the new review might not be saved to the database yet
		int achievementId = switch ((int) numberOfReviews) {
			case 0 -> AchievementIdEnum.NrOfReviewsAchievement_1.id;
			case 9 -> AchievementIdEnum.NrOfReviewsAchievement_10.id;
			case 49 -> AchievementIdEnum.NrOfReviewsAchievement_50.id;
			default -> -1;
		};

		if (achievementId > -1 && achievementRepository.existsById(achievementId)) {
			Achievement achievement = achievementRepository.getById(achievementId);

			user.earnAchievement(achievement);

			publisher.publishEvent(new AchievementEarnedEvent(achievement));
		}
	}
}
