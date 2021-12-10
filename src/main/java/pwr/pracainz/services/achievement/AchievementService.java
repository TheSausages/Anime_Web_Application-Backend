package pwr.pracainz.services.achievement;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pwr.pracainz.configuration.factories.sseemitter.SseEmitterFactoryInterface;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.entities.events.AchievementEarnedEvent;
import pwr.pracainz.exceptions.exceptions.AchievementException;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversion;
import pwr.pracainz.services.i18n.I18nServiceInterface;
import pwr.pracainz.services.icon.IconServiceInterface;
import pwr.pracainz.services.user.UserServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation for the {@link AchievementServiceInterface} interface.
 */
@Log4j2
@Service
public class AchievementService implements AchievementServiceInterface {
	private final DTOConversion dtoConversion;
	private final UserServiceInterface userService;
	private final I18nServiceInterface i18nService;
	private final IconServiceInterface iconService;
	private final SseEmitterFactoryInterface emitterFactory;

	private final Map<String, SseEmitter> emitterMap;

	@Autowired
	AchievementService(DTOConversion dtoConversion,
	                   UserServiceInterface userService,
	                   IconServiceInterface iconService,
	                   I18nServiceInterface i18nService,
	                   SseEmitterFactoryInterface emitterFactory) {
		this.dtoConversion = dtoConversion;
		this.userService = userService;
		this.iconService = iconService;
		this.i18nService = i18nService;
		this.emitterFactory = emitterFactory;

		emitterMap = new ConcurrentHashMap<>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * Each user has its own {@link SseEmitter}.
	 * @return An {@link SseEmitter} used for a given user.
	 */
	@Override
	public SseEmitter subscribe() {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.only-logged-in-can-earn-achievements"),
					"A not logged in user tried to subscribe to achievements");
		}

		String userId = UserAuthorizationUtilities.getIdOfCurrentUser();
		if (emitterMap.containsKey(userId)) {
			return emitterMap.get(userId);
		} else {
			SseEmitter emitter = emitterFactory.createNewSseEmitter();

			emitterMap.put(userId, emitter);

			log.info("User {} subscribed to achievement emitter", userService.getUsernameOfCurrentUser());

			return emitter;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cancel() {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.error-during-authentication"),
					"A not logged in user tried to cancel subscription to achievements");
		}

		String userId = UserAuthorizationUtilities.getIdOfCurrentUser();
		emitterMap.computeIfPresent(userId, (id, emitter) -> {
			emitter.complete();
			return null;
		});

		log.info("User {} canceled subscription to achievements", userService.getUsernameOfCurrentUser());
	}

	/*
	This code can be used to try if async works
		try {
			sleep(5000);
			System.out.println("ach:" + LocalDateTime.now());
		} catch (Exception e) {
		}
	 */
	/**
	 * {@inheritDoc}
	 *
	 * When an error occurs, emit an {@link AchievementException} with a message.
	 */
	@EventListener
	public void emitAchievement(AchievementEarnedEvent event) {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			//Don't emit an achievement if the user is not logged in
			return;
		}

		Achievement earnedAchievement = (Achievement) event.getSource();
		SseEmitter emitter = emitterMap.get(UserAuthorizationUtilities.getIdOfCurrentUser());

		try {
			emitter.send(SseEmitter.event()
					.name(earnedAchievement.getName())
					.data(dtoConversion.convertToDTO(earnedAchievement, iconService.getAchievementIcon(earnedAchievement)), MediaType.APPLICATION_JSON));

			log.info("Achievement '{}' successfully emitted to user {}", earnedAchievement.getName(), userService.getUsernameOfCurrentUser());
		} catch (Exception ex) {
			log.error("Error occurred during achievement emitting for user with id {}", UserAuthorizationUtilities.getIdOfCurrentUser());

			emitterMap.computeIfPresent(UserAuthorizationUtilities.getIdOfCurrentUser(), (id, emmiter) -> {
				emitter.completeWithError(new AchievementException(
						i18nService.getTranslation("general.error-during-achievement-emission")
				));

				return null;
			});
		}
	}
}