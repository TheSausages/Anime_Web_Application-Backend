package pwr.pracainz.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

/**
 * Simple domain class holding a message that should be forward to the user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleMessageDTO {
	@NotBlank(message = "Message cannot be blank")
	String message;
}
