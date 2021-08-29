package pwr.pracainz.services.forum.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.repositories.forum.ForumCategoryRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumCategoryService implements ForumCategoryServiceInterface {
    private final ForumCategoryRepository categoryRepository;
    private final DTOConversionInterface dtoConversion;

    @Autowired
    ForumCategoryService(ForumCategoryRepository forumCategoryRepository, DTOConversionInterface dtoConversion) {
        categoryRepository = forumCategoryRepository;
        this.dtoConversion = dtoConversion;
    }

    @Override
    public List<ForumCategoryDTO> getAllCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "categoryName"))
                .stream().map(dtoConversion::convertForumCategoryToForumDTO).collect(Collectors.toList());
    }
}
