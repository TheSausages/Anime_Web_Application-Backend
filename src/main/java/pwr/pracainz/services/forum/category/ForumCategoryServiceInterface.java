package pwr.pracainz.services.forum.category;

import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;

import java.util.List;

/**
 * Interface for a Forum Category Service. Each implementation must be use this interface.
 */
public interface ForumCategoryServiceInterface {
	/**
	 * Get all forum categories found in the database.
	 * @return List of {@link ForumCategory}
	 */
	List<ForumCategoryDTO> getAllCategories();

	/**
	 * Find a {@link ForumCategory} by its id and name.
	 * @param id Id of the searched category
	 * @param name Name of the searched category
	 * @return The found Category
	 */
	ForumCategory findCategoryByIdAndNameOrNull(int id, String name);

	/**
	 * Variant of {@link #findCategoryByIdAndNameOrNull(int, String)} that uses the DTO object instead of id and name.
	 * Additionally, it returns null if not found, instead of throwing an error.
	 * @param categoryDTO DTO object used to search
	 * @return The found Category or null
	 */
	ForumCategory findCategoryByIdAndNameOrNull(ForumCategoryDTO categoryDTO);
}
