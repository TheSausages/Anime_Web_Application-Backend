package pwr.pracainz.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.repositories.user.UserRepository;
import pwr.pracainz.services.DTOConvension.DTOConversion;
import pwr.pracainz.services.DTOConvension.DTOConversionInterface;

import static pwr.pracainz.utils.UserAuthorizationUtilities.getPrincipalOfCurrentUser;

@Service
public class UserService implements UserServiceInterface {
    private final DTOConversionInterface dtoConversion;
    private final UserRepository userRepository;

    @Autowired
    UserService(DTOConversion dtoConversion, UserRepository userRepository) {
        this.dtoConversion = dtoConversion;
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findById(getPrincipalOfCurrentUser().toString())
                .orElseThrow(() -> new AuthenticationException("No User logged in!"));
    }
}
