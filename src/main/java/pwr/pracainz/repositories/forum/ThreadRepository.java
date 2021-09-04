package pwr.pracainz.repositories.forum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;
import pwr.pracainz.entities.databaseerntities.forum.Thread;

@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Integer> {
    Page<Thread> getAllByCategory(ForumCategory category, Pageable pageable);
}
