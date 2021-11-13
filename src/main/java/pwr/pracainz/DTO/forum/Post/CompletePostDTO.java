package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Complete domain class for {@link pwr.pracainz.entities.databaseerntities.forum.Post} class.
 * It holds extensive information on a post, ex. status of the currently logged-in user.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompletePostDTO extends SimplePostDTO {
	@NotBlank(message = "Post text cannot be blank")
	@Size(max = 600, message = "Post text is to long")
	private String text;

	@Min(value = 0, message = "Number of upvotes must be positive")
	private int nrOfPlus;

	@Min(value = 0, message = "Number of downvotes must be positive")
	private int nrOfMinus;

	@Valid
	private PostUserStatusDTO postUserStatus;

	/**
	 * Constructor for a complete post DTO. Uses {@link SimplePostDTO} constructor.
	 * This constructor doesn't hold any post creator status.
	 * @param id Id of the post
	 * @param title Title of the post
	 * @param isBlocked Is the post blocked
	 * @param creation When was the post created
	 * @param modification When was the last time the post was modified
	 * @param creator Who is the creator of the post
	 * @param text What is the text of the post
	 * @param nrOfPlus How many times was the post upvoted
	 * @param nrOfMinus How many times was the post downvoted
	 */
	public CompletePostDTO(int id, String title, boolean isBlocked, LocalDateTime creation, LocalDateTime modification, SimpleUserDTO creator, String text, int nrOfPlus, int nrOfMinus) {
		super(id, title, isBlocked, creation, modification, creator);
		this.text = text;
		this.nrOfPlus = nrOfPlus;
		this.nrOfMinus = nrOfMinus;
		this.postUserStatus = null;
	}

	/**
	 * Variant of {@link #CompletePostDTO(int, String, boolean, LocalDateTime, LocalDateTime, SimpleUserDTO, String, int, int)}.
	 * This constructor adds post user status.
	 * @param postUserStatus The status for the post for the currently authenticated user
	 */
	public CompletePostDTO(int id, String title, boolean isBlocked, LocalDateTime creation, LocalDateTime modification, SimpleUserDTO created, String text, int nrOfPlus, int nrOfMinus, PostUserStatusDTO postUserStatus) {
		super(id, title, isBlocked, creation, modification, created);
		this.text = text;
		this.nrOfPlus = nrOfPlus;
		this.nrOfMinus = nrOfMinus;
		this.postUserStatus = postUserStatus;
	}
}
