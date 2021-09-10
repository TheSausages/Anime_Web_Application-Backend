package pwr.pracainz.services.forum.post;

import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;

public interface PostServiceInterface {
    PageDTO<CompletePostDTO> findPostsByThread(int pageNumber, int threadId);
}
