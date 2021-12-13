package pwr.pracainz.services.forum.category;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.ForumCategoryRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.i18n.I18nServiceInterface;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Default implementation for the {@link ForumCategoryServiceInterface} interface.
 */
@Log4j2
@Service
public class ForumCategoryService implements ForumCategoryServiceInterface {
	private final ForumCategoryRepository categoryRepository;
	private final I18nServiceInterface i18nService;
	private final DTOConversionInterface dtoConversion;

	@Autowired
	ForumCategoryService(ForumCategoryRepository forumCategoryRepository, DTOConversionInterface dtoConversion, I18nServiceInterface i18nService) {
		categoryRepository = forumCategoryRepository;
		this.i18nService = i18nService;
		this.dtoConversion = dtoConversion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForumCategoryDTO> getAllCategories() {
		log.info("Get all Forum Categories");

		return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "categoryName"))
				.stream().map(dtoConversion::convertToDTO).collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumCategory findCategoryByIdAndNameOrNull(int id, String name) {
		return categoryRepository.findForumCategoryByCategoryIdAndCategoryName(id, name)
				.orElseThrow(() -> new ObjectNotFoundException(i18nService.getTranslation("forum.no-such-category", name),
						"No category with name '" + name + "' and id '" + id + "' found!"));
	}

	@Override
	public ForumCategory findCategoryByIdAndNameOrNull(ForumCategoryDTO categoryDTO) {
		if (Objects.isNull(categoryDTO)) return null;

		return categoryRepository.findForumCategoryByCategoryIdAndCategoryName(
						categoryDTO.getCategoryId(),
						categoryDTO.getCategoryName()
				)
				.orElse(null);
	}
}