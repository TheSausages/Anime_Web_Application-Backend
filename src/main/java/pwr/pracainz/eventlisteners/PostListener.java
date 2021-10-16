package pwr.pracainz.eventlisteners;

import org.springframework.stereotype.Component;
import pwr.pracainz.entities.databaseerntities.forum.Post;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
public class PostListener {

    @PostPersist
    @PostUpdate
    public void addToNrOfPosts(Post post) {
        post.getUser().incrementNrOfPosts();
    }
}
