package pwr.pracainz.configuration;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pwr.pracainz.configuration.properties.KeycloakMasterProperties;

@Configuration
public class KeycloakConfiguration {
	private final KeycloakMasterProperties keycloakProperties;

	KeycloakConfiguration(KeycloakMasterProperties keycloakProperties) {
		this.keycloakProperties = keycloakProperties;
	}

	@Bean
	Keycloak getKeycloak() {
		return KeycloakBuilder.builder()
				.serverUrl(keycloakProperties.getUrl())
				.realm(keycloakProperties.getRealm())
				.grantType(OAuth2Constants.PASSWORD)
				.username(keycloakProperties.getUsername())
				.password(keycloakProperties.getPassword())
				.clientId(keycloakProperties.getClientId())
				.clientSecret(keycloakProperties.getClientSecret())
				.resteasyClient(
						new ResteasyClientBuilder()
								.connectionPoolSize(10)
								.build()
				).build();
	}
}
