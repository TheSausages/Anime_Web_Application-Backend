package pwr.pracainz.services.achievement;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.exceptions.exceptions.AchievementException;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversion;

import java.io.IOException;

import static pwr.pracainz.utils.UserAuthorizationUtilities.checkIfLoggedUser;
import static pwr.pracainz.utils.UserAuthorizationUtilities.getIdOfCurrentUser;

@Log4j2
@Service
public class AchievementService implements AchievementServiceInterface {
	private final DTOConversion dtoConversion;
	private final SseEmitter emitter;

	@Autowired
	AchievementService(DTOConversion dtoConversion) {
		this.dtoConversion = dtoConversion;

		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		emitter.onCompletion(() -> log.info("Achievement emitter ended"));
		emitter.onTimeout(() -> log.info("Achievement sending timeout"));
		this.emitter = emitter;

		log.info("Created Achievement SseEmitter");
	}

	@Override
	public SseEmitter achievementEmitter() {
		if (!checkIfLoggedUser()) {
			throw new AuthenticationException("Only a logged in User can earn achievements");
		}

		return emitter;
	}

	@Override
	public void emitAchievement(Achievement achievement) {
		if (!checkIfLoggedUser()) {
			throw new AuthenticationException("Only a logged in User can earn achievements");
		}

		try {
			emitter.send(SseEmitter.event()
					.name(achievement.getName())
					.data(dtoConversion.convertToDTO(achievement), MediaType.APPLICATION_JSON));

			log.info("Achievement '{}' successfully emitted to user with id:{}", achievement.getName(), getIdOfCurrentUser());
		} catch (IOException ex) {
			log.error("Error occurred during achievement emitting for user:{}, \n {}", getIdOfCurrentUser(), ex);

			emitter.completeWithError(new AchievementException(
					"An achievement was added, but notification could not be send"
			));
		}
	}
}
