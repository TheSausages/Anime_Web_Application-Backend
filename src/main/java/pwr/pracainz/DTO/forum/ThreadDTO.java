package pwr.pracainz.DTO.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ThreadDTO {
    private int threadId;
    private String title;
    private ThreadStatus status;
    private ForumCategoryDTO category;
    private Set<TagDTO> tags;
    private Set<PostDTO> posts;
}
