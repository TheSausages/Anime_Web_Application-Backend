package pwr.pracainz.services.forum.post;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.Post.CreatePostDTO;
import pwr.pracainz.DTO.forum.Post.UpdatePostDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.forum.PostUserStatusIdDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatusId;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.events.PostCreationEvent;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.exceptions.exceptions.DataException;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.PostRepository;
import pwr.pracainz.repositories.forum.PostUserStatusRepository;
import pwr.pracainz.repositories.forum.ThreadRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.DTOOperations.Deconversion.DTODeconversionInterface;
import pwr.pracainz.services.i18n.I18nServiceInterface;
import pwr.pracainz.services.user.UserServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;

import javax.transaction.Transactional;
import java.util.Objects;

@Log4j2
@Service
public class PostService implements PostServiceInterface {
	private final PostRepository postRepository;
	private final PostUserStatusRepository postUserStatusRepository;
	private final ThreadRepository threadRepository;
	private final I18nServiceInterface i18nService;
	private final UserServiceInterface userService;
	private final DTOConversionInterface dtoConversion;
	private final DTODeconversionInterface dtoDeconversion;
	private final ApplicationEventPublisher publisher;

	@Autowired
	PostService(PostRepository postRepository,
	            ThreadRepository threadRepository,
	            PostUserStatusRepository postUserStatusRepository,
	            UserServiceInterface userService,
	            I18nServiceInterface i18nService,
	            DTOConversionInterface dtoConversion,
	            DTODeconversionInterface dtoDeconversion,
	            ApplicationEventPublisher publisher) {
		this.dtoConversion = dtoConversion;
		this.postUserStatusRepository = postUserStatusRepository;
		this.userService = userService;
		this.i18nService = i18nService;
		this.postRepository = postRepository;
		this.threadRepository = threadRepository;
		this.dtoDeconversion = dtoDeconversion;
		this.publisher = publisher;
	}


	@Override
	public PageDTO<CompletePostDTO> findPostsByThread(int pageNumber, int threadId) {
		log.info("Get page {} of posts from thread {} and for user {}", pageNumber, threadId, userService.getUsernameOfCurrentUser());

		return dtoConversion.convertToDTO(
				postRepository.getAllByThread_ThreadId(threadId, PageRequest.of(pageNumber, 30, Sort.by("creation").descending()))
						.map(post -> dtoConversion.convertToDTO(
								post, post.getPostUserStatuses().stream().filter(postUserStatus -> userService.getCurrentUser().equals(postUserStatus.getPostUserStatusId().getUser())).findFirst()
										.orElseGet(() -> PostUserStatus.getEmptyPostUserStatus(post, userService.getCurrentUser(), false, false, false))
						))
		);
	}

	@Override
	public PageDTO<CompletePostDTO> createPostForThread(int threadId, CreatePostDTO createPost) {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
					String.format("User tried to create Post for thread %s without being logged in", threadId));
		}

		User currUser = userService.getCurrentUser();
		Thread thread = threadRepository.findById(threadId)
				.orElseThrow(() -> new ObjectNotFoundException(i18nService.getTranslation("forum.no-such-thread", threadId),
						String.format("Could not find thread with id: %s", threadId)));

		if (thread.getStatus().equals(ThreadStatus.CLOSED)) {
			throw new DataException(i18nService.getTranslation("forum.post-to-closed-thread-error"),
					String.format("User %s tried to add post to closed thread with title %s (id: %s)", userService.getUsernameOfCurrentUser(), thread.getTitle(), threadId));
		}

		log.info("Create post for thread with id {}(id: {}) created by user {}", thread.getTitle(), threadId, currUser.getUsername());

		Post post = dtoDeconversion.convertFromDTO(createPost);

		post.setCreator(currUser);
		post.setThread(thread);

		currUser.incrementNrOfPosts();

		publisher.publishEvent(new PostCreationEvent(post));

		postRepository.save(post);

		return findPostsByThread(0, threadId);
	}

	@Override
	public CompletePostDTO updatePostForThread(int threadId, UpdatePostDTO post) {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
					String.format("User tried to update Post for thread %s without being logged in", threadId));
		}

		User currUser = userService.getCurrentUser();
		Post oldPost = postRepository.findById(post.getPostId())
				.orElseThrow(() -> new ObjectNotFoundException(i18nService.getTranslation("forum.no-such-post", post.getPostId()),
						String.format("No post with id %s was found", post.getPostId())));

		if (!currUser.equals(oldPost.getCreator())) {
			throw new AuthenticationException(i18nService.getTranslation("forum.no-updating-others-post"),
					String.format("User %s tried to update others people post", userService.getUsernameOfCurrentUser()));
		}

		log.info("Update post {}(id: {}) created by user {}", oldPost.getTitle(), oldPost.getPostId(), currUser.getUsername());

		oldPost.setTitle(post.getTitle());
		oldPost.setPostText(post.getText());

		return dtoConversion.convertToDTO(postRepository.save(oldPost));
	}

	@Override
	@Transactional
	public PostUserStatusDTO updatePostUserStatus(int postId, PostUserStatusDTO status) {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
					"User tried to update Post status without being logged in");
		}

		User currUser = userService.getCurrentUser();
		PostUserStatusIdDTO requestUserId = status.getIds();

		if (Objects.isNull(requestUserId) || !currUser.getUserId().equals(requestUserId.getUser().getUserId())) {
			throw new AuthenticationException(i18nService.getTranslation("forum.error-during-post-status-update"),
					String.format("Error during Post status update for user %s", currUser.getUsername()));
		}

		if (status.getIds().getPost().getPostId() != postId) {
			throw new DataException(i18nService.getTranslation("forum.error-during-post-status-update"),
					String.format("Error occurred during post status update for user %s", userService.getUsernameOfCurrentUser()));
		}

		int postIndex = status.getIds().getPost().getPostId();
		Post post = postRepository.findById(postIndex)
				.orElseThrow(() -> new ObjectNotFoundException(i18nService.getTranslation("forum.no-such-post", postIndex),
						String.format("No post with id %s was found", postIndex)));

		log.info("Update post user status for post with id: {}, and for user {}", postId, currUser.getUsername());

		PostUserStatusId postUserId = new PostUserStatusId(currUser, post);

		PostUserStatus userStatus = postUserStatusRepository.findById(postUserId)
				.map(postUserStatus -> {
					if (postUserStatus.isLiked() != status.isLiked()) {
						updateNrOfPlus(postId, status);
					}
					if (postUserStatus.isDisliked() != status.isDisliked()) {
						updateNrOfMinus(postId, status);
					}
					if (!postUserStatus.isReported() && status.isReported()) {
						reportPost(postId, status);
					}

					return postUserStatus.copyDataFromDTO(status);
				})
				.orElseGet(() -> {
					updateFields(postId, status);

					return dtoDeconversion.convertFromDTO(status, postUserId);
				});

		return dtoConversion.convertToDTO(postUserStatusRepository.save(userStatus));
	}

	private void updateFields(int postId, PostUserStatusDTO status) {
		if (status.isLiked()) {
			postRepository.incrementNrOfPlusByPostId(postId);
		}
		if (status.isDisliked()) {
			postRepository.incrementNrOfMinusByPostId(postId);
		}
		if (status.isReported()) {
			postRepository.reportPostByPostId(postId);
		}
	}

	private void updateNrOfPlus(int postId, PostUserStatusDTO status) {
		if (status.isLiked()) {
			postRepository.incrementNrOfPlusByPostId(postId);
		} else {
			postRepository.decrementNrOfPlusByPostId(postId);
		}
	}

	private void updateNrOfMinus(int postId, PostUserStatusDTO status) {
		if (status.isDisliked()) {
			postRepository.incrementNrOfMinusByPostId(postId);
		} else {
			postRepository.decrementNrOfMinusByPostId(postId);
		}
	}

	private void reportPost(int postId, PostUserStatusDTO status) {
		if (status.isReported()) {
			postRepository.reportPostByPostId(postId);
		}
	}
}
