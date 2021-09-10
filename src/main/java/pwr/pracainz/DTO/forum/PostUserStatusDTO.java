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
public class PostUserStatusDTO {
    private PostUserStatusIdDTO ids;

    @JsonProperty(value = "isLiked")
    private boolean isLiked;

    @JsonProperty(value = "isDisliked")
    private boolean isDisliked;

    @JsonProperty(value = "isReported")
    private boolean isReported;
}
