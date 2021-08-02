package pwr.pracainz.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.user.Achievement;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
}
