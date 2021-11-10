package pwr.pracainz.configuration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * Configure an app-wide {@link ObjectMapper} with:
 * <ul>
 *     <li>Use the {@link JavaTimeModule}</li>
 *     <li>The date format is <i>dd.MM.yyyy</i></li>
 * </ul>
 */
@Configuration
public class ObjectMapperConfiguration {

	@Bean
	ObjectMapper getObjectMapper() {
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
	}
}
