package pwr.pracainz.eventlisteners.achievementlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.repositories.user.AchievementRepository;
import pwr.pracainz.services.achievement.AchievementServiceInterface;

import javax.persistence.PostPersist;
import java.util.Objects;

@Component
public class NrOfPostsAchievementsListener {
	private static AchievementRepository achievementRepository;
	private static AchievementServiceInterface achievementService;

	@Autowired
	public void setAchievementRepository(AchievementRepository achievementRepository, AchievementServiceInterface achievementService) {
		NrOfPostsAchievementsListener.achievementRepository = achievementRepository;
		NrOfPostsAchievementsListener.achievementService = achievementService;
	}

	@PostPersist
	public void nrOfPostsAchievements(Post post) {
		User user = post.getUser();

		Achievement achievement = switch (post.getUser().getNrOfPosts()) {
			case 0 -> achievementRepository.getById(2);
			case 10 -> achievementRepository.getById(3);
			case 50 -> achievementRepository.getById(4);
			default -> null;
		};

		if (Objects.nonNull(achievement)) {
			user.addAchievementPoints(achievement.getAchievementPoints());
			user.getAchievements().add(achievement);
			achievementService.emitAchievement(achievement);
		}
	}
}
