package pwr.pracainz.DTO.forum.Thread;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SimpleThreadDTO {
    private int threadId;
    private String title;
    private ThreadStatus status;
    private ForumCategoryDTO category;
    private Set<TagDTO> tags;
}
