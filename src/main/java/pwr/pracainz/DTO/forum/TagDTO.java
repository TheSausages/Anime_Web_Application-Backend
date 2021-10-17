package pwr.pracainz.DTO.forum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagDTO {
	@Positive(message = "Tag Id must be positive")
	private int tagId;

	@NotBlank(message = "Tag name cannot be blank")
	@Size(max = 45, message = "Tag name is to long")
	private String tagName;

	@NotNull(message = "Tag importance cannopt be null")
	private TagImportance tagImportance;

	@Pattern(regexp = "rgb\\(\\d{1,3}, \\d{1,3}, \\d{1,3}\\)", message = "Tag color has wrong structure")
	private String tagColor;
}
