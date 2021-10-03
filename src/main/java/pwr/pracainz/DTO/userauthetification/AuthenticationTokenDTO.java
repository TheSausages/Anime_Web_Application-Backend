package pwr.pracainz.DTO.userauthetification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationTokenDTO {
    @Pattern(regexp = "\\w{1,120}", message = "Access token does not have the correct structure")
    String access_token;

    @Positive(message = "Token expiration time cannot be negative")
    int expires_in;

    @Pattern(regexp = "\\w{1,120}", message = "Refresh token does not have the correct structure")
    String refresh_token;

    @NotBlank(message = "Token type cannot be blank")
    String token_type;
}
