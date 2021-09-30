package pwr.pracainz.services.forum.thread;

import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumQuery;
import pwr.pracainz.DTO.forum.Thread.CompleteThreadDTO;
import pwr.pracainz.DTO.forum.Thread.CreateThreadDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.entities.databaseerntities.forum.Thread;

public interface ThreadServiceInterface {
    PageDTO<SimpleThreadDTO> getNewestThreads(int pageNumber);

    PageDTO<SimpleThreadDTO> searchThreads(int pageNumber, ForumQuery forumQuery);

    CompleteThreadDTO getThreadById(int id);

    Thread getNonDTOThreadById(int id);

    CompleteThreadDTO createThread(CreateThreadDTO thread);
}
