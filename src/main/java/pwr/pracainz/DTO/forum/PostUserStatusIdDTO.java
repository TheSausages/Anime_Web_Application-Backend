package pwr.pracainz.DTO.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.Post.SimplePostDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Domain class for the {@link pwr.pracainz.entities.databaseerntities.forum.PostUserStatusId} class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUserStatusIdDTO {
	@NotNull(message = "User in Post User Status cannot be empty")
	@Valid
	private SimpleUserDTO user;

	@NotNull(message = "Post in Post User Status cannot be empty")
	@Valid
	private SimplePostDTO post;
}
