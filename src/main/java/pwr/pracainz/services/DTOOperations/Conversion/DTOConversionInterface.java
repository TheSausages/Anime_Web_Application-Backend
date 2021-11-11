package pwr.pracainz.services.DTOOperations.Conversion;

import org.springframework.data.domain.Page;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.animeInfo.AnimeDTO;
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
import pwr.pracainz.entities.databaseerntities.animeInfo.Anime;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.forum.*;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.userauthentification.AuthenticationToken;

import java.util.Set;

/**
 * Interface for a DTO Conversion Service. Each implementation must use this interface.
 * <p>
 * Conversion methods for:
 * <ul>
 *     <li>Complete DTO objects (ex. {@link CompleteUserDTO}) should be named <i>convertToDTO</i></li>
 *     <li>Simple DTO objects (ex. {@link SimpleUserDTO}) should be named <i>convertToSimpleDTO</i></li>
 * </ul>
 * <p>
 * Any implementation should not have any dependencies or state fields.
 */
public interface DTOConversionInterface {
	/**
	 * Convert a Spring JPA {@link Page} to a {@link PageDTO} domain object.
	 * @param page Page to be converted
	 * @param <T> Class of the data inside the Page
	 * @return A DTO with data from the original Page.
	 */
	<T> PageDTO<T> convertToDTO(Page<T> page);

	/**
	 * Convert a {@link User} to a {@link CompleteUserDTO} complete domain object.
	 * @param user User to be converted
	 * @param achievements Achievements of the user. Should be processed outside the implementation.
	 * @return A DTO with the data from the original User
	 */
	CompleteUserDTO convertToDTO(User user, Set<AchievementDTO> achievements);

	/**
	 * Convert a {@link User} to a {@link SimpleUserDTO} simple domain object.
	 * @param user User to be converted
	 * @return A DTO with the data from the original User
	 */
	SimpleUserDTO convertToSimpleDTO(User user);

	/**
	 * Convert a {@link Anime} to a {@link AnimeDTO} domain object.
	 * @param anime Anime to be converted
	 * @return A DTO with the data from the original Anime
	 */
	AnimeDTO convertToDTO(Anime anime);

	/**
	 * Convert a {@link Achievement} to a {@link AchievementDTO} domain object. Add the icon to the domain object.
	 * @param achievement Achievement to be converted
	 * @param icon The icon to be added to the {@link AchievementDTO} object
	 * @return A DTO with the data from the original Achievement with the icon added
	 */
	AchievementDTO convertToDTO(Achievement achievement, byte[] icon);

	/**
	 * Convert a {@link Post} to a {@link SimplePostDTO} simple domain object.
	 * @param post Post to be converted
	 * @return A DTO with the data from the original Post
	 */
	SimplePostDTO convertToSimpleDTO(Post post);

	/**
	 * Convert a {@link Post} to a {@link CompleteUserDTO} complete domain object.
	 * @param post Post to be converted
	 * @return A DTO with the data from the original Post
	 */
	CompletePostDTO convertToDTO(Post post);

	/**
	 * Variant of {@link #convertToDTO(Post)} method. This method additionally adds a post user status to the DTO.
	 * @param post Post to be converted
	 * @param status Status to be added to the DTO
	 * @return A DTO with the data from the original Post with the Status added
	 */
	CompletePostDTO convertToDTO(Post post, PostUserStatus status);

	/**
	 * Convert a {@link Thread} to a {@link SimpleThreadDTO} simple domain object with the thread user status added.
	 * @param thread Thread to be converted
	 * @param status Status to be added to the DTO
	 * @return A DTO with the data from the original Thread and the status added
	 */
	SimpleThreadDTO convertToSimpleDTO(Thread thread, ThreadUserStatus status);

	/**
	 * Convert a {@link Thread} to a {@link CompleteThreadDTO} complete domain object.
	 * The thread user status and a {@link PageDTO} of {@link CompletePostDTO} is added to the DTO.
	 * @param thread Thread to be converted
	 * @param posts Page of posts to be added to the DTO
	 * @param status Status to be added to the DTO
	 * @return A DTO with the data from the original Thread with the status and posts added
	 */
	CompleteThreadDTO convertToDTO(Thread thread, PageDTO<CompletePostDTO> posts, ThreadUserStatus status);

	/**
	 * Convert a {@link ForumCategory} to a {@link ForumCategoryDTO} domain object.
	 * @param forumCategory Category to be converted
	 * @return A DTO with the data from the original Category
	 */
	ForumCategoryDTO convertToDTO(ForumCategory forumCategory);

	/**
	 * Convert a {@link Tag} to a {@link TagDTO} domain object.
	 * @param tag Tag to be converted
	 * @return A DTO with the data from the original Tag
	 */
	TagDTO convertToDTO(Tag tag);

	/**
	 * Convert a {@link Review} to a {@link ReviewDTO} domain object.
	 * @param review Review to be converted
	 * @return A DTO with the data from the original Review
	 */
	ReviewDTO convertToDTO(Review review);

	/**
	 * Convert a {@link AnimeUserInfo} to a {@link AnimeUserInfoDTO} domain object.
	 * @param animeUserInfo Anime user information to be converted
	 * @return A DTO with data from the original Anime User Info
	 */
	AnimeUserInfoDTO convertToDTO(AnimeUserInfo animeUserInfo);

	/**
	 * Convert a {@link AnimeUserInfoId} to a {@link AnimeUserInfoIdDTO} domain object.
	 * @param animeUserInfoId Id to be converted
	 * @return A DTO with data from the original Id
	 */
	AnimeUserInfoIdDTO convertToDTO(AnimeUserInfoId animeUserInfoId);

	/**
	 * Convert a {@link ThreadUserStatusId} to a {@link ThreadUserStatusIdDTO} domain object.
	 * @param threadUserStatusId Id to be converted
	 * @return A DTO with data from the original Id
	 */
	ThreadUserStatusIdDTO convertToDTO(ThreadUserStatusId threadUserStatusId);

	/**
	 * Convert a {@link ThreadUserStatus} to a {@link ThreadUserStatusDTO} domain object.
	 * @param threadUserStatus Status to be converted
	 * @return A DTO with the data from the original Status
	 */
	ThreadUserStatusDTO convertToDTO(ThreadUserStatus threadUserStatus);

	/**
	 * Convert a {@link PostUserStatusId} to a {@link PostUserStatusIdDTO} domain object.
	 * @param postUserStatusId Id to be converted
	 * @return A DTO with the data from the original Id
	 */
	PostUserStatusIdDTO convertToDTO(PostUserStatusId postUserStatusId);

	/**
	 * Convert a {@link PostUserStatus} to a {@link PostUserStatusDTO} domain object.
	 * @param postUserStatus Status to be converted
	 * @return A DTO with the data from the original Status
	 */
	PostUserStatusDTO convertToDTO(PostUserStatus postUserStatus);

	/**
	 * Convert a {@link AuthenticationToken} to a {@link AuthenticationTokenDTO} domain object.
	 * @param authenticationToken Tokens to be converted
	 * @return A DTO with the data from the original Tokens
	 */
	AuthenticationTokenDTO convertToDTO(AuthenticationToken authenticationToken);
}
