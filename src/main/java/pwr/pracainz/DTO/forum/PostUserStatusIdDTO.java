package pwr.pracainz.DTO.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.Post.SimplePostDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUserStatusIdDTO {
    private SimpleUserDTO user;
    private SimplePostDTO post;
}
