package pwr.pracainz.DTO.animeInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnimeQuery {
	@Transient
	private LocalDateTime queryCreationTime = LocalDateTime.now();

	@PastOrPresent(message = "Minimal creation time cannot be in the future")
	private LocalDateTime minCreation = LocalDateTime.MIN;

	@PastOrPresent(message = "Maximal creation time cannot be in the future")
	private LocalDateTime maxCreation = queryCreationTime;

	@PastOrPresent(message = "Minimal modification time cannot be in the future")
	private LocalDateTime minModification = LocalDateTime.MIN;

	@PastOrPresent(message = "Maximal modification time cannot be in the future")
	private LocalDateTime maxModification = queryCreationTime;
}
