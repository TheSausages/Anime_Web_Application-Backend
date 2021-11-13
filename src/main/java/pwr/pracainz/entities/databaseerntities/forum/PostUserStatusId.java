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

/**
 * Class representing the composite primary key of the {@link PostUserStatusId} class.
 * It consists of an {@link User} and a {@link Post}.
 */
@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserStatusId implements Serializable {
	@NotEmpty
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;

	@NotEmpty
	@ManyToOne
	@JoinColumn(name = "PostId")
	private Post post;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PostUserStatusId that = (PostUserStatusId) o;
		return Objects.equals(user, that.user) && Objects.equals(post, that.post);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, post);
	}
}
