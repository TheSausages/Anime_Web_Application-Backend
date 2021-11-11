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
	ForumCategory findCategoryByIdAndName(int id, String name);
}
