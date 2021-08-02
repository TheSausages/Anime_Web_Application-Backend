package pwr.pracainz.services;

import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.DTO.animeInfo.GradeDTO;
import pwr.pracainz.DTO.animeInfo.ReviewDTO;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.PostDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.DTO.forum.ThreadDTO;
import pwr.pracainz.DTO.user.AchievementDTO;
import pwr.pracainz.DTO.user.UserDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Grade;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.forum.Tag;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.User;

import java.util.stream.Collectors;

@Service
public class DTOConversionService {
    public UserDTO convertUserToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getNrOfPosts(),
                user.getWatchTime(),
                user.getAchievementPoints(),
                user.getAchievements().stream().map(this::convertAchievementToDTO).collect(Collectors.toSet()),
                user.getPosts().stream().map(this::convertPostToDTO).collect(Collectors.toSet())
        );
    }

    public AchievementDTO convertAchievementToDTO(Achievement achievement) {
        return new AchievementDTO(
                achievement.getAchievementId(),
                achievement.getName(),
                achievement.getDescription(),
                achievement.getIconPath(),
                achievement.getAchievementPoints(),
                achievement.getUsers().size()
        );
    }

    public PostDTO convertPostToDTO(Post post) {
        return new PostDTO(
                post.getPostId(),
                post.getTitle(),
                post.getPostText(),
                post.isBlocked(),
                post.getNrOfPlus(),
                post.getNrOfMinus(),
                convertUserToDTO(post.getUser()),
                convertThreadToThreadDTO(post.getThread())
        );
    }

    public ThreadDTO convertThreadToThreadDTO(Thread thread) {
        return new ThreadDTO(
                thread.getThreadId(),
                thread.getTitle(),
                thread.getStatus(),
                convertForumCategoryToForumDTO(thread.getCategory()),
                thread.getTags().stream().map(this::convertTagToDTO).collect(Collectors.toSet()),
                thread.getPosts().stream().map(this::convertPostToDTO).collect(Collectors.toSet())
        );
    }

    public ForumCategoryDTO convertForumCategoryToForumDTO(ForumCategory forumCategory) {
        return new ForumCategoryDTO(
                forumCategory.getCategoryId(),
                forumCategory.getCategoryName(),
                forumCategory.getCategoryDescription()
        );
    }

    public TagDTO convertTagToDTO(Tag tag) {
        return new TagDTO(
                tag.getTagId(),
                tag.getTagName(),
                tag.getTagImportance()
        );
    }

    public ReviewDTO convertReviewToDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getReviewText(),
                review.getNrOfHelpful(),
                review.getNrOfPlus(),
                review.getNrOfMinus()
        );
    }

    public GradeDTO convertGradeToDTO(Grade grade) {
        return new GradeDTO(
                grade.getGradeId(),
                grade.getScale(),
                grade.getGradeName()
        );
    }

    public AnimeUserInfoDTO convertAnimeUserInfoToDTO(AnimeUserInfo animeUserInfo) {
        return new AnimeUserInfoDTO(
                convertAnimeUserInfoIdToDTO(animeUserInfo.getAnimeUserInfoId()),
                animeUserInfo.getStatus(),
                animeUserInfo.getWatchStartDate(),
                animeUserInfo.getWatchEndDate(),
                animeUserInfo.getNrOfEpisodesSeen(),
                animeUserInfo.isFavourite(),
                animeUserInfo.isDidReview(),
                convertGradeToDTO(animeUserInfo.getGrade()),
                convertReviewToDTO(animeUserInfo.getReview())
        );
    }

    public AnimeUserInfoIdDTO convertAnimeUserInfoIdToDTO(AnimeUserInfoId animeUserInfoId) {
        return new AnimeUserInfoIdDTO(
                animeUserInfoId.getUserId(),
                animeUserInfoId.getAnimeId()
        );
    }
}
