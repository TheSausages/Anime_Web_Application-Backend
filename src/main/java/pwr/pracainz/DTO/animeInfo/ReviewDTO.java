package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewDTO {
    private int id;
    private String reviewText;
    private int nrOfHelpful;
    private int nrOfPlus;
    private int nrOfMinus;
}
