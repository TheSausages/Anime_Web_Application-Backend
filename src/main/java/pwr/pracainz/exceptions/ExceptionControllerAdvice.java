package pwr.pracainz.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.exceptions.exceptions.AnilistException;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.exceptions.exceptions.RegistrationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseBodyWithMessageDTO authenticationExceptionHandler(AuthenticationException ex) {
        log.error("An unauthorized access to secured elements occurred: " + ex.getMessage());

        return new ResponseBodyWithMessageDTO(ex.getMessage());
    }

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseBodyWithMessageDTO registrationExceptionHandler(RegistrationException ex) {
        log.error("An error during registration: " + ex.getMessage());

        return new ResponseBodyWithMessageDTO(ex.getMessage());
    }

    @ExceptionHandler(AnilistException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    ResponseBodyWithMessageDTO anilistExceptionHandler(AnilistException ex) {
        log.error("The Anilist served did not respond on date: " + LocalDateTime.now().format(dateTimeFormatter));

        return new ResponseBodyWithMessageDTO(ex.getMessage());
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getCause() != null) {
            String innerCauseMessage = ex.getCause().getMessage();

            log.error("Error during deserialization: {}", innerCauseMessage);

            return handleExceptionInternal(
                    ex, new ResponseBodyWithMessageDTO(innerCauseMessage),
                    headers, HttpStatus.BAD_REQUEST, request
            );
        }

        return handleExceptionInternal(ex, new ResponseBodyWithMessageDTO("Error during deserialization!")
                , headers, status, request);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.error(errorMessage);

        return handleExceptionInternal(ex, new ResponseBodyWithMessageDTO(errorMessage), headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(@NonNull HttpMediaTypeNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.error("This media type is not Supported");

        return handleExceptionInternal(ex, new ResponseBodyWithMessageDTO("This media type is not Supported"), headers, status, request);
    }
}
