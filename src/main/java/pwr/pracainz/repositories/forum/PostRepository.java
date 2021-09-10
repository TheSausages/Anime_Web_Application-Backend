package pwr.pracainz.repositories.forum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.forum.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> getAllByThread_ThreadId(int id, Pageable pageable);
}
