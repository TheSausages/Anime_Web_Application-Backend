package pwr.pracainz.services.keycloak;

import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;

public interface KeycloakServiceInterface {
    AuthenticationTokenDTO login(LoginCredentialsDTO credentials);

    ResponseBodyWithMessageDTO logout(RefreshTokenDTO logoutRequestBody, String accessToken);

    ResponseBodyWithMessageDTO register(RegistrationBodyDTO registrationBody);

    AuthenticationTokenDTO refreshTokens(RefreshTokenDTO refreshTokenDTO);
}
