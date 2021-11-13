package pwr.pracainz.entities.events;

import org.springframework.context.ApplicationEvent;
import pwr.pracainz.entities.databaseerntities.forum.Post;

/**
 * Event used when a new post has been created.
 */
public class PostCreationEvent extends ApplicationEvent {
	public PostCreationEvent(Post source) { super(source); }
}
