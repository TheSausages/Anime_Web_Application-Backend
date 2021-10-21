package pwr.pracainz.DTO.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSeason;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MediaSeasonYear {
	private MediaSeason season;
	private int year;
}
