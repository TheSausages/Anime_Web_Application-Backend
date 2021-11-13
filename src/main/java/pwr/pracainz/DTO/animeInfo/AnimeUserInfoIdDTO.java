package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.user.SimpleUserDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Domain class for the {@link pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId} class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimeUserInfoIdDTO {

	@NotNull(message = "User id Anime User Information cannot be null")
	@Valid
	private SimpleUserDTO user;

	@NotNull(message = "User id Anime User Information cannot be null")
	@Valid
	private AnimeDTO anime;
}
