package pwr.pracainz.eventlisteners.achievementlisteners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.repositories.user.AchievementRepository;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
public class NrOfPostsAchievements {
    private static AchievementRepository achievementRepository;

    @Autowired
    public void setAchievementRepository(AchievementRepository achievementRepository) {
        NrOfPostsAchievements.achievementRepository = achievementRepository;
    }

    @PostPersist
    @PostUpdate
    public void addToNrOfPosts(Post post) {
        User user = post.getUser();

        if (user.getNrOfPosts() == 0) {
            Achievement achievement = achievementRepository.getById(2);

            user.addAchievementPoints(achievement.getAchievementPoints());
            user.getAchievements().add(achievement);
        }
    }
}
