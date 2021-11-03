package pwr.pracainz.services.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.repositories.user.UserRepository;
import pwr.pracainz.services.i18n.I18nServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;

@Log4j2
@Service
public class UserService implements UserServiceInterface {
	private final UserRepository userRepository;
	private final I18nServiceInterface i18nService;

	@Autowired
	UserService(UserRepository userRepository, I18nServiceInterface i18nService) {
		this.userRepository = userRepository;
		this.i18nService = i18nService;
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
