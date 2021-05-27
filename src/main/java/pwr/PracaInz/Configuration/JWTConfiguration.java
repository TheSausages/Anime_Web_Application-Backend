package pwr.PracaInz.Configuration;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Getter
@Configuration
@PropertySource("classpath:application.properties")
public class JWTConfiguration {
    @Value("${jwt.key}")
    private String secret;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
