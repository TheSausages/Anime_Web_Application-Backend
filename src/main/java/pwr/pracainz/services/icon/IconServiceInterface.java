package pwr.pracainz.services.icon;

import pwr.pracainz.entities.databaseerntities.user.Achievement;

import java.io.IOException;

public interface IconServiceInterface {
	byte[] getAchievementIcon(Achievement achievement) throws IOException;
}
