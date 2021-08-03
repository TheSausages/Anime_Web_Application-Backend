package pwr.pracainz.DTO.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnimeUserInfoDTO {
    private AnimeUserInfoIdDTO id;
    private AnimeUserStatus status;
    private LocalDate watchStartDate;
    private LocalDate watchEndDate;
    private int nrOfEpisodesSeen;
    private boolean isFavourite;
    private boolean didReview;
    private GradeDTO grade;
    private ReviewDTO review;
}


