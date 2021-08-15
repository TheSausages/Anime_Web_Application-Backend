package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;

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
    private int nrOfEpisodesSeen;
    private boolean isFavourite;
    private boolean didReview;
    private Integer grade;
    private ReviewDTO review;
}


