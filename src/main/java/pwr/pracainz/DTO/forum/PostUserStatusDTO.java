package pwr.pracainz.DTO.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Domain class for the {@link pwr.pracainz.entities.databaseerntities.forum.PostUserStatus} class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUserStatusDTO {
	@NotNull(message = "Post User Status id cannot be null")
	@Valid
	private PostUserStatusIdDTO ids;

	@JsonProperty(value = "isLiked")
	private boolean isLiked;

	@JsonProperty(value = "isDisliked")
	private boolean isDisliked;

	@JsonProperty(value = "isReported")
	private boolean isReported;
}
