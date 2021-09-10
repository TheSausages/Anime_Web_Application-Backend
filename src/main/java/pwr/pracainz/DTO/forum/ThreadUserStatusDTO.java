package pwr.pracainz.DTO.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(value = "isWatching")
    private boolean isWatching;

    @JsonProperty(value = "isBlocked")
    private boolean isBlocked;
}
