package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Anime")
public class Anime {
	@Id
	@Positive
	private int animeId;

	@Min(value = 0)
	@ColumnDefault("0")
	private double averageScore;

	@Min(value = 0)
	@ColumnDefault("0")
	private int nrOfScores;

	@Min(value = 0)
	@ColumnDefault("0")
	private int nrOfFavourites;

	@Min(value = 0)
	@ColumnDefault("0")
	private int nrOfReviews;

	@Min(value = 0)
	@ColumnDefault("25")
	private int averageEpisodeLength;

	@OneToMany(
			mappedBy = "animeUserInfoId.anime",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private Set<AnimeUserInfo> animeUserInfos;

	public Anime(int animeId) { this.animeId = animeId; }

	public Anime(int animeId, int averageEpisodeLength) {
		this.animeId = animeId;
		this.averageEpisodeLength = averageEpisodeLength;
	}

	public void updateAverageScore(int grade) {
		nrOfScores++;

		//https://stackoverflow.com/questions/12636613/how-to-calculate-moving-average-without-keeping-the-count-and-data-total
		averageScore = (averageScore * ((double) (nrOfScores - 1)) / nrOfScores) + ((double) grade / nrOfScores);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Anime anime = (Anime) o;
		return animeId == anime.animeId && averageScore == anime.averageScore && nrOfScores == anime.nrOfScores && nrOfFavourites == anime.nrOfFavourites && nrOfReviews == anime.nrOfReviews;
	}

	@Override
	public int hashCode() {
		return Objects.hash(animeId, averageScore, nrOfScores, nrOfFavourites, nrOfReviews);
	}
}
