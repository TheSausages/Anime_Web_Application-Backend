package pwr.pracainz.services.forum.thread;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumQuery;
import pwr.pracainz.DTO.forum.Thread.CompleteThreadDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.ThreadRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.forum.post.PostServiceInterface;
import pwr.pracainz.services.user.UserServiceInterface;

@Log4j2
@Service
public class ThreadService implements ThreadServiceInterface {
    private final ThreadRepository threadRepository;
    private final PostServiceInterface postService;
    private final UserServiceInterface userService;
    private final DTOConversionInterface<SimpleThreadDTO> dtoConversion;

    @Autowired
    ThreadService(ThreadRepository threadRepository, PostServiceInterface postService, UserServiceInterface userService, DTOConversionInterface<SimpleThreadDTO> dtoConversion) {
        this.threadRepository = threadRepository;
        this.postService = postService;
        this.userService = userService;
        this.dtoConversion = dtoConversion;
    }

    @Override
    public PageDTO<SimpleThreadDTO> getNewestThreads(int pageNumber) {
        log.info("Get newest threads - page: {}", pageNumber);

        return dtoConversion.convertToDTO(
                threadRepository.findAll(PageRequest.of(pageNumber, 30, Sort.by("creation").descending())).map(dtoConversion::convertToSimpleDTO)
        );
    }

    @Override
    public PageDTO<SimpleThreadDTO> searchThreads(int pageNumber, ForumQuery forumQuery) {
        log.info("Get all threads with meet criteria: {}, page: {}", forumQuery, pageNumber);

        return dtoConversion.convertToDTO(
                threadRepository.getAllByCategory(forumQuery.getCategory(), PageRequest.of(pageNumber, 30, Sort.by("creation").descending())).map(dtoConversion::convertToSimpleDTO)
        );
    }

    @Override
    public CompleteThreadDTO getThreadById(int id) {
        Thread thread = getPureThreadById(id);


        return dtoConversion.convertToDTO(
                thread, postService.findPostsByThread(0, thread.getThreadId())
        );
    }

    @Override
    public Thread getPureThreadById(int id) {
        log.info("Get thread with id: {}", id);

        return threadRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Could not find thread with id: " + id));
    }
}
