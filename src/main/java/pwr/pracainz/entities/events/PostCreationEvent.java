package pwr.pracainz.entities.events;

import org.springframework.context.ApplicationEvent;
import pwr.pracainz.entities.databaseerntities.forum.Post;

public class PostCreationEvent extends ApplicationEvent {
	public PostCreationEvent(Post source) { super(source); }
}
