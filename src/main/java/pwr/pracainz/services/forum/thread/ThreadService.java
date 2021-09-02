package pwr.pracainz.services.forum.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.repositories.forum.ThreadRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;

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
        return dtoConversion.convertDomainPageToDTO(
                threadRepository.findAll(PageRequest.of(pageNumber, 30)).map(dtoConversion::convertThreadToSimpleDTO)
        );
    }
}
