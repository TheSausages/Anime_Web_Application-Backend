package pwr.pracainz.services.DTOOperations.Deconversion;

import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.forum.Post.CreatePostDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.forum.Thread.CreateThreadDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.forum.*;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * Default implementation for the {@link DTODeconversionInterface} interface.
 * <p>
 * Any implementation should not have any dependencies or state fields.
 */
@Service
public class DTODeconversion implements DTODeconversionInterface {

	/**
	 * {@inheritDoc}
	 */
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
				LocalDateTime.now(),
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PostUserStatus convertFromDTO(PostUserStatusDTO postUserStatusDTO, PostUserStatusId postUserStatusId) {
		return new PostUserStatus(
				postUserStatusId,
				postUserStatusDTO.isLiked(),
				postUserStatusDTO.isDisliked(),
				postUserStatusDTO.isReported()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ThreadUserStatus convertFromDTO(ThreadUserStatusDTO threadUserStatusDTO, ThreadUserStatusId threadUserStatusId) {
		return new ThreadUserStatus(
				threadUserStatusId,
				threadUserStatusDTO.isWatching(),
				threadUserStatusDTO.isBlocked()
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Post convertFromDTO(CreatePostDTO postDTO) {
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setPostText(postDTO.getText());
		post.setCreation(LocalDateTime.now());
		post.setModification(LocalDateTime.now());

		return post;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Thread convertFromDTO(CreateThreadDTO threadDTO) {
		Thread thread = new Thread();
		thread.setTitle(threadDTO.getTitle());
		thread.setThreadText(threadDTO.getText());
		thread.setStatus(ThreadStatus.OPEN);
		thread.setCreation(LocalDateTime.now());
		thread.setModification(LocalDateTime.now());
		thread.setNrOfPosts(0);
		thread.setTags(Collections.emptyList());
		thread.setThreadUserStatuses(Collections.emptySet());
		thread.setPosts(Collections.emptySet());

		return thread;
	}
}
