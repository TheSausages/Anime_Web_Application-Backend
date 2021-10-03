package pwr.pracainz.DTO.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThreadUserStatusDTO {
    @NotNull(message = "Thread User Status id cannot be null")
    @Valid
    private ThreadUserStatusIdDTO ids;

    @JsonProperty(value = "isWatching")
    private boolean isWatching;

    @JsonProperty(value = "isBlocked")
    private boolean isBlocked;
}
