package pwr.pracainz.services.forum.tag;

import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.entities.databaseerntities.forum.Tag;

import java.util.List;

/**
 * Interface for a Tag Service. Each implementation must use this interface.
 */
public interface TagServiceInterface {
	/**
	 * Get all forum tags from the database.
	 * @return List of tags already converted to {@link TagDTO}
	 */
	List<TagDTO> getAllTags();

	/**
	 * Find a forum Tag by its id and name.
	 * @param id Id of the searched Tag
	 * @param name Name of the searched Tag
	 * @return The found tag
	 */
	Tag findTagByIdAndName(int id, String name);
}
