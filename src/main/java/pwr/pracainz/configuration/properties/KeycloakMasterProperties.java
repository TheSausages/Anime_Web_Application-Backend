package pwr.pracainz.configuration.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "keycloakrealm.master")
@ConstructorBinding
@Getter
public class KeycloakMasterProperties {
	private final String url;
	private final String realm;
	private final String username;
	private final String password;
	private final String clientId;
	private final String clientSecret;

	public KeycloakMasterProperties(String url,
	                                String realm,
	                                String username,
	                                String password,
	                                String clientId,
	                                String clientSecret) {
		this.url = url;
		this.realm = realm;
		this.username = username;
		this.password = password;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
}
