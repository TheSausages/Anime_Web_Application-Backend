package pwr.pracainz.DTO.animeInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.anime.query.parameters.media.MediaFormat;
import pwr.pracainz.entities.anime.query.parameters.media.MediaStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnimeQuery {
	private String name = null;

	private LocalDateTime maxStartDate = null;

	private LocalDateTime minStartDate = null;

	private LocalDateTime maxEndDate = null;

	private LocalDateTime minEndDate = null;

	@Min(value = 0, message = "Maximal number of episodes cannot be negative")
	private Integer maxNrOfEpisodes = null;

	@Min(value = 0, message = "Minimal number of episodes cannot be negative")
	private Integer minNrOfEpisodes = null;

	@Min(value = 0, message = "Maximal average score cannot be negative")
	@Max(value = 101, message = "Maximal average score cannot be over 100")
	private Integer maxAverageScore = null;

	@Min(value = 0, message = "Minimal average score cannot be negative")
	@Max(value = 101, message = "Minimal average score cannot be over 100")
	private Integer minAverageScore = null;

	private MediaSeasonYear season = null;

	private MediaStatus status = null;

	private MediaFormat format = null;
}
