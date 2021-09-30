package pwr.pracainz.services.forum.tag;

import pwr.pracainz.entities.databaseerntities.forum.Tag;

public interface TagServiceInterface {
    Tag findTagByIdAndName(int id, String name);
}
