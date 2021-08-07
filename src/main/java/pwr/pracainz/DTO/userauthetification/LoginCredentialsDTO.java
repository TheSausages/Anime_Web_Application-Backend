package pwr.pracainz.DTO.userauthetification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentialsDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    @Pattern(regexp = "^(?=.+[0-9])(?=.{4,}[a-z])(?=.*[A-Z]).{6,}$")
    private String password;
}
