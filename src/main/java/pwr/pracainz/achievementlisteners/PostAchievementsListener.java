package pwr.pracainz.achievementlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.AchievementIdEnum;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.events.AchievementEarnedEvent;
import pwr.pracainz.entities.events.PostEvent;
import pwr.pracainz.repositories.user.AchievementRepository;

@Component
public class PostAchievementsListener {
	private final AchievementRepository achievementRepository;
	private final ApplicationEventPublisher publisher;

	@Autowired
	PostAchievementsListener(AchievementRepository achievementRepository, ApplicationEventPublisher publisher) {
		this.achievementRepository = achievementRepository;
		this.publisher = publisher;
	}

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@EventListener
	public void NrOfAchievementListener(PostEvent event) {
		User user = ((Post) event.getSource()).getCreator();

		int achievementId = switch (user.getNrOfPosts()) {
			case 1 -> AchievementIdEnum.NrOfPostsAchievement_1.id;
			case 10 -> AchievementIdEnum.NrOfPostsAchievement_10.id;
			case 50 -> AchievementIdEnum.NrOfPostsAchievement_50.id;
			default -> -1;
		};

		if (achievementId > -1 && achievementRepository.existsById(achievementId)) {
			Achievement achievement = achievementRepository.getById(achievementId);

			user.addAchievementPoints(achievement.getAchievementPoints());
			user.getAchievements().add(achievement);

			publisher.publishEvent(new AchievementEarnedEvent(achievement));
		}
	}
}
