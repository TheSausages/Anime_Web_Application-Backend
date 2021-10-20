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
	private int averageScore;

	@Min(value = 0)
	@ColumnDefault("0")
	private int nrOfScores;

	@Min(value = 0)
	@ColumnDefault("0")
	private int nrOfFavourites;

	@Min(value = 0)
	@ColumnDefault("0")
	private int nrOfReviews;

	@OneToMany(
			mappedBy = "animeUserInfoId.anime",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private Set<AnimeUserInfo> animeUserInfos;

	public Anime(int animeId) {
		this.animeId = animeId;
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
