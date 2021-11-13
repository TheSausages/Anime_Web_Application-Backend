package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Complete domain class for {@link pwr.pracainz.entities.databaseerntities.forum.Thread} class.
 * It holds extensive information on a thread, ex. it's posts.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompleteThreadDTO extends SimpleThreadDTO {

	@NotBlank(message = "Thread text cannot be blank")
	@Size(max = 600, message = "Thread text is to long")
	private String text;

	@NotNull(message = "Posts cannot be null value")
	@Valid
	private PageDTO<CompletePostDTO> posts;

	/**
	 * Constructor for a complete thread DTO. Uses {@link SimpleThreadDTO} constructor.
	 * @param id Id of the thread
	 * @param title Title of the thread
	 * @param text Text of the thread
	 * @param NrOfPosts How many posts does a thread have
	 * @param status Current status of the thread
	 * @param creation When was the thread created
	 * @param modification When was the last time the thread was modified
	 * @param creator Who created the thread
	 * @param category To which category does the thread belong
	 * @param tags What tags does the thread have
	 * @param posts Posts of the thread
	 * @param userStatus Status of the current user on the thread
	 */
	public CompleteThreadDTO(int id, String title, String text, int NrOfPosts, ThreadStatus status, LocalDateTime creation, LocalDateTime modification, SimpleUserDTO creator, ForumCategoryDTO category, List<TagDTO> tags, PageDTO<CompletePostDTO> posts, ThreadUserStatusDTO userStatus) {
		super(id, title, NrOfPosts, status, creation, modification, creator, category, tags, userStatus);
		this.text = text;
		this.posts = posts;
	}
}
