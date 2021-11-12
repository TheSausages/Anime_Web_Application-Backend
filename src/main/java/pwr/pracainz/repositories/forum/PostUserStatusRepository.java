package pwr.pracainz.repositories.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatusId;

/**
 * Repository for the {@link PostUserStatus} class (PostUserStatuses table).
 */
@Repository
public interface PostUserStatusRepository extends JpaRepository<PostUserStatus, PostUserStatusId> {
}
