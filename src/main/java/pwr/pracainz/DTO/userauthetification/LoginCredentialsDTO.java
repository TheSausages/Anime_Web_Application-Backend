package pwr.pracainz.DTO.userauthetification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginCredentialsDTO {
	@NotBlank(message = "Username cannot be empty!")
	@Size(min = 6, message = "Username must have at least 6 characters")
	String username;

	@NotBlank(message = "Password cannot be empty!")
	@Pattern(regexp = "^(?=.+[0-9])(?=.{4,}[a-z])(?=.*[A-Z]).{6,}$", message = "Password doesn't have the correct structure")
	String password;
}
