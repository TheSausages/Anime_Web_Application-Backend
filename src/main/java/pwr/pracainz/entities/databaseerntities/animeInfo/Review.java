package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    private String reviewText;

    @ColumnDefault("0")
    private int nrOfHelpful;

    @ColumnDefault("0")
    private int nrOfPlus;

    @ColumnDefault("0")
    private int nrOfMinus;

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
