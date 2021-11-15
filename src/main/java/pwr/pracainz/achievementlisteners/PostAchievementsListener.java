package pwr.pracainz.achievementlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.AchievementIdEnum;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.events.AchievementEarnedEvent;
import pwr.pracainz.entities.events.PostCreationEvent;
import pwr.pracainz.repositories.user.AchievementRepository;

/**
 * Class that contains all {@link AchievementListener} for the {@link Post} class.
 */
@Component
public class PostAchievementsListener {
	private final AchievementRepository achievementRepository;
	private final ApplicationEventPublisher publisher;

	@Autowired
	PostAchievementsListener(AchievementRepository achievementRepository, ApplicationEventPublisher publisher) {
		this.achievementRepository = achievementRepository;
		this.publisher = publisher;
	}

	/**
	 * Listener that checks if a {@link Post} creator should get an Achievement connected to the {@link User#nrOfPosts}
	 * <p>
	 * Achievements earned for this listener:
	 * <ul>
	 *     <li>{@link AchievementIdEnum#NrOfPostsAchievement_1}</li>
	 *     <li>{@link AchievementIdEnum#NrOfPostsAchievement_10}</li>
	 *     <li>{@link AchievementIdEnum#NrOfPostsAchievement_50}</li>
	 * </ul>
	 * @param event Event containing newly created post.
	 */
	@AchievementListener
	public void NrOfPostListener(PostCreationEvent event) {
		User user = ((Post) event.getSource()).getCreator();

		int achievementId = switch (user.getNrOfPosts()) {
			case 1 -> AchievementIdEnum.NrOfPostsAchievement_1.id;
			case 10 -> AchievementIdEnum.NrOfPostsAchievement_10.id;
			case 50 -> AchievementIdEnum.NrOfPostsAchievement_50.id;
			default -> -1;
		};

		if (achievementId > -1 && achievementRepository.existsById(achievementId)) {
			Achievement achievement = achievementRepository.getById(achievementId);

			user.earnAchievement(achievement);

			publisher.publishEvent(new AchievementEarnedEvent(achievement));
		}
	}
}
