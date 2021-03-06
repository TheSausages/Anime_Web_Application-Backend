package pwr.pracainz.repositories.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.ThreadUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.ThreadUserStatusId;

/**
 * Repository for the {@link ThreadUserStatus} class (ThreadUserStatuses table).
 */
@Repository
public interface ThreadUserStatusRepository extends JpaRepository<ThreadUserStatus, ThreadUserStatusId> {
}
