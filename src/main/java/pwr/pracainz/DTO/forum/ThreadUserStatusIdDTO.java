package pwr.pracainz.DTO.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ThreadUserStatusIdDTO {
    private SimpleUserDTO user;
    private SimpleThreadDTO thread;
}
