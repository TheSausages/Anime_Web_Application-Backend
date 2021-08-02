package pwr.pracainz.DTO.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReviewDTO {
    private int reviewId;
    private String reviewText;
    private int nrOfHelpful;
    private int nrOfPlus;
    private int nrOfMinus;
}
