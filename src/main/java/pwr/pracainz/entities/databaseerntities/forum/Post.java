package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing the <i>Posts</i> table from the database.
 */
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Posts")
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;

	@Length(max = 45)
	@NotEmpty
	private String title;

	@Length(max = 600)
	@NotEmpty
	private String postText;

	@ColumnDefault("false")
	private boolean isBlocked;

	@ColumnDefault("0")
	@Min(value = 0)
	private int nrOfPlus;

	@ColumnDefault("0")
	@Min(value = 0)
	private int nrOfMinus;

	@ColumnDefault("0")
	@Min(value = 0)
	private int nrOfReports;

	@PastOrPresent
	private LocalDateTime creation;

	@PastOrPresent
	private LocalDateTime modification;

	@OneToMany(
			mappedBy = "postUserStatusId.post",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private Set<PostUserStatus> postUserStatuses;

	@ManyToOne
	@JoinColumn(name = "creatorId", nullable = false)
	private User creator;

	@ManyToOne
	@JoinColumn(name = "threadId", nullable = false)
	private Thread thread;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		return postId == post.postId && isBlocked == post.isBlocked && nrOfPlus == post.nrOfPlus && nrOfMinus == post.nrOfMinus && Objects.equals(title, post.title) && Objects.equals(postText, post.postText) && Objects.equals(creator, post.creator) && Objects.equals(thread, post.thread);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId, title, postText, isBlocked, nrOfPlus, nrOfMinus, thread);
	}

	/**
	 * Custom getter for {@link #postUserStatuses}. If the field is null, return an empty Set.
	 * @return The field or an empty set
	 */
	public Set<PostUserStatus> getPostUserStatuses() {
		return Objects.isNull(postUserStatuses) ? Collections.emptySet() : postUserStatuses;
	}
}
