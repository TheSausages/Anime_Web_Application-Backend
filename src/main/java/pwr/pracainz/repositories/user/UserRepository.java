package pwr.pracainz.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.user.User;

/**
 * Repository for the {@link User} class (Users table).
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
