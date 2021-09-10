package pwr.pracainz.services.DTOOperations.Conversion;

import org.springframework.data.domain.Page;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.DTO.animeInfo.ReviewDTO;
import pwr.pracainz.DTO.forum.*;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.Post.SimplePostDTO;
import pwr.pracainz.DTO.forum.Thread.CompleteThreadDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.user.AchievementDTO;
import pwr.pracainz.DTO.user.CompleteUserDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.forum.*;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.userauthentification.AuthenticationToken;

public interface DTOConversionInterface<T> {
    PageDTO<T> convertDomainPageToDTO(Page<T> page);

    CompleteUserDTO convertUserToCompleteDTO(User user);

    SimpleUserDTO convertUserToSimpleDTO(User user);

    AchievementDTO convertAchievementToDTO(Achievement achievement);

    SimplePostDTO convertPostToSimpleDTO(Post post);

    CompletePostDTO convertPostToCompleteDTO(Post post);

    CompletePostDTO convertPostWithUserStatusToCompleteDTO(Post post, PostUserStatus status);

    SimpleThreadDTO convertThreadToSimpleDTO(Thread thread);

    CompleteThreadDTO convertThreadToCompleteDTO(Thread thread, PageDTO<CompletePostDTO> posts);

    ForumCategoryDTO convertForumCategoryToForumDTO(ForumCategory forumCategory);

    TagDTO convertTagToDTO(Tag tag);

    ReviewDTO convertReviewToDTO(Review review);

    AnimeUserInfoDTO convertAnimeUserInfoToDTO(AnimeUserInfo animeUserInfo);

    AnimeUserInfoIdDTO convertAnimeUserInfoIdToDTO(AnimeUserInfoId animeUserInfoId);

    ThreadUserStatusIdDTO convertThreadUserStatusIdToDTO(ThreadUserStatusId threadUserStatusId);

    ThreadUserStatusDTO convertThreadUserStatusToDTO(ThreadUserStatus threadUserStatus);

    PostUserStatusIdDTO convertPostUserStatusIdToDTO(PostUserStatusId postUserStatusId);

    PostUserStatusDTO convertPostUserStatusToDTO(PostUserStatus postUserStatus);

    AuthenticationTokenDTO convertAuthenticationTokenToDTO(AuthenticationToken authenticationToken);
}
