package pwr.pracainz.DTO.userauthetification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationBodyDTO {
	@Length(min = 6, message = "Username is too short")
	@NotBlank(message = "Username cannot be blank!")
	String username;

	@NotBlank(message = "Password cannot be blank")
	@Pattern(regexp = "^(?=.+[0-9])(?=.{4,}[a-z])(?=.*[A-Z]).{6,}$", message = "Password is not of correct structure")
	String password;

	@NotBlank(message = "Matching password cannot be blank")
	@Pattern(regexp = "^(?=.+[0-9])(?=.{4,}[a-z])(?=.*[A-Z]).{6,}$", message = "Matching password is not of correct structure")
	String matchingPassword;

	@NotBlank(message = "Mail cannot be blank")
	@Email(message = "Mail is not of correct structure")
	String email;

	@AssertTrue(message = "Passwords must match")
	private boolean isPasswordsMatching() {
		return password.equals(matchingPassword);
	}
}
