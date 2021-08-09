package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleThreadDTO {
    private int threadId;
    private String title;
    private ThreadStatus status;
    private ForumCategoryDTO category;
    private Set<TagDTO> tags;
}
