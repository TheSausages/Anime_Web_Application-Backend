package pwr.pracainz.repositories.animeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;

/**
 * Repository for the {@link Review} class (Reviews table).
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
