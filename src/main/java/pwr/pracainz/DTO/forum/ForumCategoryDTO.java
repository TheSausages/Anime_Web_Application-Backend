package pwr.pracainz.DTO.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ForumCategoryDTO {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
}
