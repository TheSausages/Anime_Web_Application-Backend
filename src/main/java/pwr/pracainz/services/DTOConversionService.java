package pwr.pracainz.services;

import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.DTO.animeInfo.GradeDTO;
import pwr.pracainz.DTO.animeInfo.ReviewDTO;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.Post.SimplePostDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.DTO.forum.Thread.CompleteThreadDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusIdDTO;
import pwr.pracainz.DTO.user.AchievementDTO;
import pwr.pracainz.DTO.user.CompleteUserDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Grade;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.forum.*;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.User;

import java.util.stream.Collectors;

@Service
public class DTOConversionService {
    public CompleteUserDTO convertUserToCompleteDTO(User user) {
        return new CompleteUserDTO(
                convertUserToSimpleDTO(user),
                user.getAchievements().stream().map(this::convertAchievementToDTO).collect(Collectors.toSet()),
                user.getAnimeUserInfo().stream().map(this::convertAnimeUserInfoToDTO).collect(Collectors.toSet()),
                user.getThreadUserStatuses().stream().map(this::convertThreadUserStatusToDTO).collect(Collectors.toSet()),
                user.getPosts().stream().map(this::convertPostToCompleteDTO).collect(Collectors.toSet())
        );
    }

    public SimpleUserDTO convertUserToSimpleDTO(User user) {
        return new SimpleUserDTO(
                user.getUserId(),
                user.getNrOfPosts(),
                user.getWatchTime(),
                user.getAchievementPoints()
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

    public SimplePostDTO convertPostToSimpleDTO(Post post) {
        return new SimplePostDTO(
                post.getPostId(),
                post.getTitle(),
                post.isBlocked(),
                convertUserToSimpleDTO(post.getUser())
        );
    }

    public CompletePostDTO convertPostToCompleteDTO(Post post) {
        return new CompletePostDTO(
                convertPostToSimpleDTO(post),
                post.getPostText(),
                post.getNrOfPlus(),
                post.getNrOfMinus(),
                convertThreadToSimpleDTO(post.getThread())
        );
    }

    public SimpleThreadDTO convertThreadToSimpleDTO(Thread thread) {
        return new SimpleThreadDTO(
                thread.getThreadId(),
                thread.getTitle(),
                thread.getStatus(),
                convertForumCategoryToForumDTO(thread.getCategory()),
                thread.getTags().stream().map(this::convertTagToDTO).collect(Collectors.toSet())
        );
    }

    public CompleteThreadDTO convertThreadToCompleteDTO(Thread thread) {
        return new CompleteThreadDTO(
                convertThreadToSimpleDTO(thread),
                thread.getPosts().stream().map(this::convertPostToSimpleDTO).collect(Collectors.toSet())
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
                convertUserToSimpleDTO(animeUserInfoId.getUser()),
                animeUserInfoId.getAnimeId()
        );
    }

    public ThreadUserStatusIdDTO convertThreadUserStatusIdToDTO(ThreadUserStatusId threadUserStatusId) {
        return new ThreadUserStatusIdDTO(
                convertUserToSimpleDTO(threadUserStatusId.getUser()),
                convertThreadToSimpleDTO(threadUserStatusId.getThread())
        );
    }

    public ThreadUserStatusDTO convertThreadUserStatusToDTO(ThreadUserStatus threadUserStatus) {
        return new ThreadUserStatusDTO(
                convertThreadUserStatusIdToDTO(threadUserStatus.getThreadUserStatusId()),
                threadUserStatus.isWatching(),
                threadUserStatus.isBlocked()
        );
    }
}
