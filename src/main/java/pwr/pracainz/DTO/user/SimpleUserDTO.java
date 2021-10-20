package pwr.pracainz.DTO.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleUserDTO {
	@Pattern(regexp = "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}", message = "User id doesnt have the correct structure")
	private String userId;

	@Size(max = 45, message = "Username is too long")
	private String username;

	@Min(value = 0, message = "Nr of posts cannot be negative")
	private int nrOfPosts;

	@Min(value = 0, message = "Watch time cannot be negative")
	private int watchTime;

	@Min(value = 0, message = "Achievement points cannot be negative")
	private long achievementPoints;
}
