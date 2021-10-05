package pwr.pracainz.DTO.forum.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.user.SimpleUserDTO;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePostDTO {
    @Positive(message = "Post Id must be positive!")
    private int postId;

    @NotBlank(message = "Post title cannot be blank")
    @Size(max = 80, message = "Post title is to long")
    private String title;

    private boolean isBlocked;

    @NotNull(message = "Creation date cannot be null")
    @PastOrPresent(message = "Creation date cannot be in the future")
    private LocalDateTime creation;

    @NotNull(message = "Modification date cannot be null")
    @PastOrPresent(message = "Modification date cannot be in the future")
    private LocalDateTime modification;

    @NotNull(message = "Creator cannot be null")
    @Valid
    private SimpleUserDTO user;
}
