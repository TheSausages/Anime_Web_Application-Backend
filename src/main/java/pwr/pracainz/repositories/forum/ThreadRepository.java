package pwr.pracainz.repositories.forum;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.Thread;

@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Integer> {
}
