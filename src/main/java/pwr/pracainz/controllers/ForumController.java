package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.repositories.animeInfo.GradeRepository;
import pwr.pracainz.repositories.animeInfo.ReviewRepository;
import pwr.pracainz.repositories.user.AchievementRepository;
import pwr.pracainz.repositories.user.UserRepository;
import pwr.pracainz.services.DTOConversionService;

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
    private final DTOConversionService conversionService;

    @Autowired
    ForumController(UserRepository userRepository, DTOConversionService conversionService, AchievementRepository achievementRepository
    , GradeRepository gradeRepository, ReviewRepository reviewRepository, AnimeUserInfoRepository animeUserInfoRepository) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.gradeRepository = gradeRepository;
        this.reviewRepository = reviewRepository;
        this.animeUserInfoRepository = animeUserInfoRepository;
        this.achievementRepository = achievementRepository;
    }

    @GetMapping("/1")
    public List<?> now1() {
        return userRepository.findAll().stream().map(conversionService::convertUserToDTO).collect(Collectors.toList());
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
}
