package pwr.pracainz.services.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.repositories.user.UserRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;

import static pwr.pracainz.utils.UserAuthorizationUtilities.*;

@Log4j2
@Service
public class UserService implements UserServiceInterface {
    private final DTOConversionInterface<?> dtoConversion;
    private final UserRepository userRepository;

    @Autowired
    UserService(DTOConversionInterface<?> dtoConversion, UserRepository userRepository) {
        this.dtoConversion = dtoConversion;
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findById(getPrincipalOfCurrentUser().toString())
                .orElseThrow(() -> new AuthenticationException("No User logged in!"));
    }

    @Override
    public User getCurrentUserOrInsert() {
        if (!checkIfLoggedUser()) {
            throw new AuthenticationException("You are not logged in!");
        }

        String currUserId = getIdOfCurrentUser();

        return userRepository.findById(currUserId)
                .orElseGet(() -> userRepository.save(new User(currUserId)));
    }
}
