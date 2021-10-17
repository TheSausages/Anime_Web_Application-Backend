package pwr.pracainz.eventlisteners;

import org.springframework.stereotype.Component;
import pwr.pracainz.entities.databaseerntities.forum.Post;

import javax.persistence.PostPersist;

@Component
public class PostListener {

    @PostPersist
    public void addToNrOfPosts(Post post) {
        post.getUser().incrementNrOfPosts();
    }
}
