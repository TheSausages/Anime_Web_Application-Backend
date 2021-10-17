package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.*;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

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

	@Positive
	private int animeId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AnimeUserInfoId that = (AnimeUserInfoId) o;
		return animeId == that.animeId && Objects.equals(user, that.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, animeId);
	}
}