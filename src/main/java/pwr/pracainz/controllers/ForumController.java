package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.ForumQuery;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.services.forum.category.ForumCategoryServiceInterface;
import pwr.pracainz.services.forum.thread.ThreadServiceInterface;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/forum")
public class ForumController {
    private final ForumCategoryServiceInterface categoryService;
    private final ThreadServiceInterface threadService;

    @Autowired
    ForumController(ForumCategoryServiceInterface forumCategoryServiceInterface, ThreadServiceInterface threadService) {
        this.categoryService = forumCategoryServiceInterface;
        this.threadService = threadService;
    }

    @GetMapping("/categories")
    public List<ForumCategoryDTO> getForumCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/newestThreads/{page}")
    public PageDTO<SimpleThreadDTO> getNewestThread(@PathVariable @Min(value = 0) int page) {
        return threadService.getNewestThreads(page);
    }

    @PostMapping("/search/{page}")
    public PageDTO<SimpleThreadDTO> searchThreads(@PathVariable @Min(value = 0) int page, @RequestBody @Valid ForumQuery query) {
        return threadService.searchThreads(page, query);
    }
}
