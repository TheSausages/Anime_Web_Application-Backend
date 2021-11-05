package pwr.pracainz.achievementlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pwr.pracainz.entities.AchievementEarnedEvent;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.repositories.user.AchievementRepository;

import javax.persistence.PostPersist;
import java.util.Objects;

@Component
public class NrOfPostsAchievementsListener {
	private static AchievementRepository achievementRepository;
	private static ApplicationEventPublisher publisher;

	@Autowired
	public void setAchievementRepository(AchievementRepository achievementRepository, ApplicationEventPublisher publisher) {
		NrOfPostsAchievementsListener.achievementRepository = achievementRepository;
		NrOfPostsAchievementsListener.publisher = publisher;
	}

	@PostPersist
	public void nrOfPostsAchievements(Post post) {
		User user = post.getCreator();

		Achievement achievement = switch (post.getCreator().getNrOfPosts()) {
			case 1 -> achievementRepository.getById(2);
			case 10 -> achievementRepository.getById(3);
			case 50 -> achievementRepository.getById(4);
			default -> null;
		};

		if (Objects.nonNull(achievement)) {
			user.addAchievementPoints(achievement.getAchievementPoints());
			user.getAchievements().add(achievement);

			publisher.publishEvent(new AchievementEarnedEvent(achievement));
		}
	}
}
