package pwr.pracainz.repositories.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;

import java.util.Optional;

/**
 * Repository for the {@link ForumCategory} class (ForumCategories table).
 */
@Repository
public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Integer> {
	Optional<ForumCategory> findForumCategoryByCategoryIdAndCategoryName(int id, String name);
}
