package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompletePostDTO {
    private SimplePostDTO postInformation;
    @NotEmpty
    private String postText;
    private int nrOfPlus;
    private int nrOfMinus;
    private SimpleThreadDTO thread;
}
