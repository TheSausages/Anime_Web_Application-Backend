package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.ReviewDTO;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class representing the <i>AnimeUserInfos</i> table from the database.
 * It uses {@link AnimeUserInfoId} as an embedded composite primary id.
 */
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

	@PastOrPresent
	private LocalDateTime modification;

	@ColumnDefault("false")
	private boolean didReview;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ReviewId")
	private Review review;

	/**
	 * Return an empty {@link AnimeUserInfo} object with only the id. Other fields have the default value.
	 * @param animeUserInfoId The id for the new object
	 * @return An empty {@link AnimeUserInfo} with default field values with only the id inserted
	 */
	public static AnimeUserInfo getEmptyAnimeUserInfo(AnimeUserInfoId animeUserInfoId) {
		return new AnimeUserInfo(animeUserInfoId, AnimeUserStatus.NO_STATUS, null, null, 0
				, false, null, LocalDateTime.now(), false, null);
	}

	/**
	 * Copy all data from a {@link AnimeUserInfoDTO} into an existing {@link AnimeUserInfo}.
	 * @param animeUserInfoDTO The DTO from which the data should be copied
	 * @return Updated {@link AnimeUserInfo} object
	 */
	public AnimeUserInfo copyDataFromDTO(AnimeUserInfoDTO animeUserInfoDTO) {
		ReviewDTO reviewDTO = animeUserInfoDTO.getReview();

		this.status = animeUserInfoDTO.getStatus();
		this.watchStartDate = animeUserInfoDTO.getWatchStartDate();
		this.watchEndDate = animeUserInfoDTO.getWatchEndDate();
		this.nrOfEpisodesSeen = animeUserInfoDTO.getNrOfEpisodesSeen();
		this.isFavourite = animeUserInfoDTO.isFavourite();
		this.grade = animeUserInfoDTO.getGrade();
		this.modification = LocalDateTime.now();
		this.didReview = animeUserInfoDTO.isDidReview();
		this.review = this.didReview && Objects.nonNull(reviewDTO) ?
				new Review(
						reviewDTO.getReviewTitle(),
						reviewDTO.getReviewText(),
						reviewDTO.getNrOfHelpful(),
						reviewDTO.getNrOfPlus(),
						reviewDTO.getNrOfMinus()
				)
				:
				null;

		return this;
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