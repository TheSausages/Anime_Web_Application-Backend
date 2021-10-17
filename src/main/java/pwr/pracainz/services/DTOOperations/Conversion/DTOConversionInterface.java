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
	PageDTO<T> convertToDTO(Page<T> page);

	CompleteUserDTO convertToDTO(User user);

	SimpleUserDTO convertToSimpleDTO(User user);

	AchievementDTO convertToDTO(Achievement achievement);

	SimplePostDTO convertToSimpleDTO(Post post);

	CompletePostDTO convertToDTO(Post post);

	CompletePostDTO convertToDTO(Post post, PostUserStatus status);

	SimpleThreadDTO convertToSimpleDTO(Thread thread);

	CompleteThreadDTO convertToDTO(Thread thread, PageDTO<CompletePostDTO> posts);

	ForumCategoryDTO convertToDTO(ForumCategory forumCategory);

	TagDTO convertToDTO(Tag tag);

	ReviewDTO convertToDTO(Review review);

	AnimeUserInfoDTO convertToDTO(AnimeUserInfo animeUserInfo);

	AnimeUserInfoIdDTO convertToDTO(AnimeUserInfoId animeUserInfoId);

	ThreadUserStatusIdDTO convertToDTO(ThreadUserStatusId threadUserStatusId);

	ThreadUserStatusDTO convertToDTO(ThreadUserStatus threadUserStatus);

	PostUserStatusIdDTO convertToDTO(PostUserStatusId postUserStatusId);

	PostUserStatusDTO convertToDTO(PostUserStatus postUserStatus);

	AuthenticationTokenDTO convertToDTO(AuthenticationToken authenticationToken);
}
