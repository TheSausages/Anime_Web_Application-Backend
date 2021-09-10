package pwr.pracainz.services.forum.post;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatus;
import pwr.pracainz.repositories.forum.PostRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.user.UserService;

import static pwr.pracainz.utils.UserAuthorizationUtilities.getIdOfCurrentUser;

@Log4j2
@Service
public class PostService implements PostServiceInterface {
    private final PostRepository postRepository;
    private final UserService userService;
    private final DTOConversionInterface<CompletePostDTO> dtoConversion;

    @Autowired
    PostService(PostRepository postRepository, UserService userService, DTOConversionInterface<CompletePostDTO> dtoConversion) {
        this.dtoConversion = dtoConversion;
        this.userService = userService;
        this.postRepository = postRepository;
    }


    @Override
    public PageDTO<CompletePostDTO> findPostsByThread(int pageNumber, int threadId) {
        log.info("Get page {} of posts from thread {} and for user {}", pageNumber, threadId, getIdOfCurrentUser());

        return dtoConversion.convertDomainPageToDTO(
                postRepository.getAllByThread_ThreadId(threadId, PageRequest.of(pageNumber, 30, Sort.by("creation").descending()))
                        .map(post -> dtoConversion.convertPostWithUserStatusToCompleteDTO(
                                post, post.getPostUserStatuses().stream().filter(postUserStatus -> postUserStatus.getPostUserStatusId().getUser().equals(userService.getCurrentUser())).findFirst()
                                        .orElseGet(() -> PostUserStatus.getEmptyPostUserStatus(post, userService.getCurrentUser(), false, false, false))
                        ))
        );
    }
}
