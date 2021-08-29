package pwr.pracainz.services.forum.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.repositories.forum.ThreadRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;

@Service
public class ThreadService implements ThreadServiceInterface {
    private final ThreadRepository threadRepository;
    private final DTOConversionInterface dtoConversion;

    @Autowired
    ThreadService(ThreadRepository threadRepository, DTOConversionInterface dtoConversion) {
        this.threadRepository = threadRepository;
        this.dtoConversion = dtoConversion;
    }

    @Override
    public PageDTO getNewestThreads(int pageNumber) {
        return dtoConversion.convertDomainPageToDTO(
                threadRepository.findAll(PageRequest.of(pageNumber, 30)).map(dtoConversion::convertThreadToSimpleDTO)
        );
    }
}
