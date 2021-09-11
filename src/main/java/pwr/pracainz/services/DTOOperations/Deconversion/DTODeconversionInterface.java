package pwr.pracainz.services.DTOOperations.Deconversion;

import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatusId;

public interface DTODeconversionInterface {
    AnimeUserInfo convertFromDTO(AnimeUserInfoDTO animeUserInfoDTO, AnimeUserInfoId animeUserInfoId);

    PostUserStatus convertFromDTO(PostUserStatusDTO postUserStatusDTO, PostUserStatusId postUserStatusId);
}
