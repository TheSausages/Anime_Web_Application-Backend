package pwr.pracainz.configuration.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@ConfigurationProperties(prefix = "anilist")
@ConstructorBinding
@Getter
public class AnilistProperties {
	private final String apiUrl;

	public AnilistProperties(String apiUrl) {
		this.apiUrl = apiUrl;
	}
}
