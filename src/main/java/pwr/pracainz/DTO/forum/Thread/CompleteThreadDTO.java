package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.Post.SimplePostDTO;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompleteThreadDTO {
    private SimpleThreadDTO threadInformation;
    private Set<SimplePostDTO> posts;
}
