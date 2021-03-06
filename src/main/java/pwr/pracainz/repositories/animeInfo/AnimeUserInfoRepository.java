package pwr.pracainz.repositories.animeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;

/**
 * Repository for the {@link AnimeUserInfo} class (AnimeUserInfos table).
 */
@Repository
public interface AnimeUserInfoRepository extends JpaRepository<AnimeUserInfo, AnimeUserInfoId> {
}
