package pwr.pracainz.services.user;

import pwr.pracainz.DTO.user.CompleteUserDTO;
import pwr.pracainz.entities.databaseerntities.user.User;

public interface UserServiceInterface {
	CompleteUserDTO getCurrentUserInformation();

	CompleteUserDTO getUserInformationById(String userId, boolean isCurrentUser);

	User getCurrentUser();

	User getCurrentUserOrInsert();

	User saveUser(User user);

	String getUsernameOfCurrentUser();
}
