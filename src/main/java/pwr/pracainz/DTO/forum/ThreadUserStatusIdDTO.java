package pwr.pracainz.DTO.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadUserStatusIdDTO {
    private SimpleUserDTO user;
    private SimpleThreadDTO thread;
}
