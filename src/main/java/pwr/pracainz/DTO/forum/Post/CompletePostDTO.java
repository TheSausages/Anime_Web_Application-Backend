package pwr.pracainz.DTO.forum.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompletePostDTO {
    private SimplePostDTO postInformation;
    private String postText;
    private int nrOfPlus;
    private int nrOfMinus;
    private SimpleThreadDTO thread;
}
