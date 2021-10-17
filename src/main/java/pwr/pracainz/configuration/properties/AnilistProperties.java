package pwr.pracainz.configuration.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@ConfigurationProperties(prefix = "anilist")
@ConstructorBinding
@Getter
public class AnilistProperties {
	private final String apiUrl;

	private final String errorMessage;

	public AnilistProperties(String apiUrl, String errorMessage) {
		this.apiUrl = apiUrl;
		this.errorMessage = errorMessage;
	}
}
