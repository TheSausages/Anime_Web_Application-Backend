package pwr.pracainz.configuration.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "keycloakrealm.clientserver")
@ConstructorBinding
@Getter
public class KeycloakClientServerProperties {
	private final String mainRealm;
	private final String realm;
	private final String url;
	private final String clientSecret;
	private final String scope;
	private final KeycloakGrantType grantType;

	public KeycloakClientServerProperties(String mainRealm,
	                                      String realm,
	                                      String url,
	                                      String clientSecret,
	                                      String scope,
	                                      KeycloakGrantType grantType) {
		this.mainRealm = mainRealm;
		this.realm = realm;
		this.url = url;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.grantType = grantType;
	}

	@ConstructorBinding
	@Getter
	public static class KeycloakGrantType {
		private final String login;
		private final String refresh;

		public KeycloakGrantType(String login,
		                         String refresh) {
			this.login = login;
			this.refresh = refresh;
		}
	}
}
