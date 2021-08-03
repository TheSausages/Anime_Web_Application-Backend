package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "AnimeUserInfos")
@Entity
public class AnimeUserInfo {
    @EmbeddedId
    private AnimeUserInfoId animeUserInfoId;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("NO_STATUS")
    private AnimeUserStatus status;

    private LocalDate watchStartDate;

    private LocalDate watchEndDate;

    @Min(0)
    @ColumnDefault("0")
    private int nrOfEpisodesSeen;

    @ColumnDefault("false")
    private boolean isFavourite;

    @ColumnDefault("false")
    private boolean didReview;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ReviewId")
    private Review review;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "GradeId")
    private Grade grade;
}