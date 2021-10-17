package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleThreadDTO {
	@Positive(message = "Thread Id must be positive")
	private int threadId;

	@NotBlank(message = "Thread title cannot be blank")
	@Size(max = 80, message = "Thread title is to long")
	private String title;

	@Min(value = 0, message = "Nr. of Posts cannot be negative")
	private long nrOfPosts;

	@NotNull(message = "Status cannot be null")
	private ThreadStatus status;

	@NotNull(message = "Creation date cannot be null")
	@PastOrPresent(message = "Creation date cannot be in the future")
	private LocalDateTime creation;

	@NotNull(message = "Modification date cannot be null")
	@PastOrPresent(message = "Modification date cannot be in the future")
	private LocalDateTime modification;

	@NotNull(message = "Thread creator cannot be null")
	@Valid
	private SimpleUserDTO creator;

	@NotNull(message = "Category cannot be null")
	@Valid
	private ForumCategoryDTO category;

	@NotEmpty(message = "There must be at least 1 tag in a thread")
	@Valid
	private List<TagDTO> tags;
}
