package pwr.pracainz.services.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.user.AchievementDTO;
import pwr.pracainz.DTO.user.CompleteUserDTO;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.user.UserRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.i18n.I18nServiceInterface;
import pwr.pracainz.services.icon.IconServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserService implements UserServiceInterface {
	private final UserRepository userRepository;
	private final I18nServiceInterface i18nService;
	private final IconServiceInterface iconService;
	private final DTOConversionInterface dtoConversion;
	private final ApplicationEventPublisher publisher;

	@Autowired
	UserService(UserRepository userRepository, I18nServiceInterface i18nService, IconServiceInterface iconService, DTOConversionInterface dtoConversion, ApplicationEventPublisher publisher) {
		this.userRepository = userRepository;
		this.i18nService = i18nService;
		this.iconService = iconService;
		this.dtoConversion = dtoConversion;
		this.publisher = publisher;
	}

	@Override
	public CompleteUserDTO getCurrentUserInformation() {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
					"No User was logged in");
		}

		return getUserInformationById(getCurrentUser().getUserId(), true);
	}

	@Override
	public CompleteUserDTO getUserInformationById(String userId, boolean isCurrentUser) {
		log.info("Get complete user information for user with id: {}, current User: {}", userId, isCurrentUser);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ObjectNotFoundException(i18nService.getTranslation("general.no-such-user", userId),
						String.format("No User was found for given id: %s", userId)));

		Set<AchievementDTO> achievements = user.getAchievements().stream()
				.map(iconService::getAchievementDtoWithIcon)
				.collect(Collectors.toSet());

		return dtoConversion.convertToDTO(user, achievements);
	}

	@Override
	public User getCurrentUser() {
		return userRepository.findById(UserAuthorizationUtilities.getIdOfCurrentUser())
				.orElseThrow(() -> new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
						String.format("No User was found for given id: %s", UserAuthorizationUtilities.getIdOfCurrentUser())));
	}

	@Override
	public User getCurrentUserOrInsert() {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
					"No User was logged in");
		}

		String currUserId = UserAuthorizationUtilities.getIdOfCurrentUser();

		return userRepository.findById(currUserId)
				.orElseGet(() -> userRepository.save(new User(currUserId)));
	}

	@Override
	public User saveUser(User user) {
		log.info("Save user {} with id {}", user.getUsername(), user.getUserId());

		return userRepository.save(user);
	}

	@Override
	public String getUsernameOfCurrentUser() {
		return getCurrentUser().getUsername();
	}
}
