package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static AnimeUserInfo getEmptyUserInfo(AnimeUserInfoId animeUserInfoId) {
        return AnimeUserInfo.builder()
                .animeUserInfoId(animeUserInfoId)
                .status(AnimeUserStatus.NO_STATUS)
                .nrOfEpisodesSeen(0)
                .isFavourite(false)
                .didReview(false)
                .review(null)
                .grade(null)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeUserInfo that = (AnimeUserInfo) o;
        return nrOfEpisodesSeen == that.nrOfEpisodesSeen && isFavourite == that.isFavourite && didReview == that.didReview && Objects.equals(animeUserInfoId, that.animeUserInfoId) && status == that.status && Objects.equals(watchStartDate, that.watchStartDate) && Objects.equals(watchEndDate, that.watchEndDate) && Objects.equals(review, that.review) && Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animeUserInfoId, status, watchStartDate, watchEndDate, nrOfEpisodesSeen, isFavourite, didReview, review, grade);
    }
}