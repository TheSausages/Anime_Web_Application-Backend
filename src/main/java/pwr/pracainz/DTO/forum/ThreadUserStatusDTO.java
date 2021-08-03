package pwr.pracainz.DTO.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ThreadUserStatusDTO {
    private ThreadUserStatusIdDTO ids;
    private boolean isWatching;
    private boolean isBlocked;
}
