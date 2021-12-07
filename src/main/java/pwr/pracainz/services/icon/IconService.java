package pwr.pracainz.services.icon;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.user.AchievementDTO;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.user.AchievementRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Default implementation for the {@link IconServiceInterface} interface.
 */
@Log4j2
@Service
public class IconService implements IconServiceInterface {
	private final static String defaultAchievementIconPath = "achievements/Default.png";

	private final DTOConversionInterface dtoConversion;
	private final AchievementRepository achievementRepository;
	private final byte[] defaultAchievementIcon;

	IconService(DTOConversionInterface dtoConversion, AchievementRepository achievementRepository) {
		this.dtoConversion = dtoConversion;
		this.achievementRepository = achievementRepository;

		try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(defaultAchievementIconPath)) {
			if (Objects.isNull(stream)) {
				throw new IOException("The default achievement icon stream is null");
			}

			defaultAchievementIcon = IOUtils.toByteArray(stream);
		} catch (Exception e) {
			throw new BeanInitializationException("Could not find the default achievement icon", e);
		}
	}

	/**
	 * Variant of {@link #getAchievementIcon(Achievement)} that searches for the achievement using its id.
	 * @throws ObjectNotFoundException When no achievement with the id was found
	 */
	public byte[] getAchievementIcon(int id) throws IOException {
		return getAchievementIcon(achievementRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Could not find achievement with id " + id))
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getAchievementIcon(Achievement achievement) throws IOException {
		log.info("Try to get Icon with path:" + achievement.getIconPath());

		InputStream in = this.getClass().getClassLoader().getResourceAsStream(achievement.getIconPath());

		if (Objects.isNull(in)) {
			throw new IOException("Could not find icon with path: " + achievement.getIconPath());
		}

		return IOUtils.toByteArray(in);
	}

	/**
	 * {@inheritDoc}
	 *
	 * For this implementation, if no icon was found use a default icon.
	 */
	@Override
	public AchievementDTO getAchievementDtoWithIcon(Achievement achievement) {
		log.info("Create Achievement DTO for {}", achievement.getName());

		try {
			return dtoConversion.convertToDTO(achievement, this.getAchievementIcon(achievement));
		} catch (IOException e) {
			log.warn("Error occurred during Achievement DTO creation: {}, \n Using default achievement icon with name {}", e.getMessage(), defaultAchievementIconPath);

			return dtoConversion.convertToDTO(achievement, defaultAchievementIcon);
		}
	}
}
