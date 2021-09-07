package pwr.pracainz.services.DTOOperations.Conversion;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
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
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.animeInfo.Review;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.forum.*;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.entities.userauthentification.AuthenticationToken;

import java.util.stream.Collectors;

@Service
public class DTOConversion<T> implements DTOConversionInterface<T> {
    @Override
    public PageDTO<T> convertDomainPageToDTO(Page<T> page) {
        return new PageDTO<>(
                page.toList(),
                page.getNumberOfElements(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages(),
                page.isLast(),
                page.isEmpty()
        );
    }

    @Override
    public CompleteUserDTO convertUserToCompleteDTO(User user) {
        return new CompleteUserDTO(
                convertUserToSimpleDTO(user),
                user.getAchievements().stream().map(this::convertAchievementToDTO).collect(Collectors.toSet()),
                user.getAnimeUserInfo().stream().map(this::convertAnimeUserInfoToDTO).collect(Collectors.toSet()),
                user.getThreadUserStatuses().stream().map(this::convertThreadUserStatusToDTO).collect(Collectors.toSet()),
                user.getPosts().stream().map(this::convertPostToCompleteDTO).collect(Collectors.toSet())
        );
    }

    @Override
    public SimpleUserDTO convertUserToSimpleDTO(User user) {
        return new SimpleUserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getNrOfPosts(),
                user.getWatchTime(),
                user.getAchievementPoints()
        );
    }

    @Override
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

    @Override
    public SimplePostDTO convertPostToSimpleDTO(Post post) {
        return new SimplePostDTO(
                post.getPostId(),
                post.getTitle(),
                post.isBlocked(),
                post.getCreation(),
                post.getModification(),
                convertUserToSimpleDTO(post.getUser())
        );
    }

    @Override
    public CompletePostDTO convertPostToCompleteDTO(Post post) {
        return new CompletePostDTO(
                post.getPostId(),
                post.getTitle(),
                post.isBlocked(),
                post.getCreation(),
                post.getModification(),
                convertUserToSimpleDTO(post.getUser()),
                post.getPostText(),
                post.getNrOfPlus(),
                post.getNrOfMinus()
        );
    }

    @Override
    public SimpleThreadDTO convertThreadToSimpleDTO(Thread thread) {
        return new SimpleThreadDTO(
                thread.getThreadId(),
                thread.getTitle(),
                thread.getNrOfPosts(),
                thread.getStatus(),
                thread.getCreation(),
                thread.getModification(),
                convertUserToSimpleDTO(thread.getCreator()),
                convertForumCategoryToForumDTO(thread.getCategory()),
                thread.getTags().stream().map(this::convertTagToDTO).collect(Collectors.toList())
        );
    }

    @Override
    public CompleteThreadDTO convertThreadToCompleteDTO(Thread thread) {
        return new CompleteThreadDTO(
                thread.getThreadId(),
                thread.getTitle(),
                thread.getThreadText(),
                thread.getNrOfPosts(),
                thread.getStatus(),
                thread.getCreation(),
                thread.getModification(),
                convertUserToSimpleDTO(thread.getCreator()),
                convertForumCategoryToForumDTO(thread.getCategory()),
                thread.getTags().stream().map(this::convertTagToDTO).collect(Collectors.toList()),
                thread.getPosts().stream().map(this::convertPostToCompleteDTO).collect(Collectors.toSet())
        );
    }

    @Override
    public ForumCategoryDTO convertForumCategoryToForumDTO(ForumCategory forumCategory) {
        return new ForumCategoryDTO(
                forumCategory.getCategoryId(),
                forumCategory.getCategoryName(),
                forumCategory.getCategoryDescription()
        );
    }

    @Override
    public TagDTO convertTagToDTO(Tag tag) {
        return new TagDTO(
                tag.getTagId(),
                tag.getTagName(),
                tag.getTagImportance(),
                tag.getTagColor()
        );
    }

    @Override
    public ReviewDTO convertReviewToDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getReviewTitle(),
                review.getReviewText(),
                review.getNrOfHelpful(),
                review.getNrOfPlus(),
                review.getNrOfMinus()
        );
    }

    @Override
    public AnimeUserInfoDTO convertAnimeUserInfoToDTO(AnimeUserInfo animeUserInfo) {
        return new AnimeUserInfoDTO(
                convertAnimeUserInfoIdToDTO(animeUserInfo.getAnimeUserInfoId()),
                animeUserInfo.getStatus(),
                animeUserInfo.getWatchStartDate() != null ? animeUserInfo.getWatchStartDate() : null,
                animeUserInfo.getWatchEndDate() != null ? animeUserInfo.getWatchEndDate() : null,
                animeUserInfo.getNrOfEpisodesSeen(),
                animeUserInfo.isFavourite(),
                animeUserInfo.getModification(),
                animeUserInfo.isDidReview(),
                animeUserInfo.getGrade() != null ? animeUserInfo.getGrade() : null,
                animeUserInfo.getReview() != null ? convertReviewToDTO(animeUserInfo.getReview()) : null
        );
    }

    @Override
    public AnimeUserInfoIdDTO convertAnimeUserInfoIdToDTO(AnimeUserInfoId animeUserInfoId) {
        return new AnimeUserInfoIdDTO(
                convertUserToSimpleDTO(animeUserInfoId.getUser()),
                animeUserInfoId.getAnimeId()
        );
    }

    @Override
    public ThreadUserStatusIdDTO convertThreadUserStatusIdToDTO(ThreadUserStatusId threadUserStatusId) {
        return new ThreadUserStatusIdDTO(
                convertUserToSimpleDTO(threadUserStatusId.getUser()),
                convertThreadToSimpleDTO(threadUserStatusId.getThread())
        );
    }

    @Override
    public ThreadUserStatusDTO convertThreadUserStatusToDTO(ThreadUserStatus threadUserStatus) {
        return new ThreadUserStatusDTO(
                convertThreadUserStatusIdToDTO(threadUserStatus.getThreadUserStatusId()),
                threadUserStatus.isWatching(),
                threadUserStatus.isBlocked()
        );
    }

    @Override
    public AuthenticationTokenDTO convertAuthenticationTokenToDTO(AuthenticationToken authenticationToken) {
        return new AuthenticationTokenDTO(
                authenticationToken.getAccess_token(),
                authenticationToken.getExpires_in(),
                authenticationToken.getRefresh_token(),
                authenticationToken.getToken_type()
        );
    }
}
