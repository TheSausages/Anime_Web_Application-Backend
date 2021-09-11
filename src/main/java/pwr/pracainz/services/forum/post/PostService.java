package pwr.pracainz.services.forum.post;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.forum.PostUserStatusIdDTO;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatusId;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.PostRepository;
import pwr.pracainz.repositories.forum.PostUserStatusRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.DTOOperations.DTODeconversion.DTODeconversionInterface;
import pwr.pracainz.services.user.UserService;

import java.util.Objects;

import static pwr.pracainz.utils.UserAuthorizationUtilities.checkIfLoggedUser;
import static pwr.pracainz.utils.UserAuthorizationUtilities.getIdOfCurrentUser;

@Log4j2
@Service
public class PostService implements PostServiceInterface {
    private final PostRepository postRepository;
    private final PostUserStatusRepository postUserStatusRepository;
    private final UserService userService;
    private final DTOConversionInterface<CompletePostDTO> dtoConversion;
    private final DTODeconversionInterface dtoDeconversion;

    @Autowired
    PostService(PostRepository postRepository, PostUserStatusRepository postUserStatusRepository, UserService userService, DTOConversionInterface<CompletePostDTO> dtoConversion, DTODeconversionInterface dtoDeconversion) {
        this.dtoConversion = dtoConversion;
        this.postUserStatusRepository = postUserStatusRepository;
        this.userService = userService;
        this.postRepository = postRepository;
        this.dtoDeconversion = dtoDeconversion;
    }


    @Override
    public PageDTO<CompletePostDTO> findPostsByThread(int pageNumber, int threadId) {
        log.info("Get page {} of posts from thread {} and for user {}", pageNumber, threadId, getIdOfCurrentUser());

        return dtoConversion.convertToDTO(
                postRepository.getAllByThread_ThreadId(threadId, PageRequest.of(pageNumber, 30, Sort.by("creation").descending()))
                        .map(post -> dtoConversion.convertToDTO(
                                post, post.getPostUserStatuses().stream().filter(postUserStatus -> postUserStatus.getPostUserStatusId().getUser().equals(userService.getCurrentUser())).findFirst()
                                        .orElseGet(() -> PostUserStatus.getEmptyPostUserStatus(post, userService.getCurrentUserOrInsert(), false, false, false))
                        ))
        );
    }

    @Override
    public PostUserStatusDTO updatePostUserStatus(int postId, PostUserStatusDTO status) {
        if (!checkIfLoggedUser()) {
            throw new AuthenticationException("You are not logged in!");
        }

        User currUser = userService.getCurrentUserOrInsert();
        PostUserStatusIdDTO requestUserId = status.getIds();

        if (Objects.nonNull(requestUserId) && !currUser.getUserId().equals(requestUserId.getUser().getUserId())) {
            throw new AuthenticationException("Updating the post information was not successful - please try again");
        }

        Post post = postRepository.findById(status.getIds().getPost().getPostId())
                .orElseThrow(() -> new ObjectNotFoundException("Selected post not found!"));

        PostUserStatusId postUserId = new PostUserStatusId(currUser, post);

        PostUserStatus userStatus = postUserStatusRepository.findById(postUserId)
                .map(postUserStatus -> postUserStatus.copyDataFromDTO(status))
                .orElse(dtoDeconversion.convertFromDTO(status, postUserId));

        return dtoConversion.convertToDTO(postUserStatusRepository.save(userStatus));
    }
}
