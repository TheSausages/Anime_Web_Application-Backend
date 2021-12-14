package pwr.pracainz.DTO.forum;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Transient;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Query used when a user searcher the for Threads using selected conditions.
 * No fields should be mandatory.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
//Builder is for testing in #ForumIntegrationTests
@Builder
public class ForumQuery {
	@Transient
	private final LocalDateTime queryCreationTime = LocalDateTime.now();

	@PastOrPresent(message = "Minimal creation time cannot be in the future")
	@Builder.Default
	private LocalDateTime minCreation = LocalDateTime.MIN;

	@PastOrPresent(message = "Maximal creation time cannot be in the future")
	@Builder.Default
	private LocalDateTime maxCreation = LocalDateTime.now();

	@PastOrPresent(message = "Minimal modification time cannot be in the future")
	@Builder.Default
	private LocalDateTime minModification = LocalDateTime.MIN;

	@PastOrPresent(message = "Maximal modification time cannot be in the future")
	@Builder.Default
	private LocalDateTime maxModification = LocalDateTime.now();

	@Min(value = 0, message = "Minimal number of posts cannot be negative")
	@Builder.Default
	private int maxNrOfPosts = Integer.MAX_VALUE;

	@Min(value = 0, message = "Maximal number of posts cannot be negative")
	@Builder.Default
	private int minNrOfPosts = 0;

	@Builder.Default
	private ForumCategoryDTO category = null;

	@Builder.Default
	private String title = "";

	@Builder.Default
	private String creatorUsername = "";

	@Builder.Default
	private ThreadStatus status = null;

	@Valid
	@Builder.Default
	private List<TagDTO> tags = Collections.emptyList();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (!LocalDateTime.MIN.equals(minCreation) && !queryCreationTime.equals(maxCreation)) {
			builder.append("\n creation date between ").append(minCreation).append(" and ").append(maxCreation);
		} else {
			if (!LocalDateTime.MIN.equals(minCreation)) {
				builder.append("\n creation date is at least ").append(minCreation);
			}

			if (!queryCreationTime.equals(maxCreation)) {
				builder.append("\n creation date is at max ").append(maxCreation);
			}
		}

		if (!LocalDateTime.MIN.equals(minModification) && !queryCreationTime.equals(maxModification)) {
			builder.append("\n modification date between ").append(minModification).append(" and ").append(maxModification);
		} else {
			if (!LocalDateTime.MIN.equals(minModification)) {
				builder.append("\n modification date is at least ").append(minModification);
			}

			if (!queryCreationTime.equals(maxModification)) {
				builder.append("\n modification date is at max ").append(maxModification);
			}
		}

		if (minNrOfPosts != 0 && maxNrOfPosts != Integer.MAX_VALUE) {
			builder.append("\n nr. of posts between ").append(minNrOfPosts).append(" and ").append(maxNrOfPosts);
		} else {
			if (minNrOfPosts != 0) {
				builder.append("\n nr. of posts at least ").append(minNrOfPosts);
			}

			if (maxNrOfPosts != Integer.MAX_VALUE) {
				builder.append("\n nr. of posts at most ").append(maxNrOfPosts);
			}
		}

		if (Objects.nonNull(category)) {
			builder.append("\n category = '").append(category).append("'");
		}

		if (!title.isEmpty()) {
			builder.append("\n title contains '").append(title).append("'");
		}

		if (!creatorUsername.isEmpty()) {
			builder.append("\n creator username contains '").append(creatorUsername).append("'");
		}

		if (!tags.isEmpty()) {
			builder.append("\n tags = ").append(tags);
		}

		if (Objects.nonNull(status)) {
			builder.append("\n status = ").append(status);
		}

		return builder.toString();
	}
}
