package pwr.pracainz.services.forum.post;

import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;

public interface PostServiceInterface {
    PageDTO<CompletePostDTO> findPostsByThread(int pageNumber, int threadId);

    PostUserStatusDTO updatePostUserStatus(int postId, PostUserStatusDTO status);
}
