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
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.ThreadRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;

@Log4j2
@Service
public class ThreadService implements ThreadServiceInterface {
    private final ThreadRepository threadRepository;
    private final DTOConversionInterface<SimpleThreadDTO> dtoConversion;

    @Autowired
    ThreadService(ThreadRepository threadRepository, DTOConversionInterface<SimpleThreadDTO> dtoConversion) {
        this.threadRepository = threadRepository;
        this.dtoConversion = dtoConversion;
    }

    @Override
    public PageDTO<SimpleThreadDTO> getNewestThreads(int pageNumber) {
        log.info("Get newest threads - page: {}", pageNumber);

        return dtoConversion.convertDomainPageToDTO(
                threadRepository.findAll(PageRequest.of(pageNumber, 30, Sort.by("creation").descending())).map(dtoConversion::convertThreadToSimpleDTO)
        );
    }

    @Override
    public PageDTO<SimpleThreadDTO> searchThreads(int pageNumber, ForumQuery forumQuery) {
        log.info("Get all threads with meet criteria: {}, page: {}", forumQuery, pageNumber);

        return dtoConversion.convertDomainPageToDTO(
                threadRepository.getAllByCategory(forumQuery.getCategory(), PageRequest.of(pageNumber, 30, Sort.by("creation").descending())).map(dtoConversion::convertThreadToSimpleDTO)
        );
    }

    @Override
    public CompleteThreadDTO getThreadById(int id) {
        log.info("Get thread with id: {}", id);

        return dtoConversion.convertThreadToCompleteDTO(
                threadRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Could not find thread with id: " + id))
        );
    }
}
