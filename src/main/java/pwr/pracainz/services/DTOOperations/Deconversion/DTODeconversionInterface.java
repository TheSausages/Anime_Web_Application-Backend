package pwr.pracainz.services.DTOOperations.Deconversion;

import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.forum.Post.CreatePostDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.forum.Thread.CreateThreadDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatusId;
import pwr.pracainz.entities.databaseerntities.forum.Thread;

public interface DTODeconversionInterface {
    AnimeUserInfo convertFromDTO(AnimeUserInfoDTO animeUserInfoDTO, AnimeUserInfoId animeUserInfoId);

    PostUserStatus convertFromDTO(PostUserStatusDTO postUserStatusDTO, PostUserStatusId postUserStatusId);

    Post convertFromDTO(CreatePostDTO postDTO);

    Thread convertFromDTO(CreateThreadDTO threadDTO);
}
