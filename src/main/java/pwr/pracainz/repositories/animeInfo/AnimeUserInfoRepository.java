package pwr.pracainz.repositories.animeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Repository
public interface AnimeUserInfoRepository extends JpaRepository<AnimeUserInfo, AnimeUserInfoId> {
	Boolean existsByAnimeUserInfoIdUserUserIdAndAnimeUserInfoIdAnimeId(@NotEmpty String animeUserInfoId_user_userId, @Positive int animeUserInfoId_animeId);
}
