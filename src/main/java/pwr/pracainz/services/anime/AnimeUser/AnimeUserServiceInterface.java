package pwr.pracainz.services.anime.AnimeUser;

import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.Anime;

public interface AnimeUserServiceInterface {
	AnimeUserInfoDTO getCurrentUserAnimeInfo(Anime anime);

	AnimeUserInfoDTO updateCurrentUserAnimeInfo(AnimeUserInfoDTO animeUserInfoDTO);
}
