package pwr.pracainz.services.DTOOperations.DTODeconversion;

import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;

@Service
public class DTODeconversion implements DTODeconversionInterface {

    @Override
    public AnimeUserInfo convertFromDTO(AnimeUserInfoDTO animeUserInfoDTO, AnimeUserInfoId animeUserInfoId) {
        return new AnimeUserInfo(
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
                        null);
    }
}
