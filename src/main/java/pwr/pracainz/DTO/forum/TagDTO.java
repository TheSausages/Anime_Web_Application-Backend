package pwr.pracainz.DTO.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagDTO {
    private int tagId;
    private String tagName;
    private TagImportance tagImportance;
}
