package pwr.pracainz.services.user;

import pwr.pracainz.DTO.user.CompleteUserDTO;
import pwr.pracainz.entities.databaseerntities.user.User;

/**
 * Interface for a User Service. Each implementation must use this interface.
 */
public interface UserServiceInterface {
	/**
	 * Get complete information about the currently authenticated User. Can only be used when a user is authenticated.
	 * @return Complete authenticated user information
	 */
	CompleteUserDTO getCurrentUserInformation();

	/**
	 * Get complete information about a given User.
	 * @param userId Id of the searched user
	 * @return Complete user information
	 */
	CompleteUserDTO getUserInformationById(String userId);

	/**
	 * Get the currently authenticated user.
	 * @return Current user
	 */
	User getCurrentUser();

	/**
	 * Save a new user to the non-keycloak database.
	 * @param user the user to be saved
	 * @return The saved user.
	 */
	User saveUser(User user);

	/**
	 * Get the username of the currently authenticated User. Usually used for logging.
	 * @return The username of the user
	 */
	String getUsernameOfCurrentUser();
}
