package pwr.pracainz.services.Keycloak;

import org.springframework.http.ResponseEntity;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.LogoutRequestBodyDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;

public interface KeycloakServiceInterface {
    ResponseEntity<AuthenticationTokenDTO> login(LoginCredentialsDTO credentials);

    ResponseEntity<ResponseBodyWithMessageDTO> logout(LogoutRequestBodyDTO logoutRequestBody, String accessToken);

    ResponseEntity<ResponseBodyWithMessageDTO> register(RegistrationBodyDTO registrationBody);
}
