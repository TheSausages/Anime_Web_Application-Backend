package pwr.pracainz.repositories.forum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;
import pwr.pracainz.entities.databaseerntities.forum.Tag;
import pwr.pracainz.entities.databaseerntities.forum.Thread;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for the {@link Thread} class (Threads table). This repository enables Pagination.
 */
@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Integer> {

	@Query("Select distinct t From Thread t where " +
			"(:#{#tags.size()} < 1 or (" +
			"   t in (Select tt from Thread tt inner join tt.tags ytt " +
			"       where ytt in (:tags)" +
			"       group by tt" +
			"       having count (tt) = :#{new Long(#tags.size())})" +
			")) and " +
			"(t.creation between :minCreation and :maxCreation) and " +
			"(t.modification between :minModification and :maxModification) and " +
			"(t.nrOfPosts between :minNrOfPosts and :maxNrOfPosts) and " +
			"(:title is null or t.title like %:title%) and " +
			"(:creatorUsername is null or t.creator.username like %:creatorUsername%) and " +
			"(:category is null or t.category = :category) and " +
			"(:status is null or t.status = :status)")
	Page<Thread> findAllByForumQuery(@Param("minCreation") LocalDateTime minCreation,
	                                 @Param("maxCreation") LocalDateTime maxCreation,
	                                 @Param("minModification") LocalDateTime minModification,
	                                 @Param("maxModification") LocalDateTime maxModification,
	                                 @Param("minNrOfPosts") @Min(0) int minNrOfPosts,
	                                 @Param("maxNrOfPosts") @Min(0) int maxNrOfPosts,
	                                 @Param("title") String title,
	                                 @Param("creatorUsername") String creatorUsername,
	                                 @Param("category") ForumCategory category,
	                                 @Param("status") ThreadStatus status,
	                                 @Param("tags") List<Tag> tags,
	                                 Pageable pageable);
}
