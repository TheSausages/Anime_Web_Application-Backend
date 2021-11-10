package pwr.pracainz.configuration;

import pwr.pracainz.DTO.animeInfo.ReviewDTO;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

/**
 * Custom filter for when to include the {@link ReviewDTO}. It is used in {@link pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO}.
 */
public class ReviewDTOFilter {
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	/**
	 * Filter should include the object (should return false) when:
	 * <ul>
	 *     <li>The object is null</li>
	 *     <li>the object is a valid {@link ReviewDTO} instance</li>
	 * </ul>
	 * Otherwise, don't serialize/deserialize it
	 *
	 * @param obj The object which is checked
	 * @return true when conditions are met, otherwise false (or error if violations)
	 */
	@Override
	public boolean equals(Object obj) {
		if (Objects.isNull(obj)) {
			return false;
		}

		if (!(obj instanceof ReviewDTO)) {
			return true;
		}

		ReviewDTO review = (ReviewDTO) obj;

		Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(review);

		if (!validator.validate(review).isEmpty()) {
			throw new ConstraintViolationException(violations);
		}

		return false;
	}
}
