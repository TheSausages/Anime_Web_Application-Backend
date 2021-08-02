package pwr.pracainz.DTO.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.user.UserDTO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostDTO {
    private int postId;
    private String title;
    private String postText;
    private boolean isBlocked;
    private int nrOfPlus;
    private int nrOfMinus;
    private UserDTO user;
    private ThreadDTO thread;
}
