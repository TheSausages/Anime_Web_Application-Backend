package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.*;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing the composite primary key of the {@link AnimeUserInfo} class.
 * It consists of an {@link User} and an {@link Anime}.
 */
@Embeddable
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimeUserInfoId implements Serializable {

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "animeId")
	private Anime anime;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AnimeUserInfoId that = (AnimeUserInfoId) o;
		return Objects.equals(anime, that.anime) && Objects.equals(user, that.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, anime);
	}
}