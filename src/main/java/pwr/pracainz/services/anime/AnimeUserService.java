package pwr.pracainz.services.anime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.repositories.animeInfo.ReviewRepository;
import pwr.pracainz.services.DTOConvension.DTOConversionInterface;
import pwr.pracainz.services.user.UserServiceInterface;

@Service
public class AnimeUserService implements AnimeUserServiceInterface {
    private final DTOConversionInterface dtoConversion;
    private final UserServiceInterface userService;
    private final AnimeUserInfoRepository animeUserInfoRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    AnimeUserService(DTOConversionInterface dtoConversion, UserServiceInterface userService, AnimeUserInfoRepository animeUserInfoRepository, ReviewRepository reviewRepository) {
        this.dtoConversion = dtoConversion;
        this.userService = userService;
        this.animeUserInfoRepository = animeUserInfoRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public AnimeUserInfoDTO getCurrentUserAnimeInfo(int animeId) {
        AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(userService.getCurrentUser(), animeId);

        return animeUserInfoRepository
                .findById(animeUserInfoId)
                .map(dtoConversion::convertAnimeUserInfoToDTO)
                .orElse(dtoConversion.convertAnimeUserInfoToDTO(AnimeUserInfo.getEmptyAnimeUserInfo(animeUserInfoId)));
    }

    @Override
    public AnimeUserInfoDTO updateCurrentUserAnimeInfo(AnimeUserInfoDTO animeUserInfoDTO) {
        User currUser = userService.getCurrentUser();

        if (!animeUserInfoDTO.getId().getUser().getUserId().equals(currUser.getUserId())) {
            throw new AuthenticationException("User Anime Information was not successful - please try again");
        }

        AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(currUser, animeUserInfoDTO.getId().getAnimeId());

        AnimeUserInfo updatedAnimeUserInfo = animeUserInfoRepository.findById(animeUserInfoId)
                .map(animeUserInfo -> animeUserInfo.copyDataFromDTO(animeUserInfoDTO))
                .orElse(new AnimeUserInfo(
                        animeUserInfoId,
                        animeUserInfoDTO.getStatus(),
                        animeUserInfoDTO.getWatchStartDate(),
                        animeUserInfoDTO.getWatchEndDate(),
                        animeUserInfoDTO.getNrOfEpisodesSeen(),
                        animeUserInfoDTO.isFavourite(),
                        animeUserInfoDTO.getGrade(),
                        animeUserInfoDTO.isDidReview(),
                        animeUserInfoDTO.isDidReview() ?
                                new Review(
                                        animeUserInfoDTO.getReview().getReviewTitle(),
                                        animeUserInfoDTO.getReview().getReviewText(),
                                        animeUserInfoDTO.getReview().getNrOfHelpful(),
                                        animeUserInfoDTO.getReview().getNrOfPlus(),
                                        animeUserInfoDTO.getReview().getNrOfMinus()
                                )
                                :
                                null
                ));

        return dtoConversion.convertAnimeUserInfoToDTO(animeUserInfoRepository.save(updatedAnimeUserInfo));
    }
}
