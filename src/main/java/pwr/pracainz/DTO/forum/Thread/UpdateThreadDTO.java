package pwr.pracainz.DTO.forum.Thread;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateThreadDTO extends CreateThreadDTO {
    @Positive(message = "Thread Id must be positive")
    private int threadId;

    @NotNull(message = "Status cannot be null")
    private ThreadStatus status;

    public UpdateThreadDTO(int threadId, String title, String text, ForumCategoryDTO category, List<TagDTO> tags, ThreadStatus status) {
        super(title, text, category, tags);
        this.threadId = threadId;
        this.status = status;
    }
}
