package pwr.pracainz.configuration;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {
	@Bean
	Keycloak getKeycloak() {
		return KeycloakBuilder.builder()
				.serverUrl("http://localhost:8180/auth")
				.realm("master")
				.grantType(OAuth2Constants.PASSWORD)
				.username("admin")
				.password("Password1")
				.clientId("admin-cli")
				.clientSecret("0f98645c-da38-44e9-8fce-681f3b69cfc4")
				.resteasyClient(
						new ResteasyClientBuilder()
								.connectionPoolSize(10)
								.build()
				).build();
	}
}
