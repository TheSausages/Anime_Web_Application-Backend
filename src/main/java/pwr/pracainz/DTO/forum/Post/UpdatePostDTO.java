package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Domain object when a {@link pwr.pracainz.entities.databaseerntities.forum.Post} is being updated.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePostDTO {
	@Positive(message = "Post Id must be positive!")
	private int postId;

	@NotBlank(message = "Post title cannot be blank")
	@Size(max = 80, message = "Post title is to long")
	private String title;

	@NotBlank(message = "Post text cannot be blank")
	@Size(max = 600, message = "Post text is to long")
	private String text;
}
