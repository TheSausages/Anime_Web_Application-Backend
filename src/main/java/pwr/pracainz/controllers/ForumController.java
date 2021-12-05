package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.*;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.Post.CreatePostDTO;
import pwr.pracainz.DTO.forum.Post.UpdatePostDTO;
import pwr.pracainz.DTO.forum.Thread.CompleteThreadDTO;
import pwr.pracainz.DTO.forum.Thread.CreateThreadDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.forum.Thread.UpdateThreadDTO;
import pwr.pracainz.services.forum.category.ForumCategoryServiceInterface;
import pwr.pracainz.services.forum.post.PostServiceInterface;
import pwr.pracainz.services.forum.tag.TagServiceInterface;
import pwr.pracainz.services.forum.thread.ThreadServiceInterface;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Controller for request connected to Forum: Tags, Categories, Thead, Posts and Thread/Post User Statuses.
 */
@RestControllerWithBasePath("${api.default-path}/forum")
public class ForumController {
	private final ForumCategoryServiceInterface categoryService;
	private final ThreadServiceInterface threadService;
	private final PostServiceInterface postService;
	private final TagServiceInterface tagService;

	@Autowired
	ForumController(ForumCategoryServiceInterface forumCategoryServiceInterface, ThreadServiceInterface threadService, PostServiceInterface postService, TagServiceInterface tagService) {
		this.categoryService = forumCategoryServiceInterface;
		this.threadService = threadService;
		this.postService = postService;
		this.tagService = tagService;
	}

	@GetMapping("/tags")
	public List<TagDTO> getTags() {
		return tagService.getAllTags();
	}

	@GetMapping("/categories")
	public List<ForumCategoryDTO> getForumCategories() {
		return categoryService.getAllCategories();
	}

	@PostMapping("/thread/search/{pageNumber}")
	public PageDTO<SimpleThreadDTO> searchThreads(@PathVariable @Min(value = 0) int pageNumber, @RequestBody @Valid ForumQuery query) {
		return threadService.searchThreads(pageNumber, query);
	}

	@GetMapping("/thread/newest/{pageNumber}")
	public PageDTO<SimpleThreadDTO> getNewestThreads(@PathVariable @Min(value = 0) int pageNumber) {
		return threadService.getNewestThreads(pageNumber);
	}

	@PostMapping("/thread")
	public SimpleThreadDTO newThread(@RequestBody @Valid CreateThreadDTO newThread) {
		return threadService.createThread(newThread);
	}

	@GetMapping("/thread/{threadId}")
	public CompleteThreadDTO getThreadById(@PathVariable @Positive int threadId) {
		return threadService.getThreadById(threadId);
	}

	@PutMapping("/thread/{threadId}")
	public CompleteThreadDTO updateThread(@PathVariable @Positive int threadId,
	                                      @RequestBody @Valid UpdateThreadDTO thread) {
		return threadService.updateThread(threadId, thread);
	}

	@PostMapping("/thread/{threadId}/post")
	public PageDTO<CompletePostDTO> createPostForThread(@PathVariable @Positive int threadId,
	                                                    @RequestBody @Valid CreatePostDTO post) {
		return postService.createPostForThread(threadId, post);
	}

	@PutMapping("/thread/{threadId}/post")
	public CompletePostDTO updatePostForThread(@PathVariable @Positive int threadId,
	                                           @RequestBody @Valid UpdatePostDTO post) {
		return postService.updatePostForThread(threadId, post);
	}

	@GetMapping("/thread/{threadId}/post/{pageNumber}")
	public PageDTO<CompletePostDTO> getPostPageForThread(@PathVariable @Positive int threadId,
	                                                     @PathVariable @Positive int pageNumber) {
		return postService.findPostsByThread(pageNumber, threadId);
	}

	@PutMapping("/post/{postId}")
	public PostUserStatusDTO updateUserPostStatus(@PathVariable @Positive int postId,
	                                              @RequestBody @Valid PostUserStatusDTO status) {
		return postService.updatePostUserStatus(postId, status);
	}

	@PutMapping("/thread/{threadId}/status")
	public ThreadUserStatusDTO updateThreadUserStatus(@PathVariable @Positive int threadId,
	                                                  @RequestBody @Valid ThreadUserStatusDTO status) {
		return threadService.updateThreadUserStatus(threadId, status);
	}
}
