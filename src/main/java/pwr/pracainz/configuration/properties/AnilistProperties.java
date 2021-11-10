package pwr.pracainz.configuration.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Properties used for connecting to the anilist api. It uses the <i>anilist</i> prefix
 */
@ConfigurationProperties(prefix = "anilist")
@ConstructorBinding
@Getter
public class AnilistProperties {
	private final String apiUrl;

	public AnilistProperties(String apiUrl) {
		this.apiUrl = apiUrl;
	}
}
