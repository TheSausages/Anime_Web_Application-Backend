package pwr.pracainz.DTO.userauthetification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenDTO {
    private String access_token;
    private int refreshes_expires_in;
    private String refresh_token;
    private String token_type;
}
