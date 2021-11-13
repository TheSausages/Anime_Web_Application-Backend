package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Domain object used when a new {@link pwr.pracainz.entities.databaseerntities.forum.Post} is created.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePostDTO {
	@NotBlank(message = "Post title cannot be blank")
	@Size(max = 80, message = "Post title is to long")
	private String title;

	@NotBlank(message = "Post text cannot be blank")
	@Size(max = 600, message = "Post text is to long")
	private String text;
}
