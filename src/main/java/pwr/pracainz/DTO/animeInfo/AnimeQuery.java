package pwr.pracainz.DTO.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.anime.query.parameters.media.MediaFormat;
import pwr.pracainz.entities.anime.query.parameters.media.MediaStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Query used when a User searches for Anime using selected conditions.
 * No field should be mandatory.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnimeQuery {
	private String title = null;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (Objects.nonNull(minStartDate) && Objects.nonNull(maxStartDate)) {
			builder.append("\n Start date date between ").append(minStartDate).append(" and ").append(maxStartDate);
		} else {
			if (Objects.nonNull(minStartDate)) {
				builder.append("\n Start date date is at least ").append(minStartDate);
			}

			if (Objects.nonNull(maxStartDate)) {
				builder.append("\n Start date date is at max ").append(maxStartDate);
			}
		}

		if (Objects.nonNull(minEndDate) && Objects.nonNull(maxEndDate)) {
			builder.append("\n End date date between ").append(minEndDate).append(" and ").append(maxEndDate);
		} else {
			if (Objects.nonNull(minEndDate)) {
				builder.append("\n End date date is at least ").append(minEndDate);
			}

			if (Objects.nonNull(maxEndDate)) {
				builder.append("\n End date date is at max ").append(maxEndDate);
			}
		}

		if (Objects.nonNull(minNrOfEpisodes) && Objects.nonNull(maxNrOfEpisodes)) {
			builder.append("\n nr. of posts between ").append(minNrOfEpisodes).append(" and ").append(maxNrOfEpisodes);
		} else {
			if (Objects.nonNull(minNrOfEpisodes)) {
				builder.append("\n nr. of posts at least ").append(minNrOfEpisodes);
			}

			if (Objects.nonNull(maxNrOfEpisodes)) {
				builder.append("\n nr. of posts at most ").append(maxNrOfEpisodes);
			}
		}

		if (Objects.nonNull(minAverageScore) && Objects.nonNull(maxAverageScore)) {
			builder.append("\n Average score between ").append(minAverageScore).append(" and ").append(maxAverageScore);
		} else {
			if (Objects.nonNull(minAverageScore)) {
				builder.append("\n Average score at least ").append(minAverageScore);
			}

			if (Objects.nonNull(maxAverageScore)) {
				builder.append("\n Average score at most ").append(maxAverageScore);
			}
		}

		if (Objects.nonNull(title) && !title.isEmpty()) {
			builder.append("\n title contains '").append(title).append("'");
		}

		if (Objects.nonNull(status)) {
			builder.append("\n status = ").append(status);
		}

		if (Objects.nonNull(format)) {
			builder.append("\n format = ").append(format);
		}

		if (Objects.nonNull(season)) {
			builder.append("\n season = ").append(season);
		}

		return builder.toString();
	}
}
