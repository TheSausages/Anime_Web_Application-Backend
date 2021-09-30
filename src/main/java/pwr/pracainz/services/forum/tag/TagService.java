package pwr.pracainz.services.forum.tag;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.entities.databaseerntities.forum.Tag;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.TagRepository;

@Service
@Log4j2
public class TagService implements TagServiceInterface {
    private final TagRepository tagRepository;

    @Autowired
    TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findTagByIdAndName(int id, String name) {
        return tagRepository.findByTagIdAndTagName(id, name)
                .orElseThrow(() -> new ObjectNotFoundException("No Tag with name '" + name + "' and id '" + id + "' found!"));
    }
}
