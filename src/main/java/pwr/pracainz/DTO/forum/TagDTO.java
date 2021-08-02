package pwr.pracainz.DTO.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TagDTO {
    private int tagId;
    private String tagName;
    private TagImportance tagImportance;
}
