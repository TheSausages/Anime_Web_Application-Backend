package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimeUserInfoId implements Serializable {
    @NotEmpty
    private String userId;

    @Positive
    private int animeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeUserInfoId that = (AnimeUserInfoId) o;
        return animeId == that.animeId && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, animeId);
    }
}