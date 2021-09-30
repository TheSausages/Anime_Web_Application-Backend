package pwr.pracainz.services.forum.category;

import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;

import java.util.List;

public interface ForumCategoryServiceInterface {
    List<ForumCategoryDTO> getAllCategories();

    ForumCategory findCategoryByIdAndName(int id, String name);
}
