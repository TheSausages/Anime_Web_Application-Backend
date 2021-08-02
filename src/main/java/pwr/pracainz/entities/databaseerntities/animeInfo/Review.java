package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

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
}
