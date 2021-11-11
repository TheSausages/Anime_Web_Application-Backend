package pwr.pracainz.services.icon;

import pwr.pracainz.DTO.user.AchievementDTO;
import pwr.pracainz.entities.databaseerntities.user.Achievement;

import java.io.IOException;

/**
 * Interface for an Icon Service. Each implementation must use this interface.
 */
public interface IconServiceInterface {
	/**
	 * Get the icon for an achievement. Allows for custom error handling.
	 * @param achievement Achievement for which the icon should be found
	 * @return Icon for the achievement in form of a byte array
	 * @throws IOException Thrown if no icon was found
	 */
	byte[] getAchievementIcon(Achievement achievement) throws IOException;

	/**
	 * Get a complete {@link AchievementDTO} instance using an {@link Achievement} instance.
	 * @param achievement Achievement for which an {@link AchievementDTO} should be created
	 * @return {@link AchievementDTO}
	 */
	AchievementDTO getAchievementDtoWithIcon(Achievement achievement);
}
