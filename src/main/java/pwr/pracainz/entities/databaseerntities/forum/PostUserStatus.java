package pwr.pracainz.entities.databaseerntities.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Class representing the <i>PostUserStatuses</i> table from the database.
 * It uses {@link PostUserStatusId} as an embedded composite primary key.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "PostUserStatuses")
@Entity
public class PostUserStatus {
	@EmbeddedId
	private PostUserStatusId postUserStatusId;

	@ColumnDefault("false")
	private boolean isLiked;

	@ColumnDefault("false")
	private boolean isDisliked;

	@ColumnDefault("false")
	private boolean isReported;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PostUserStatus that = (PostUserStatus) o;
		return isLiked == that.isLiked && isDisliked == that.isDisliked && isReported == that.isReported && Objects.equals(postUserStatusId, that.postUserStatusId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postUserStatusId, isLiked, isDisliked, isReported);
	}

	/**
	 * Create an empty {@link PostUserStatus}.
	 * @param post Post used in the id
	 * @param user user used in the id
	 * @param isLiked Has the user liked the post?
	 * @param isDisliked Has the user disliked the post?
	 * @param isReported has the user reported the post?
	 * @return A new {@link PostUserStatus} object
	 */
	public static PostUserStatus getEmptyPostUserStatus(Post post, User user, boolean isLiked, boolean isDisliked, boolean isReported) {
		PostUserStatusId id = new PostUserStatusId(user, post);

		return new PostUserStatus(
				id, isLiked, isDisliked, isReported
		);
	}

	/**
	 * Copy all data from an {@link PostUserStatusDTO} into an existing {@link PostUserStatus}.
	 * @param status The DTo from which the data should be copied
	 * @return Updated {@link PostUserStatus} object
	 */
	public PostUserStatus copyDataFromDTO(PostUserStatusDTO status) {
		this.isLiked = status.isLiked();
		this.isDisliked = status.isDisliked();
		this.isReported = status.isReported();

		return this;
	}
}
