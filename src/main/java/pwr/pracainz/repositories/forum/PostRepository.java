package pwr.pracainz.repositories.forum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.Post;

/**
 * Repository for the {@link Post} class (Posts table). The {@link #getAllByThread_ThreadId(int, Pageable)} uses Pagination.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	Page<Post> getAllByThread_ThreadId(int id, Pageable pageable);

	@Modifying
	@Query("UPDATE Post p set p.nrOfPlus = p.nrOfPlus + 1 WHERE p.postId = :id")
	void incrementNrOfPlusByPostId(int id);

	@Modifying
	@Query("UPDATE Post p set p.nrOfPlus = p.nrOfPlus - 1 WHERE p.postId = :id")
	void decrementNrOfPlusByPostId(int id);

	@Modifying
	@Query("UPDATE Post p set p.nrOfMinus = p.nrOfMinus + 1 WHERE p.postId = :id")
	void incrementNrOfMinusByPostId(int id);

	@Modifying
	@Query("UPDATE Post p set p.nrOfMinus = p.nrOfMinus - 1 WHERE p.postId = :id")
	void decrementNrOfMinusByPostId(int id);

	@Modifying
	@Query("UPDATE Post p set p.nrOfReports = p.nrOfReports + 1 WHERE p.postId = :id")
	void reportPostByPostId(int id);
}
