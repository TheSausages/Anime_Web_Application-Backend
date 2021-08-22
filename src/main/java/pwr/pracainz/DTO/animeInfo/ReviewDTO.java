package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewDTO {
    private int id;

    @NotEmpty
    private String reviewTitle;

    @NotEmpty
    private String reviewText;

    @Positive
    private int nrOfHelpful;
    private int nrOfPlus;
    private int nrOfMinus;
}
