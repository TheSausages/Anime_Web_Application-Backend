package pwr.pracainz.entities.userauthentification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationToken {
    private String access_token;
    private int expires_in;
    private String id_token;
    private long not_before_policy;
    private int refreshes_expires_in;
    private String refresh_token;
    private String scope;
    private String session_state;
    private String token_type;
}
