package pwr.pracainz.DTO.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Domain class for the {@link pwr.pracainz.entities.databaseerntities.forum.ForumCategory} class.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForumCategoryDTO {
	@Positive(message = "Category id cannot be negative")
	private int categoryId;

	@NotBlank(message = "Category name cannot be blank")
	@Size(max = 45, message = "Category name is too long")
	private String categoryName;

	@Size(max = 150, message = "Description is too long")
	private String categoryDescription;
}
