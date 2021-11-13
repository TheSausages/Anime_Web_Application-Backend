package pwr.pracainz.entities.userauthentification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Information received from Keycloak when a user logs in.
 */
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuthenticationToken that = (AuthenticationToken) o;
		return expires_in == that.expires_in && not_before_policy == that.not_before_policy && refreshes_expires_in == that.refreshes_expires_in && Objects.equals(access_token, that.access_token) && Objects.equals(id_token, that.id_token) && Objects.equals(refresh_token, that.refresh_token) && Objects.equals(scope, that.scope) && Objects.equals(session_state, that.session_state) && Objects.equals(token_type, that.token_type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(access_token, expires_in, id_token, not_before_policy, refreshes_expires_in, refresh_token, scope, session_state, token_type);
	}
}
