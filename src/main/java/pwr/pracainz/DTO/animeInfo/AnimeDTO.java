package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * Domain class for the {@link pwr.pracainz.entities.databaseerntities.animeInfo.Anime} class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimeDTO {
	@Positive(message = "Anime id cannot be negative!")
	private int animeId;

	@Min(value = 0, message = "Average score cannot be negative")
	private double averageScore;

	@Min(value = 0, message = "Nr. of favourites cannot be negative")
	private int nrOfFavourites;

	@Min(value = 0, message = "Nr. of reviews cannot be negative")
	private int nrOfReviews;
}
