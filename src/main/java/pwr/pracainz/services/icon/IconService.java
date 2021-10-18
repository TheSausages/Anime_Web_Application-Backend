package pwr.pracainz.services.icon;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import pwr.pracainz.entities.databaseerntities.user.Achievement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Log4j2
@Service
public class IconService implements IconServiceInterface {
	@Override
	public byte[] getAchievementIcon(Achievement achievement) throws IOException {
		log.info("Try to get Icon with path:" + achievement.getIconPath());

		InputStream in = this.getClass().getClassLoader().getResourceAsStream(achievement.getIconPath());

		if (Objects.isNull(in)) {
			throw new IOException("Could not find icon with path:" + achievement.getIconPath());
		}

		return IOUtils.toByteArray(in);
	}
}
