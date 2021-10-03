package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompletePostDTO extends SimplePostDTO {
    @NotBlank(message = "Post text cannot be blank")
    @Size(max = 600, message = "Post text is to long")
    private String text;

    @Min(value = 0, message = "Number of upvotes must be positive")
    private int nrOfPlus;

    @Min(value = 0, message = "Number of downvotes must be positive")
    private int nrOfMinus;

    @Valid
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
