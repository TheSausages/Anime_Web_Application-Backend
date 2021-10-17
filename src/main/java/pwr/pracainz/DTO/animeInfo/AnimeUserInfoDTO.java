package pwr.pracainz.DTO.animeInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimeUserInfoDTO {
	@Valid
	private AnimeUserInfoIdDTO id;

	private AnimeUserStatus status;

	@PastOrPresent(message = "Watch start date cannot be in the future")
	private LocalDate watchStartDate;

	@PastOrPresent(message = "Watch end date cannot be in the future")
	private LocalDate watchEndDate;

	@Min(value = 0, message = "Number of episodes seen cannot be negative")
	private int nrOfEpisodesSeen;

	@JsonProperty(value = "isFavourite")
	private boolean isFavourite;

	@PastOrPresent(message = "Modification date cannot be in the future")
	private LocalDateTime modification;

	private boolean didReview;

	@Min(value = 1, message = "Not one of the possible grades")
	private Integer grade;

	@Valid
	private ReviewDTO review;
}


