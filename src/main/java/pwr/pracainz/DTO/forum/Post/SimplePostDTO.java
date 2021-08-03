package pwr.pracainz.DTO.forum.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.user.SimpleUserDTO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SimplePostDTO {
    private int postId;
    private String title;
    private boolean isBlocked;
    private SimpleUserDTO user;
}
