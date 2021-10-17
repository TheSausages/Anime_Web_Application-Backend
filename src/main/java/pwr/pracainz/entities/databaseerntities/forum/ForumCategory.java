package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ForumCategories")
public class ForumCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;

	@NotEmpty
	private String categoryName;

	private String categoryDescription;

	@OneToMany(mappedBy = "category")
	private Set<Thread> threads;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ForumCategory that = (ForumCategory) o;
		return categoryId == that.categoryId && Objects.equals(categoryName, that.categoryName) && Objects.equals(categoryDescription, that.categoryDescription) && Objects.equals(threads, that.threads);
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, categoryName, categoryDescription, threads);
	}

	@Override
	public String toString() {
		return "ForumCategory \n" +
				"   categoryId=" + categoryId + "\n" +
				"   categoryName='" + categoryName + "\n" +
				"   categoryDescription='" + categoryDescription;
	}
}
