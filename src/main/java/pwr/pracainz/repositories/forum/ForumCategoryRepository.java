package pwr.pracainz.repositories.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;

@Repository
public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Integer> {
}
