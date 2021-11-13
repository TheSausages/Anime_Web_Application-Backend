package pwr.pracainz.entities.databaseerntities.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Class representing the <i>ThreadUserStatuses</i> table from the database.
 * It uses {@link ThreadUserStatusId} as an embedded composite primary id.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ThreadUserStatuses")
@Entity
public class ThreadUserStatus {
	@EmbeddedId
	private ThreadUserStatusId threadUserStatusId;

	@ColumnDefault("false")
	private boolean isWatching;

	@ColumnDefault("false")
	private boolean isBlocked;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ThreadUserStatus that = (ThreadUserStatus) o;
		return isWatching == that.isWatching && isBlocked == that.isBlocked && Objects.equals(threadUserStatusId, that.threadUserStatusId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isWatching, isBlocked);
	}

	public static ThreadUserStatus getEmptyThreadUserStatus(Thread thread, User user, boolean isWatching, boolean isBlocked) {
		ThreadUserStatusId id = new ThreadUserStatusId(user, thread);

		return new ThreadUserStatus(
				id, isWatching, isBlocked
		);
	}

	public ThreadUserStatus copyDataFromDTO(ThreadUserStatusDTO userStatus) {
		this.isBlocked = userStatus.isBlocked();
		this.isWatching = userStatus.isWatching();

		return this;
	}
}
