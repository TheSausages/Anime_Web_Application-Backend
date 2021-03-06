package pwr.pracainz.services.anime.AnimeUser;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.Anime;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.events.AnimeUserInfoUpdateEvent;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.exceptions.exceptions.DataException;
import pwr.pracainz.repositories.animeInfo.AnimeRepository;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.DTOOperations.Deconversion.DTODeconversionInterface;
import pwr.pracainz.services.i18n.I18nServiceInterface;
import pwr.pracainz.services.user.UserServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;

import java.util.Objects;

/**
 * Default implementation for the {@link AnimeUserServiceInterface} interface.
 */
@Log4j2
@Service
public class AnimeUserService implements AnimeUserServiceInterface {
	private final DTOConversionInterface dtoConversion;
	private final DTODeconversionInterface dtoDeconversion;
	private final UserServiceInterface userService;
	private final I18nServiceInterface i18nService;
	private final AnimeUserInfoRepository animeUserInfoRepository;
	private final ApplicationEventPublisher publisher;
	private final AnimeRepository animeRepository;

	@Autowired
	AnimeUserService(DTOConversionInterface dtoConversion,
	                 DTODeconversionInterface dtoDeconversion,
	                 UserServiceInterface userService,
	                 AnimeUserInfoRepository animeUserInfoRepository,
	                 AnimeRepository animeRepository,
	                 ApplicationEventPublisher publisher,
	                 I18nServiceInterface i18nService) {
		this.dtoConversion = dtoConversion;
		this.dtoDeconversion = dtoDeconversion;
		this.userService = userService;
		this.i18nService = i18nService;
		this.animeUserInfoRepository = animeUserInfoRepository;
		this.publisher = publisher;
		this.animeRepository = animeRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AnimeUserInfoDTO getCurrentUserAnimeInfo(Anime anime) {
		AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(userService.getCurrentUser(), anime);

		return dtoConversion.convertToDTO(
				animeUserInfoRepository
						.findById(animeUserInfoId)
						.orElseGet(() -> AnimeUserInfo.getEmptyAnimeUserInfo(animeUserInfoId))
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public AnimeUserInfoDTO updateCurrentUserAnimeInfo(AnimeUserInfoDTO animeUserInfoDTO) {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
					"User tried to update User info without being logged in");
		}

		User currUser = userService.getCurrentUser();
		AnimeUserInfoIdDTO requestUserId = animeUserInfoDTO.getId();

		if (Objects.isNull(requestUserId) || !currUser.getUserId().equals(requestUserId.getUser().getUserId())) {
			throw new AuthenticationException(i18nService.getTranslation("anime.error-during-anime-info-update"),
					String.format("Error during Anime User Information update for user %s", currUser.getUsername()));
		}

		if (animeUserInfoDTO.isDidReview() && (animeUserInfoDTO.getReview().getReviewTitle().length() > 100 || animeUserInfoDTO.getReview().getReviewText().length() > 300)) {
			throw new AuthenticationException(i18nService.getTranslation("anime.review-to-long"),
					currUser.getUsername() + " review was too long");
		}

		Anime anime = animeRepository.findById(animeUserInfoDTO.getId().getAnime().getAnimeId())
				.orElseGet(() -> animeRepository.save(new Anime(
						animeUserInfoDTO.getId().getAnime().getAnimeId()
				)));

		log.info("Update Anime data for User: {}", currUser.getUsername());

		if (Objects.nonNull(animeUserInfoDTO.getGrade())) {
			anime.updateAverageScore(animeUserInfoDTO.getGrade());
		}

		AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(currUser, anime);

		AnimeUserInfo updatedAnimeUserInfo = animeUserInfoRepository.findById(animeUserInfoId)
				.map(animeUserInfo -> {
					if (animeUserInfo.isDidReview() && animeUserInfoDTO.isDidReview() && animeUserInfo.getReview().getReviewId() != animeUserInfoDTO.getReview().getId()) {
						throw new DataException(i18nService.getTranslation("anime.error-during-anime-info-update"),
									String.format("While updating %s anime information, the id of the reviews wasn't the same", currUser.getUsername())
								);
					}

					int oldWatchTime = animeUserInfo.getNrOfEpisodesSeen() * anime.getAverageEpisodeLength();
					int newWatchTime = animeUserInfoDTO.getNrOfEpisodesSeen() * anime.getAverageEpisodeLength();
					currUser.addWatchTime(newWatchTime - oldWatchTime);

					return animeUserInfo.copyDataFromDTO(animeUserInfoDTO);
				})
				.orElseGet(() -> {
					int newWatchTime = animeUserInfoDTO.getNrOfEpisodesSeen() * anime.getAverageEpisodeLength();
					currUser.addWatchTime(newWatchTime);

					return dtoDeconversion.convertFromDTO(animeUserInfoDTO, animeUserInfoId);
				});

		//This needs to be done here, as we can do max 1 database operation in Listener (I think)
		int numberOfReviews = (int) currUser.getAnimeUserInfo().stream()
				.filter(AnimeUserInfo::isDidReview)
				.count();

		publisher.publishEvent(new AnimeUserInfoUpdateEvent(updatedAnimeUserInfo, numberOfReviews));

		return dtoConversion.convertToDTO(animeUserInfoRepository.save(updatedAnimeUserInfo));
	}
}
