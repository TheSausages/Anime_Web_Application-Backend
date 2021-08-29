package pwr.pracainz.services.forum.thread;

import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;

import java.util.List;

public interface ThreadServiceInterface {
    List<SimpleThreadDTO> getNewestThreads(int pageNumber);
}
