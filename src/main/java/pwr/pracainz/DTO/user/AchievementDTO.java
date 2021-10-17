package pwr.pracainz.DTO.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementDTO {
	@Positive(message = "Achievement id cannot be negative")
	int achievementId;

	@NotBlank(message = "Achievement name cannot be blank")
	@Size(max = 45, message = "Achievement name is too long")
	String name;

	@NotBlank(message = "Achievement description cannot be blank")
	@Size(max = 600, message = "Achievement description is too long")
	String description;

	@NotBlank(message = "Icon path for achievement not found")
	@Size(max = 45, message = "Icon path for achievement is too long")
	String iconPath;

	@Positive(message = "Achievement points cannot be negative")
	int achievementPoints;

	@Min(value = 0, message = "Nr. of people who have an achievement cannot be negative")
	int numberOfUsersThatPosses;
}
