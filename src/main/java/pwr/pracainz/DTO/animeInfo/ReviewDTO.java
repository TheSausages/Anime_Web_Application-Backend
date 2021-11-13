package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Domain class for the {@link pwr.pracainz.entities.databaseerntities.animeInfo.Review} class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewDTO {
	@Positive(message = "Review id must be positive")
	private int id;

	@NotBlank(message = "Review title cannot be blank")
	@Size(max = 100, message = "Review title is to long")
	private String reviewTitle;

	@Size(max = 300, message = "Review text is to long")
	private String reviewText;

	@Min(0)
	private int nrOfHelpful;

	@Min(0)
	private int nrOfPlus;

	@Min(0)
	private int nrOfMinus;
}
