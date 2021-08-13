package pwr.pracainz.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.exceptions.exceptions.AnilistException;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @ExceptionHandler(AuthenticationException.class)
    ResponseBodyWithMessageDTO authenticationExceptionHandler(AuthenticationException ex) {
        log.error("An unauthorized access to secured elements occurred: " + ex.getMessage());

        return new ResponseBodyWithMessageDTO(ex.getMessage());
    }

    @ExceptionHandler(AnilistException.class)
    ResponseBodyWithMessageDTO anilistExceptionHandler(AnilistException ex) {
        log.error("The Anilist served did not respond on date: " + LocalDateTime.now().format(dateTimeFormatter));

        return new ResponseBodyWithMessageDTO(ex.getMessage());
    }
}
