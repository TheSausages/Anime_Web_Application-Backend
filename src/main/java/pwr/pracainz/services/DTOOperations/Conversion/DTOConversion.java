package pwr.pracainz.services.DTOOperations.Conversion;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.DTO.animeInfo.ReviewDTO;
import pwr.pracainz.DTO.forum.*;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.Post.SimplePostDTO;
import pwr.pracainz.DTO.forum.Thread.CompleteThreadDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
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
    public PageDTO<T> convertToDTO(Page<T> page) {
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
    public CompleteUserDTO convertToDTO(User user) {
        return new CompleteUserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getNrOfPosts(),
                user.getWatchTime(),
                user.getAchievementPoints(),
                user.getAchievements().stream().map(this::convertToDTO).collect(Collectors.toSet()),
                user.getAnimeUserInfo().stream().map(this::convertToDTO).collect(Collectors.toSet()),
                user.getThreadUserStatuses().stream().map(this::convertToDTO).collect(Collectors.toSet()),
                user.getPosts().stream().map(this::convertToDTO).collect(Collectors.toSet())
        );
    }

    @Override
    public SimpleUserDTO convertToSimpleDTO(User user) {
        return new SimpleUserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getNrOfPosts(),
                user.getWatchTime(),
                user.getAchievementPoints()
        );
    }

    @Override
    public AchievementDTO convertToDTO(Achievement achievement) {
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
    public SimplePostDTO convertToSimpleDTO(Post post) {
        return new SimplePostDTO(
                post.getPostId(),
                post.getTitle(),
                post.isBlocked(),
                post.getCreation(),
                post.getModification(),
                convertToSimpleDTO(post.getUser())
        );
    }

    @Override
    public CompletePostDTO convertToDTO(Post post) {
        return new CompletePostDTO(
                post.getPostId(),
                post.getTitle(),
                post.isBlocked(),
                post.getCreation(),
                post.getModification(),
                convertToSimpleDTO(post.getUser()),
                post.getPostText(),
                post.getNrOfPlus(),
                post.getNrOfMinus()
        );
    }

    @Override
    public CompletePostDTO convertToDTO(Post post, PostUserStatus status) {
        return new CompletePostDTO(
                post.getPostId(),
                post.getTitle(),
                post.isBlocked(),
                post.getCreation(),
                post.getModification(),
                convertToSimpleDTO(post.getUser()),
                post.getPostText(),
                post.getNrOfPlus(),
                post.getNrOfMinus(),
                this.convertToDTO(status)
        );
    }

    @Override
    public SimpleThreadDTO convertToSimpleDTO(Thread thread) {
        return new SimpleThreadDTO(
                thread.getThreadId(),
                thread.getTitle(),
                thread.getNrOfPosts(),
                thread.getStatus(),
                thread.getCreation(),
                thread.getModification(),
                convertToSimpleDTO(thread.getCreator()),
                convertToDTO(thread.getCategory()),
                thread.getTags().stream().map(this::convertToDTO).collect(Collectors.toList())
        );
    }

    @Override
    public CompleteThreadDTO convertToDTO(Thread thread, PageDTO<CompletePostDTO> posts) {
        return new CompleteThreadDTO(
                thread.getThreadId(),
                thread.getTitle(),
                thread.getThreadText(),
                thread.getNrOfPosts(),
                thread.getStatus(),
                thread.getCreation(),
                thread.getModification(),
                convertToSimpleDTO(thread.getCreator()),
                convertToDTO(thread.getCategory()),
                thread.getTags().stream().map(this::convertToDTO).collect(Collectors.toList()),
                posts
        );
    }

    @Override
    public ForumCategoryDTO convertToDTO(ForumCategory forumCategory) {
        return new ForumCategoryDTO(
                forumCategory.getCategoryId(),
                forumCategory.getCategoryName(),
                forumCategory.getCategoryDescription()
        );
    }

    @Override
    public TagDTO convertToDTO(Tag tag) {
        return new TagDTO(
                tag.getTagId(),
                tag.getTagName(),
                tag.getTagImportance(),
                tag.getTagColor()
        );
    }

    @Override
    public ReviewDTO convertToDTO(Review review) {
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
    public AnimeUserInfoDTO convertToDTO(AnimeUserInfo animeUserInfo) {
        return new AnimeUserInfoDTO(
                convertToDTO(animeUserInfo.getAnimeUserInfoId()),
                animeUserInfo.getStatus(),
                animeUserInfo.getWatchStartDate() != null ? animeUserInfo.getWatchStartDate() : null,
                animeUserInfo.getWatchEndDate() != null ? animeUserInfo.getWatchEndDate() : null,
                animeUserInfo.getNrOfEpisodesSeen(),
                animeUserInfo.isFavourite(),
                animeUserInfo.getModification(),
                animeUserInfo.isDidReview(),
                animeUserInfo.getGrade() != null ? animeUserInfo.getGrade() : null,
                animeUserInfo.getReview() != null ? convertToDTO(animeUserInfo.getReview()) : null
        );
    }

    @Override
    public AnimeUserInfoIdDTO convertToDTO(AnimeUserInfoId animeUserInfoId) {
        return new AnimeUserInfoIdDTO(
                convertToSimpleDTO(animeUserInfoId.getUser()),
                animeUserInfoId.getAnimeId()
        );
    }

    @Override
    public ThreadUserStatusIdDTO convertToDTO(ThreadUserStatusId threadUserStatusId) {
        return new ThreadUserStatusIdDTO(
                convertToSimpleDTO(threadUserStatusId.getUser()),
                convertToSimpleDTO(threadUserStatusId.getThread())
        );
    }

    @Override
    public ThreadUserStatusDTO convertToDTO(ThreadUserStatus threadUserStatus) {
        return new ThreadUserStatusDTO(
                convertToDTO(threadUserStatus.getThreadUserStatusId()),
                threadUserStatus.isWatching(),
                threadUserStatus.isBlocked()
        );
    }

    @Override
    public PostUserStatusIdDTO convertToDTO(PostUserStatusId postUserStatusId) {
        return new PostUserStatusIdDTO(
                convertToSimpleDTO(postUserStatusId.getUser()),
                convertToSimpleDTO(postUserStatusId.getPost())
        );
    }

    @Override
    public PostUserStatusDTO convertToDTO(PostUserStatus postUserStatus) {
        return new PostUserStatusDTO(
                convertToDTO(postUserStatus.getPostUserStatusId()),
                postUserStatus.isLiked(),
                postUserStatus.isDisliked(),
                postUserStatus.isReported()
        );
    }

    @Override
    public AuthenticationTokenDTO convertToDTO(AuthenticationToken authenticationToken) {
        return new AuthenticationTokenDTO(
                authenticationToken.getAccess_token(),
                authenticationToken.getExpires_in(),
                authenticationToken.getRefresh_token(),
                authenticationToken.getToken_type()
        );
    }
}
