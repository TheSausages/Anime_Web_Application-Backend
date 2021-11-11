package pwr.pracainz.services.DTOOperations.Conversion;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
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

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation for the {@link DTOConversionInterface} interface.
 */
@Service
public class DTOConversion implements DTOConversionInterface {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> PageDTO<T> convertToDTO(Page<T> page) {
		return new PageDTO<>(
				page.toList(),
				page.getNumberOfElements(),
				page.getSize(),
				page.getNumber(),
				page.getTotalPages(),
				page.isLast(),
				page.isEmpty()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompleteUserDTO convertToDTO(User user, Set<AchievementDTO> achievements) {
		return new CompleteUserDTO(
				user.getUserId(),
				user.getUsername(),
				user.getNrOfPosts(),
				user.getWatchTime(),
				user.getAchievementPoints(),
				achievements,
				user.getAnimeUserInfo().stream().limit(6).map(this::convertToDTO).collect(Collectors.toSet()),
				user.getThreadUserStatuses().stream().limit(6).map(this::convertToDTO).collect(Collectors.toSet()),
				user.getThreads().stream().limit(6).map(thread -> this.convertToSimpleDTO(thread, null)).collect(Collectors.toSet()),
				user.getPosts().stream().limit(6).map(this::convertToDTO).collect(Collectors.toSet())
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AnimeDTO convertToDTO(Anime anime) {
		return new AnimeDTO(
				anime.getAnimeId(),
				anime.getAverageScore(),
				anime.getNrOfFavourites(),
				anime.getNrOfReviews()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimpleUserDTO convertToSimpleDTO(User user) {
		return new SimpleUserDTO(
				user.getUserId(),
				user.getUsername(),
				user.getNrOfPosts(),
				user.getWatchTime(),
				user.getAchievementPoints()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AchievementDTO convertToDTO(Achievement achievement, byte[] icon) {
		return new AchievementDTO(
				achievement.getAchievementId(),
				achievement.getName(),
				achievement.getDescription(),
				icon,
				achievement.getAchievementPoints(),
				achievement.getUsers().size()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimplePostDTO convertToSimpleDTO(Post post) {
		return new SimplePostDTO(
				post.getPostId(),
				post.getTitle(),
				post.isBlocked(),
				post.getCreation(),
				post.getModification(),
				convertToSimpleDTO(post.getCreator())
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompletePostDTO convertToDTO(Post post) {
		return new CompletePostDTO(
				post.getPostId(),
				post.getTitle(),
				post.isBlocked(),
				post.getCreation(),
				post.getModification(),
				convertToSimpleDTO(post.getCreator()),
				post.getPostText(),
				post.getNrOfPlus(),
				post.getNrOfMinus()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompletePostDTO convertToDTO(Post post, PostUserStatus status) {
		return new CompletePostDTO(
				post.getPostId(),
				post.getTitle(),
				post.isBlocked(),
				post.getCreation(),
				post.getModification(),
				convertToSimpleDTO(post.getCreator()),
				post.getPostText(),
				post.getNrOfPlus(),
				post.getNrOfMinus(),
				this.convertToDTO(status)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimpleThreadDTO convertToSimpleDTO(Thread thread, ThreadUserStatus status) {
		return new SimpleThreadDTO(
				thread.getThreadId(),
				thread.getTitle(),
				thread.getNrOfPosts(),
				thread.getStatus(),
				thread.getCreation(),
				thread.getModification(),
				convertToSimpleDTO(thread.getCreator()),
				convertToDTO(thread.getCategory()),
				thread.getTags().stream().map(this::convertToDTO).collect(Collectors.toList()),
				Objects.isNull(status) ? null : this.convertToDTO(status)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompleteThreadDTO convertToDTO(Thread thread, PageDTO<CompletePostDTO> posts, ThreadUserStatus status) {
		return new CompleteThreadDTO(
				thread.getThreadId(),
				thread.getTitle(),
				thread.getThreadText(),
				thread.getNrOfPosts(),
				thread.getStatus(),
				thread.getCreation(),
				thread.getModification(),
				convertToSimpleDTO(thread.getCreator()),
				convertToDTO(thread.getCategory()),
				thread.getTags().stream().map(this::convertToDTO).collect(Collectors.toList()),
				posts,
				this.convertToDTO(status)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumCategoryDTO convertToDTO(ForumCategory forumCategory) {
		return new ForumCategoryDTO(
				forumCategory.getCategoryId(),
				forumCategory.getCategoryName(),
				forumCategory.getCategoryDescription()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagDTO convertToDTO(Tag tag) {
		return new TagDTO(
				tag.getTagId(),
				tag.getTagName(),
				tag.getTagImportance(),
				tag.getTagColor()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReviewDTO convertToDTO(Review review) {
		return new ReviewDTO(
				review.getReviewId(),
				review.getReviewTitle(),
				review.getReviewText(),
				review.getNrOfHelpful(),
				review.getNrOfPlus(),
				review.getNrOfMinus()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AnimeUserInfoDTO convertToDTO(AnimeUserInfo animeUserInfo) {
		return new AnimeUserInfoDTO(
				convertToDTO(animeUserInfo.getAnimeUserInfoId()),
				animeUserInfo.getStatus(),
				animeUserInfo.getWatchStartDate(),
				animeUserInfo.getWatchEndDate(),
				animeUserInfo.getNrOfEpisodesSeen(),
				animeUserInfo.isFavourite(),
				animeUserInfo.getModification(),
				animeUserInfo.isDidReview(),
				animeUserInfo.getGrade(),
				Objects.nonNull(animeUserInfo.getReview()) ? convertToDTO(animeUserInfo.getReview()) : null
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AnimeUserInfoIdDTO convertToDTO(AnimeUserInfoId animeUserInfoId) {
		return new AnimeUserInfoIdDTO(
				convertToSimpleDTO(animeUserInfoId.getUser()),
				convertToDTO(animeUserInfoId.getAnime())
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ThreadUserStatusIdDTO convertToDTO(ThreadUserStatusId threadUserStatusId) {
		return new ThreadUserStatusIdDTO(
				convertToSimpleDTO(threadUserStatusId.getUser()),
				convertToSimpleDTO(threadUserStatusId.getThread(), null)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ThreadUserStatusDTO convertToDTO(ThreadUserStatus threadUserStatus) {
		return new ThreadUserStatusDTO(
				convertToDTO(threadUserStatus.getThreadUserStatusId()),
				threadUserStatus.isWatching(),
				threadUserStatus.isBlocked()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PostUserStatusIdDTO convertToDTO(PostUserStatusId postUserStatusId) {
		return new PostUserStatusIdDTO(
				convertToSimpleDTO(postUserStatusId.getUser()),
				convertToSimpleDTO(postUserStatusId.getPost())
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PostUserStatusDTO convertToDTO(PostUserStatus postUserStatus) {
		return new PostUserStatusDTO(
				convertToDTO(postUserStatus.getPostUserStatusId()),
				postUserStatus.isLiked(),
				postUserStatus.isDisliked(),
				postUserStatus.isReported()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationTokenDTO convertToDTO(AuthenticationToken authenticationToken) {
		return new AuthenticationTokenDTO(
				authenticationToken.getAccess_token(),
				authenticationToken.getExpires_in(),
				authenticationToken.getRefresh_token(),
				authenticationToken.getToken_type()
		);
	}
}
