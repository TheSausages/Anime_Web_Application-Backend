package pwr.pracainz.repositories.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.Tag;

import java.util.Optional;

/**
 * Repository for the {@link Tag} class (Tags table).
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
	Optional<Tag> findByTagIdAndTagName(int id, String name);
}
