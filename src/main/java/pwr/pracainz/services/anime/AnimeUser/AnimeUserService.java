package pwr.pracainz.services.anime.AnimeUser;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.Anime;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.repositories.animeInfo.AnimeRepository;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.DTOOperations.Deconversion.DTODeconversionInterface;
import pwr.pracainz.services.user.UserServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;

import java.util.Objects;

@Log4j2
@Service
public class AnimeUserService implements AnimeUserServiceInterface {
	private final DTOConversionInterface dtoConversion;
	private final DTODeconversionInterface dtoDeconversion;
	private final UserServiceInterface userService;
	private final AnimeUserInfoRepository animeUserInfoRepository;
	private final AnimeRepository animeRepository;

	@Autowired
	AnimeUserService(DTOConversionInterface dtoConversion, DTODeconversionInterface dtoDeconversion, UserServiceInterface userService, AnimeUserInfoRepository animeUserInfoRepository, AnimeRepository animeRepository) {
		this.dtoConversion = dtoConversion;
		this.dtoDeconversion = dtoDeconversion;
		this.userService = userService;
		this.animeUserInfoRepository = animeUserInfoRepository;
		this.animeRepository = animeRepository;
	}

	@Override
	public AnimeUserInfoDTO getCurrentUserAnimeInfo(Anime anime) {
		AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(userService.getCurrentUserOrInsert(), anime);

		return dtoConversion.convertToDTO(
				animeUserInfoRepository
						.findById(animeUserInfoId)
						.orElseGet(() -> AnimeUserInfo.getEmptyAnimeUserInfo(animeUserInfoId))
		);
	}

	@Override
	public AnimeUserInfoDTO updateCurrentUserAnimeInfo(AnimeUserInfoDTO animeUserInfoDTO) {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException("You are not logged in!");
		}

		User currUser = userService.getCurrentUserOrInsert();
		AnimeUserInfoIdDTO requestUserId = animeUserInfoDTO.getId();

		if (Objects.nonNull(requestUserId) && !currUser.getUserId().equals(requestUserId.getUser().getUserId())) {
			throw new AuthenticationException("Updating your anime information was not successful - please try again");
		}

		Anime anime = animeRepository.findById(animeUserInfoDTO.getId().getAnime().getAnimeId())
				.orElseGet(() -> animeRepository.save(new Anime(
						animeUserInfoDTO.getId().getAnime().getAnimeId()
				)));

		log.info("Update Anime data for User: {}", currUser.getUserId());

		if (Objects.nonNull(animeUserInfoDTO.getGrade())) {
			anime.updateAverageScore(animeUserInfoDTO.getGrade());
		}

		AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(currUser, anime);

		AnimeUserInfo updatedAnimeUserInfo = animeUserInfoRepository.findById(animeUserInfoId)
				.map(animeUserInfo -> animeUserInfo.copyDataFromDTO(animeUserInfoDTO))
				.orElse(dtoDeconversion.convertFromDTO(animeUserInfoDTO, animeUserInfoId));

		return dtoConversion.convertToDTO(animeUserInfoRepository.save(updatedAnimeUserInfo));
	}
}
