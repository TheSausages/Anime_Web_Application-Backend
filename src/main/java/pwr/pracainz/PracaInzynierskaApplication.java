package pwr.pracainz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class PracaInzynierskaApplication {
	public static void main(String[] args) {
		SpringApplication.run(PracaInzynierskaApplication.class, args);
	}
}