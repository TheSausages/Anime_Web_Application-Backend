package pwr.pracainz.services.forum.thread;

import pwr.pracainz.DTO.PageDTO;

public interface ThreadServiceInterface {
    PageDTO getNewestThreads(int pageNumber);
}
