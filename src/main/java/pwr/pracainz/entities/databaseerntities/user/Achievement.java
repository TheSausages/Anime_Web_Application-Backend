package pwr.pracainz.entities.databaseerntities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing the <i>Achievements</i> table from the database.
 */
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Achievements")
@Entity
public class Achievement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int achievementId;

	@NotEmpty
	private String name;

	@NotNull
	private String description;

	@NotEmpty
	private String iconPath;

	@Positive
	private int achievementPoints;

	@ManyToMany(mappedBy = "achievements")
	private Set<User> users;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Achievement that = (Achievement) o;
		return achievementId == that.achievementId && achievementPoints == that.achievementPoints && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(iconPath, that.iconPath);
	}

	@Override
	public int hashCode() {
		return Objects.hash(achievementId, name, description, iconPath, achievementPoints);
	}
}
