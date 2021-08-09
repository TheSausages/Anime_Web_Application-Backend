package pwr.pracainz.DTO.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadUserStatusDTO {
    private ThreadUserStatusIdDTO ids;
    private boolean isWatching;
    private boolean isBlocked;
}
