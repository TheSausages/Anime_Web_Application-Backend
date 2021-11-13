package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Class representing the <i>Reviews</i> table from the database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewId;

	@NotBlank
	private String reviewTitle;

	private String reviewText;

	@ColumnDefault("0")
	private int nrOfHelpful;

	@ColumnDefault("0")
	private int nrOfPlus;

	@ColumnDefault("0")
	private int nrOfMinus;

	/**
	 * Create a new <i>Review</i> object without an id.
	 * @param reviewTitle Title of the review
	 * @param reviewText Text of the review
	 * @param nrOfHelpful Number of people that found this helpful
	 * @param nrOfPlus Number of upvotes
	 * @param nrOfMinus Number of downvotes
	 */
	public Review(String reviewTitle, String reviewText, int nrOfHelpful, int nrOfPlus, int nrOfMinus) {
		this.reviewTitle = reviewTitle;
		this.reviewText = reviewText;
		this.nrOfHelpful = nrOfHelpful;
		this.nrOfPlus = nrOfPlus;
		this.nrOfMinus = nrOfMinus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Review review = (Review) o;
		return reviewId == review.reviewId && nrOfHelpful == review.nrOfHelpful && nrOfPlus == review.nrOfPlus && nrOfMinus == review.nrOfMinus && Objects.equals(reviewText, review.reviewText);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reviewId, reviewText, nrOfHelpful, nrOfPlus, nrOfMinus);
	}
}
