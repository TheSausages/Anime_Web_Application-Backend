package pwr.pracainz.services.DTOOperations.Deconversion;

import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatusId;

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
                animeUserInfoDTO.getModification(),
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

    @Override
    public PostUserStatus convertFromDTO(PostUserStatusDTO postUserStatusDTO, PostUserStatusId postUserStatusId) {
        return new PostUserStatus(
                postUserStatusId,
                postUserStatusDTO.isLiked(),
                postUserStatusDTO.isDisliked(),
                postUserStatusDTO.isReported()
        );
    }
}