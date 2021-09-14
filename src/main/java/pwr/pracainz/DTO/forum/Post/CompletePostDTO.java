package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompletePostDTO extends SimplePostDTO {
    @NotEmpty
    private String text;
    private int nrOfPlus;
    private int nrOfMinus;
    private PostUserStatusDTO postUserStatus;

    public CompletePostDTO(int id, String title, boolean isBlocked, LocalDateTime creation, LocalDateTime modification, SimpleUserDTO user, String text, int nrOfPlus, int nrOfMinus) {
        super(id, title, isBlocked, creation, modification, user);
        this.text = text;
        this.nrOfPlus = nrOfPlus;
        this.nrOfMinus = nrOfMinus;
        this.postUserStatus = null;
    }

    public CompletePostDTO(int id, String title, boolean isBlocked, LocalDateTime creation, LocalDateTime modification, SimpleUserDTO user, String text, int nrOfPlus, int nrOfMinus, PostUserStatusDTO postUserStatus) {
        super(id, title, isBlocked, creation, modification, user);
        this.text = text;
        this.nrOfPlus = nrOfPlus;
        this.nrOfMinus = nrOfMinus;
        this.postUserStatus = postUserStatus;
    }
}
