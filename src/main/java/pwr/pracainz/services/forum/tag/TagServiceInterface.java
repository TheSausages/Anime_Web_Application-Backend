package pwr.pracainz.services.forum.tag;

import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.entities.databaseerntities.forum.Tag;

import java.util.List;

public interface TagServiceInterface {
    List<TagDTO> getAllTags();

    Tag findTagByIdAndName(int id, String name);
}
