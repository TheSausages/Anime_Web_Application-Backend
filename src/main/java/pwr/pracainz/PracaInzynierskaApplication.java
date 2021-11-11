package pwr.pracainz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Application start class.
 */
@ConfigurationPropertiesScan
@SpringBootApplication
public class PracaInzynierskaApplication {
	public static void main(String[] args) {
		SpringApplication.run(PracaInzynierskaApplication.class, args);
	}
}