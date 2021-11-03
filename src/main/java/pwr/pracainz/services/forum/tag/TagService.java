package pwr.pracainz.services.forum.tag;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.entities.databaseerntities.forum.Tag;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.TagRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.i18n.I18nServiceInterface;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TagService implements TagServiceInterface {
	private final TagRepository tagRepository;
	private final I18nServiceInterface i18nService;
	private final DTOConversionInterface dtoConversion;

	@Autowired
	TagService(TagRepository tagRepository, I18nServiceInterface i18nService, DTOConversionInterface dtoConversion) {
		this.tagRepository = tagRepository;
		this.i18nService = i18nService;
		this.dtoConversion = dtoConversion;
	}

	@Override
	public List<TagDTO> getAllTags() {
		log.info("Find all tags");

		return tagRepository.findAll().stream().map(dtoConversion::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public Tag findTagByIdAndName(int id, String name) {
		log.info("Find a tag with id {} and name {}", id, name);

		return tagRepository.findByTagIdAndTagName(id, name)
				.orElseThrow(() -> new ObjectNotFoundException(i18nService.getTranslation("forum.no-such-tag", name, id),
						String.format("No Tag with name %s (id: %s}) found", name, id)));
	}


}
