package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.ForumQuery;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.Post.CreatePostDTO;
import pwr.pracainz.DTO.forum.Post.UpdatePostDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.forum.TagDTO;
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

@RestController
@RequestMapping("/forum")
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

    @PostMapping("/search/{page}")
    public PageDTO<SimpleThreadDTO> searchThreads(@PathVariable @Min(value = 0) int page, @RequestBody @Valid ForumQuery query) {
        return threadService.searchThreads(page, query);
    }

    @GetMapping("/thread/newest/{page}")
    public PageDTO<SimpleThreadDTO> getNewestThread(@PathVariable @Min(value = 0) int page) {
        return threadService.getNewestThreads(page);
    }

    @GetMapping("/thread/{id}")
    public CompleteThreadDTO getThreadById(@PathVariable @Positive int id) {
        return threadService.getThreadById(id);
    }

    @GetMapping("/thread/{threadId}/posts/{page}")
    public PageDTO<CompletePostDTO> getPostPageForThread(@PathVariable @Positive int threadId,
                                                         @PathVariable @Positive int page) {
        return postService.findPostsByThread(page, threadId);
    }

    @PutMapping("/post/{id}")
    public PostUserStatusDTO updateUserPostStatus(@PathVariable @Positive int id,
                                                  @RequestBody @Valid PostUserStatusDTO status) {
        return postService.updatePostUserStatus(id, status);
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

    @PostMapping("/thread")
    public SimpleThreadDTO newThread(@RequestBody @Valid CreateThreadDTO newThread) {
        return threadService.createThread(newThread);
    }

    @PutMapping("/thread/{threadId}")
    public CompleteThreadDTO updateThread(@PathVariable @Positive int threadId,
                                          @RequestBody @Valid UpdateThreadDTO thread) {
        return threadService.updateThread(threadId, thread);
    }
}
