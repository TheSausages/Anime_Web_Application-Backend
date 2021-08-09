package pwr.pracainz.DTO.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForumCategoryDTO {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
}
