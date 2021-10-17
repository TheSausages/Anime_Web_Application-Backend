package pwr.pracainz.entities.databaseerntities.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThreadUserStatusId implements Serializable {
	@NotEmpty
	@ManyToOne
	@JoinColumn(name = "UserID")
	private User user;

	@NotEmpty
	@ManyToOne
	@JoinColumn(name = "threadId")
	private Thread thread;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ThreadUserStatusId that = (ThreadUserStatusId) o;
		return Objects.equals(user, that.user) && Objects.equals(thread, that.thread);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, thread);
	}
}
