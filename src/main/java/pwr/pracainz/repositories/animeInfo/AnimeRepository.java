package pwr.pracainz.repositories.animeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.animeInfo.Anime;

/**
 * Repository for the {@link Anime} class (AnimeTable)
 */
@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer> {
}
