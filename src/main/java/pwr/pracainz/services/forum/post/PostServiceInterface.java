package pwr.pracainz.services.forum.post;

import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.Post.CreatePostDTO;
import pwr.pracainz.DTO.forum.Post.UpdatePostDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;

/**
 * Interface for a Post Service. Each implementation must use this interface.
 */
public interface PostServiceInterface {
	/**
	 * Get a page of posts for a given thread.
	 * @param pageNumber Which page of posts should be returned
	 * @param threadId Id for which the posts should be found
	 * @return Page of Posts
	 */
	PageDTO<CompletePostDTO> findPostsByThread(int pageNumber, int threadId);

	/**
	 * Update post status for a user.
	 * @param postId Id of the post for which the post should be updated
	 * @param status Status to which the post user status should be updated
	 * @return The updated Post User Status
	 */
	PostUserStatusDTO updatePostUserStatus(int postId, PostUserStatusDTO status);

	/**
	 * Create a new post for a thread.
	 * @param threadId Id of the thread for which a new post should be created
	 * @param post Data of the post that should be created
	 * @return Page with the newest Posts (including the newly created one)
	 */
	PageDTO<CompletePostDTO> createPostForThread(int threadId, CreatePostDTO post);

	/**
	 * Update a given post for a given thread.
	 * @param threadId Id of the thread for which the post should be updated
	 * @param post Data used to update the post. Possesses the id of the post to be updated
	 * @return A complete DTO oof the updated Post
	 */
	CompletePostDTO updatePostForThread(int threadId, UpdatePostDTO post);
}
