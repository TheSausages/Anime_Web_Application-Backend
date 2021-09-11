package pwr.pracainz.services.forum.category;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.repositories.forum.ForumCategoryRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ForumCategoryService implements ForumCategoryServiceInterface {
    private final ForumCategoryRepository categoryRepository;
    private final DTOConversionInterface<?> dtoConversion;

    @Autowired
    ForumCategoryService(ForumCategoryRepository forumCategoryRepository, DTOConversionInterface<?> dtoConversion) {
        categoryRepository = forumCategoryRepository;
        this.dtoConversion = dtoConversion;
    }

    @Override
    public List<ForumCategoryDTO> getAllCategories() {
        log.info("Get all Forum Categories");

        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "categoryName"))
                .stream().map(dtoConversion::convertToDTO).collect(Collectors.toList());
    }
}
