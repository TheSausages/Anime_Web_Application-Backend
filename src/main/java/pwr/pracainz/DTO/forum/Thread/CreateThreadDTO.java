package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.TagDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateThreadDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotNull
    private ForumCategoryDTO category;

    @NotEmpty
    private List<TagDTO> tags;
}
