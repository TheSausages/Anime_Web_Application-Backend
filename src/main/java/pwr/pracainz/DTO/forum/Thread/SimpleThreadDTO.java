package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleThreadDTO {
    private int threadId;

    @NotEmpty
    private String title;
    @Min(value = 0)
    private long nrOfPosts;
    private ThreadStatus status;
    private ForumCategoryDTO category;
    private Set<TagDTO> tags;
}
