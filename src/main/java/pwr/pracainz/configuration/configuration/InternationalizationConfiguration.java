package pwr.pracainz.configuration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * Configure a {@link AcceptHeaderLocaleResolver} locale resolver with a default {@link Locale#UK} locale.
 */
@Configuration
public class InternationalizationConfiguration {
	@Bean
	@Primary
	LocaleResolver customLocaleResolver() {
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		resolver.setDefaultLocale(Locale.UK);

		return resolver;
	}
}
