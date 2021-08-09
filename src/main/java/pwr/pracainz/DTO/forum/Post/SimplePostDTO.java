package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.user.SimpleUserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePostDTO {
    private int postId;
    private String title;
    private boolean isBlocked;
    private SimpleUserDTO user;
}
