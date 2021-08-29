package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.services.forum.category.ForumCategoryServiceInterface;
import pwr.pracainz.services.forum.thread.ThreadServiceInterface;

import javax.validation.constraints.Positive;
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

    @GetMapping("/newestThread/{page}")
    public PageDTO getNewestThread(@PathVariable @Positive int page) {
        return threadService.getNewestThreads(page);
    }
}
