package pwr.pracainz.services.anime.AnimeUser;

import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;

public interface AnimeUserServiceInterface {
    AnimeUserInfoDTO getCurrentUserAnimeInfo(int animeId);

    AnimeUserInfoDTO updateCurrentUserAnimeInfo(AnimeUserInfoDTO animeUserInfoDTO);
}
