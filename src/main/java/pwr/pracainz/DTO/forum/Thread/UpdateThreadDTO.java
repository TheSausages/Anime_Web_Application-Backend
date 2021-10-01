package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateThreadDTO extends CreateThreadDTO {
    private ThreadStatus status;

    public UpdateThreadDTO(String title, String text, ForumCategoryDTO category, List<TagDTO> tags, ThreadStatus status) {
        super(title, text, category, tags);
        this.status = status;
    }
}
