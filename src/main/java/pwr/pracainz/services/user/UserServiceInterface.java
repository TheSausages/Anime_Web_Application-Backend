package pwr.pracainz.services.user;

import pwr.pracainz.entities.databaseerntities.user.User;

public interface UserServiceInterface {
	User getCurrentUser();

	User getCurrentUserOrInsert();

	User saveUser(User user);

	String getUsernameOfCurrentUser();
}
