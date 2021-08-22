package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimeUserInfoDTO {
    private AnimeUserInfoIdDTO id;
    private AnimeUserStatus status;
    private LocalDate watchStartDate;
    private LocalDate watchEndDate;

    @Min(0)
    private int nrOfEpisodesSeen;
    private boolean isFavourite;
    private boolean didReview;

    @Min(1)
    private Integer grade;
    private ReviewDTO review;
}


