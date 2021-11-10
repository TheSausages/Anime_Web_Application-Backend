package pwr.pracainz.configuration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.pracainz.configuration.properties.AnilistProperties;
import pwr.pracainz.configuration.properties.KeycloakClientServerProperties;

/**
 * Configure web client instances:
 * <ul>
 *     <li>The Anilist web client uses {@link AnilistProperties}</li>
 *     <li>The Keycloak web client uses {@link KeycloakClientServerProperties}</li>
 * </ul>
 */
@Configuration
public class WebClientsConfiguration {
	private final AnilistProperties anilistProperties;
	private final KeycloakClientServerProperties keycloakProperties;

	WebClientsConfiguration(AnilistProperties anilistProperties,
	                        KeycloakClientServerProperties keycloakProperties) {
		this.anilistProperties = anilistProperties;
		this.keycloakProperties = keycloakProperties;
	}

	@Bean(name = "anilistWebClient")
	WebClient anilistWebClient() {
		return WebClient.create(anilistProperties.getApiUrl());
	}

	@Bean(name = "keycloakWebClient")
	WebClient keycloakWebClient() {
		return WebClient.create(keycloakProperties.getUrl());
	}
}
