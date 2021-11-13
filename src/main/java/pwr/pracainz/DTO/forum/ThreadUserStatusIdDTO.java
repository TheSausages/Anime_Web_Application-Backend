package pwr.pracainz.DTO.forum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Domain class for the {@link pwr.pracainz.entities.databaseerntities.forum.ThreadUserStatusId} class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadUserStatusIdDTO {
	@NotNull(message = "User in Thread User Status id cannot be null")
	@Valid
	private SimpleUserDTO user;

	@JsonIgnoreProperties("threadUserStatus")
	@NotNull(message = "Thread in Thread User Status id cannot be null")
	@Valid
	private SimpleThreadDTO thread;
}
