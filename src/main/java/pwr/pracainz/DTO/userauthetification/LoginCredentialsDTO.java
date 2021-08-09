package pwr.pracainz.DTO.userauthetification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginCredentialsDTO {
    @NotEmpty
    String username;
    @NotEmpty
    @Pattern(regexp = "^(?=.+[0-9])(?=.{4,}[a-z])(?=.*[A-Z]).{6,}$")
    String password;
}
