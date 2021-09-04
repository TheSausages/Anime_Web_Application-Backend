package pwr.pracainz.DTO.animeInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimeUserInfoDTO {
    private AnimeUserInfoIdDTO id;
    private AnimeUserStatus status;

    @PastOrPresent
    private LocalDate watchStartDate;

    @PastOrPresent
    private LocalDate watchEndDate;

    @Min(0)
    private int nrOfEpisodesSeen;

    @JsonProperty(value = "isFavourite")
    private boolean isFavourite;
    private LocalDateTime modification;
    private boolean didReview;

    @Min(1)
    private Integer grade;
    private ReviewDTO review;
}


