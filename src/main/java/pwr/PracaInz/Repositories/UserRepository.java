package pwr.PracaInz.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pwr.PracaInz.Entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
