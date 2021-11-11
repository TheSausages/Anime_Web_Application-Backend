package pwr.pracainz.services.anime.AnimeUser;

import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.Anime;

/**
 * Interface for an Anime User Information Service. Each implementation must use this interface.
 */
public interface AnimeUserServiceInterface {
	/**
	 * Get anime user information for the currently authenticated user.
	 * @param anime Anime for which the information should be found.
	 * @return Anime user information.
	 */
	AnimeUserInfoDTO getCurrentUserAnimeInfo(Anime anime);

	/**
	 * Update anime user information for the currently authenticated user and a given anime.
	 * @param animeUserInfoDTO The anime user information that should be used for the update. Has information which anime should be updated.
	 * @return the updated anime user information
	 */
	AnimeUserInfoDTO updateCurrentUserAnimeInfo(AnimeUserInfoDTO animeUserInfoDTO);
}
