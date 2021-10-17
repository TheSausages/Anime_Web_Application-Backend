package pwr.pracainz.services.forum.post;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.PostRepository;
import pwr.pracainz.repositories.forum.PostUserStatusRepository;
import pwr.pracainz.repositories.forum.ThreadRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.DTOOperations.Deconversion.DTODeconversionInterface;
import pwr.pracainz.services.user.UserServiceInterface;

import javax.transaction.Transactional;
import java.util.Objects;

import static pwr.pracainz.utils.UserAuthorizationUtilities.checkIfLoggedUser;
import static pwr.pracainz.utils.UserAuthorizationUtilities.getIdOfCurrentUser;

@Log4j2
@Service
public class PostService implements PostServiceInterface {
	private final PostRepository postRepository;
	private final PostUserStatusRepository postUserStatusRepository;
	private final ThreadRepository threadRepository;
	private final UserServiceInterface userService;
	private final DTOConversionInterface dtoConversion;
	private final DTODeconversionInterface dtoDeconversion;

	@Autowired
	PostService(PostRepository postRepository, ThreadRepository threadRepository, PostUserStatusRepository postUserStatusRepository, UserServiceInterface userService, DTOConversionInterface dtoConversion, DTODeconversionInterface dtoDeconversion) {
		this.dtoConversion = dtoConversion;
		this.postUserStatusRepository = postUserStatusRepository;
		this.userService = userService;
		this.postRepository = postRepository;
		this.threadRepository = threadRepository;
		this.dtoDeconversion = dtoDeconversion;
	}


	@Override
	public PageDTO<CompletePostDTO> findPostsByThread(int pageNumber, int threadId) {
		log.info("Get page {} of posts from thread {} and for user with id {}", pageNumber, threadId, getIdOfCurrentUser());

		return dtoConversion.convertToDTO(
				postRepository.getAllByThread_ThreadId(threadId, PageRequest.of(pageNumber, 30, Sort.by("creation").descending()))
						.map(post -> dtoConversion.convertToDTO(
								post, post.getPostUserStatuses().stream().filter(postUserStatus -> userService.getCurrentUser().equals(postUserStatus.getPostUserStatusId().getUser())).findFirst()
										.orElseGet(() -> PostUserStatus.getEmptyPostUserStatus(post, userService.getCurrentUser(), false, false, false))
						))
		);
	}

	@Override
	@Transactional
	public PostUserStatusDTO updatePostUserStatus(int postId, PostUserStatusDTO status) {
		if (!checkIfLoggedUser()) {
			throw new AuthenticationException("You are not logged in!");
		}

		User currUser = userService.getCurrentUser();
		PostUserStatusIdDTO requestUserId = status.getIds();

		if (Objects.nonNull(requestUserId) && !currUser.getUserId().equals(requestUserId.getUser().getUserId())) {
			throw new AuthenticationException("Updating the post information was not successful - please try again");
		}

		if (status.getIds().getPost().getPostId() != postId) {
			throw new IllegalArgumentException("Error occurred during update!");
		}

		Post post = postRepository.findById(status.getIds().getPost().getPostId())
				.orElseThrow(() -> new ObjectNotFoundException("Selected post not found!"));

		log.info("Update post user status for post with id: {}, and for user {}", postId, currUser.getUserId());

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

	@Override
	public PageDTO<CompletePostDTO> createPostForThread(int threadId, CreatePostDTO createPost) {
		if (!checkIfLoggedUser()) {
			throw new AuthenticationException("You are not logged in!");
		}

		User currUser = userService.getCurrentUser();
		Thread thread = threadRepository.findById(threadId)
				.orElseThrow(() -> new ObjectNotFoundException("Could not find thread with id: " + threadId));

		if (thread.getStatus().equals(ThreadStatus.CLOSED)) {
			throw new IllegalStateException("Posts cannot be added for a closed thread!");
		}

		log.info("Create post for thread with id {}(id: {}) created by user {}", thread.getTitle(), threadId, currUser.getUsername());

		Post post = dtoDeconversion.convertFromDTO(createPost);
		post.setUser(currUser);
		post.setThread(thread);

		postRepository.save(post);

		return findPostsByThread(0, threadId);
	}

	@Override
	public CompletePostDTO updatePostForThread(int threadId, UpdatePostDTO post) {
		if (!checkIfLoggedUser()) {
			throw new AuthenticationException("You are not logged in!");
		}

		User currUser = userService.getCurrentUser();
		Post oldPost = postRepository.findById(post.getPostId())
				.orElseThrow(() -> new ObjectNotFoundException("No post found for id: " + post.getPostId()));

		if (!currUser.equals(oldPost.getUser())) {
			throw new AuthenticationException("You can't update another persons post!");
		}

		log.info("Update post {}(id: {}) created by user {}", oldPost.getTitle(), oldPost.getPostId(), currUser.getUsername());

		oldPost.setTitle(post.getTitle());
		oldPost.setPostText(post.getText());

		return dtoConversion.convertToDTO(postRepository.save(oldPost));
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
