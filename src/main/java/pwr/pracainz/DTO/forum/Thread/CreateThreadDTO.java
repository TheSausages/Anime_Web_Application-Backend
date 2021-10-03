package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.TagDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateThreadDTO {
    @NotBlank(message = "Thread title cannot be blank")
    @Size(max = 80, message = "Thread title is to long")
    private String title;

    @NotBlank(message = "Thread text cannot be blank")
    @Size(max = 600, message = "Thread text is to long")
    private String text;

    @NotNull(message = "Category cannot be null")
    @Valid
    private ForumCategoryDTO category;

    @NotEmpty(message = "There must be at least 1 tag in a thread")
    @Valid
    private List<TagDTO> tags;
}
