package pwr.pracainz.services.forum.thread;

import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;

public interface ThreadServiceInterface {
    PageDTO<SimpleThreadDTO> getNewestThreads(int pageNumber);
}
