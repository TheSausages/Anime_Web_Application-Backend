package pwr.pracainz.DTO.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSeason;

/**
 * Small helper class used to search by season and a year. It is used in {@link AnimeQuery}.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MediaSeasonYear {
	private MediaSeason season;
	private int year;

	@Override
	public String toString() {
		return season + " " + year;
	}
}
