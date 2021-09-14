package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompleteThreadDTO extends SimpleThreadDTO {
    private String text;
    private PageDTO<CompletePostDTO> posts;

    public CompleteThreadDTO(int id, String title, String text, int NrOfPosts, ThreadStatus status, LocalDateTime creation, LocalDateTime modification, SimpleUserDTO creator, ForumCategoryDTO category, List<TagDTO> tags, PageDTO<CompletePostDTO> posts) {
        super(id, title, NrOfPosts, status, creation, modification, creator, category, tags);
        this.text = text;
        this.posts = posts;
    }
}
