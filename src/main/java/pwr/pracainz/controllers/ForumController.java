package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.repositories.animeInfo.GradeRepository;
import pwr.pracainz.repositories.animeInfo.ReviewRepository;
import pwr.pracainz.repositories.forum.*;
import pwr.pracainz.repositories.user.AchievementRepository;
import pwr.pracainz.repositories.user.UserRepository;
import pwr.pracainz.services.DTOConvension.DTOConversion;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum")
public class ForumController {
    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final GradeRepository gradeRepository;
    private final ReviewRepository reviewRepository;
    private final AnimeUserInfoRepository animeUserInfoRepository;
    private final ForumCategoryRepository forumCategoryRepository;
    private final TagRepository tagRepository;
    private final ThreadRepository threadRepository;
    private final PostRepository postRepository;
    private final ThreadUserStatusRepository threadUserStatusRepository;
    private final DTOConversion conversionService;

    @Autowired
    ForumController(UserRepository userRepository, DTOConversion conversionService, AchievementRepository achievementRepository
            , GradeRepository gradeRepository, ReviewRepository reviewRepository, AnimeUserInfoRepository animeUserInfoRepository, ForumCategoryRepository forumCategoryRepository,
                    TagRepository tagRepository, ThreadRepository threadRepository, PostRepository postRepository, ThreadUserStatusRepository threadUserStatusRepository) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.gradeRepository = gradeRepository;
        this.reviewRepository = reviewRepository;
        this.animeUserInfoRepository = animeUserInfoRepository;
        this.achievementRepository = achievementRepository;
        this.forumCategoryRepository = forumCategoryRepository;
        this.tagRepository = tagRepository;
        this.threadRepository = threadRepository;
        this.postRepository = postRepository;
        this.threadUserStatusRepository = threadUserStatusRepository;
    }

    @GetMapping("/1")
    public List<?> now1() {
        return userRepository.findAll().stream().map(conversionService::convertUserToCompleteDTO).collect(Collectors.toList());
    }

    @GetMapping("/2")
    public List<?> now2() {
        return achievementRepository.findAll().stream().map(conversionService::convertAchievementToDTO).collect(Collectors.toList());
    }

    @GetMapping("/3")
    public List<?> now3() {
        return gradeRepository.findAll().stream().map(conversionService::convertGradeToDTO).collect(Collectors.toList());
    }

    @GetMapping("/4")
    public List<?> now4() {
        return reviewRepository.findAll().stream().map(conversionService::convertReviewToDTO).collect(Collectors.toList());
    }

    @GetMapping("/5")
    public List<?> now5() {
        return animeUserInfoRepository.findAll().stream().map(conversionService::convertAnimeUserInfoToDTO).collect(Collectors.toList());
    }

    @GetMapping("/6")
    public List<?> now6() {
        return tagRepository.findAll().stream().map(conversionService::convertTagToDTO).collect(Collectors.toList());
    }

    @GetMapping("/7")
    public List<?> now7() {
        return forumCategoryRepository.findAll().stream().map(conversionService::convertForumCategoryToForumDTO).collect(Collectors.toList());
    }

    @GetMapping("/8")
    public List<?> now8() {
        return threadUserStatusRepository.findAll().stream().map(conversionService::convertThreadUserStatusToDTO).collect(Collectors.toList());
    }

    @GetMapping("/9")
    public List<?> now9() {
        return threadRepository.findAll().stream().map(conversionService::convertThreadToCompleteDTO).collect(Collectors.toList());
    }

    @GetMapping("/10")
    public List<?> now10() {
        return postRepository.findAll().stream().map(conversionService::convertPostToCompleteDTO).collect(Collectors.toList());
    }
}
