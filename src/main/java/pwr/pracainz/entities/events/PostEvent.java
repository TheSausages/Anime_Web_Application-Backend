package pwr.pracainz.entities.events;

import org.springframework.context.ApplicationEvent;
import pwr.pracainz.entities.databaseerntities.forum.Post;

public class PostEvent extends ApplicationEvent {
	public PostEvent(Post source) { super(source); }
}
