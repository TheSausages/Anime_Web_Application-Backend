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

    @Min(1)
    @ColumnDefault("5")
    private Integer grade;

    @ColumnDefault("false")
    private boolean didReview;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ReviewId")
    private Review review;

    public static AnimeUserInfo getEmptyAnimeUserInfo(AnimeUserInfoId animeUserInfoId) {
        return new AnimeUserInfo(animeUserInfoId, AnimeUserStatus.NO_STATUS, null, null, 0
                , false, null, false, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeUserInfo that = (AnimeUserInfo) o;
        return nrOfEpisodesSeen == that.nrOfEpisodesSeen && isFavourite == that.isFavourite && didReview == that.didReview && animeUserInfoId.equals(that.animeUserInfoId) && status == that.status && Objects.equals(watchStartDate, that.watchStartDate) && Objects.equals(watchEndDate, that.watchEndDate) && Objects.equals(review, that.review) && Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animeUserInfoId, status, watchStartDate, watchEndDate, nrOfEpisodesSeen, isFavourite, didReview, review, grade);
    }

    @Override
    public String toString() {
        return "AnimeUserInfo{" +
                "animeUserInfoId=" + animeUserInfoId +
                ", status=" + status +
                ", watchStartDate=" + watchStartDate +
                ", watchEndDate=" + watchEndDate +
                ", nrOfEpisodesSeen=" + nrOfEpisodesSeen +
                ", isFavourite=" + isFavourite +
                ", didReview=" + didReview +
                ", review=" + review +
                ", grade=" + grade +
                '}';
    }
}