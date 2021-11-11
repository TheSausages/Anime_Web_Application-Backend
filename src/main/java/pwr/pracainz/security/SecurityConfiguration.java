package pwr.pracainz.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration. Uses {@link KeycloakConfiguration} and {@link KeycloakWebSecurityConfigurerAdapter} to configure keycloak authentication using tokens.
 */
@Configuration
@EnableWebSecurity
@KeycloakConfiguration
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {
	/**
	 * Configure the Authentication to use {@link KeycloakAuthenticationProvider}
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	/**
	 * Create a {@link KeycloakSpringBootConfigResolver} and return it as a bean.
	 * @return The resolver
	 */
	@Bean
	public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	/**
	 * Create {@link RegisterSessionAuthenticationStrategy} as the {@link SessionAuthenticationStrategy} bean.
	 * @return The strategy
	 */
	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	/**
	 * Configure the HttpSecurity:
	 * <ul>
	 *     <li>List of authenticated and public endpoints in the documentation</li>
	 *     <li>Disable CSRF</li>
	 *     <li>Enable Cors configured using a {@link CorsConfigurationSource} bean (by default use {@link #defaultCorsConfigurationSource()})</li>
	 *     <li>Any Requests need authentication</li>
	 * </ul>
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http
				.csrf().disable()
				.cors().and()
				.headers().frameOptions().sameOrigin()
				.and()
				.authorizeRequests()
				.mvcMatchers("/**", "/auth**", "/auth/**", "/anime/**", "/anime**", "/user/**", "/user**").permitAll()
				.mvcMatchers("/forum/**", "/forum**", "/animeUser**", "/animeUser/**", "/achievements/**", "/achievements**", "/user/current").authenticated()
				.anyRequest().authenticated()
		;

	}

	/**
	 * Configure CORS for local development. Varies from {@link #defaultCorsConfigurationSource()} by allowing requests
	 * from <i>localhost:3000</i> (when running frontend by itself).
	 * @return Local CORS configuration
	 */
	@Bean
	@Profile("local")
	public CorsConfigurationSource corsConfigurationSourceForLocal() {
		CorsConfiguration configuration = getDefaultCorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8180", "http://localhost:3000", "http://192.168.0.245:3000"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	 * Default CORS configuration. Only data from the keycloak server and static frontend is allowed.
	 * For ex. used when running with docker.
	 * @return Default CORS configuration
	 */
	@Bean
	public CorsConfigurationSource defaultCorsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", getDefaultCorsConfiguration());
		return source;
	}

	/**
	 * Default CORS configuration. Can be changed if needed in every {@link CorsConfigurationSource} bean.
	 * @return CORS configuration
	 */
	private CorsConfiguration getDefaultCorsConfiguration() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8180", "http://localhost:8080", "http://192.168.0.245:8080"));
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "OPTIONS", "DELETE", "PUT"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION, HttpHeaders.ACCEPT_LANGUAGE));
		configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);

		return configuration;
	}
}
