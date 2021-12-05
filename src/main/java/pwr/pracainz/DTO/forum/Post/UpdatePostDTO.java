package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Positive;

/**
 * Domain object when a {@link pwr.pracainz.entities.databaseerntities.forum.Post} is being updated.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePostDTO extends CreatePostDTO {
	@Positive(message = "Post Id must be positive!")
	private int postId;

	public UpdatePostDTO(int postId, String title, String text) {
		super(title, text);
		this.postId = postId;
	}
}
