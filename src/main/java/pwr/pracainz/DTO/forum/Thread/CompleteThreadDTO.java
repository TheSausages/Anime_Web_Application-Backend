package pwr.pracainz.DTO.forum.Thread;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.forum.Post.SimplePostDTO;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompleteThreadDTO {
    private SimpleThreadDTO threadInformation;
    private Set<SimplePostDTO> posts;
}
