package pwr.pracainz.DTO.userauthetification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationBodyDTO {
    @NotEmpty(message = "Username Cannot be empty!")
    String username;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.+[0-9])(?=.{4,}[a-z])(?=.*[A-Z]).{6,}$", message = "Password is not of correct structure")
    String password;

    @NotEmpty(message = "Matching password cannot be empty")
    @Pattern(regexp = "^(?=.+[0-9])(?=.{4,}[a-z])(?=.*[A-Z]).{6,}$", message = "Matching password is not of correct structure")
    String matchingPassword;

    @NotEmpty(message = "Mail cannot be empty!")
    @Email(message = "Mail is not of correct structure")
    String email;
}
