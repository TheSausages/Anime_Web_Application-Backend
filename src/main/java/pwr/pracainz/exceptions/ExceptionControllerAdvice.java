package pwr.pracainz.exceptions;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import pwr.pracainz.DTO.SimpleMessageDTO;
import pwr.pracainz.exceptions.exceptions.*;
import pwr.pracainz.services.i18n.I18nServiceInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Class for handling custom and selected errors. Each custom error must have its own Exception Handler and Status.
 * The {@link CustomException#logMessage} for each should be logged here.
 */
@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

	private final I18nServiceInterface i18nService;

	@Autowired
	ExceptionControllerAdvice(I18nServiceInterface i18nService) {
		this.i18nService = i18nService;
	}

	/**
	 * Exception handler for {@link AuthenticationException}.
	 * @param ex The exception
	 * @return A {@link SimpleMessageDTO} with {@link CustomException#detailMessage}, that is returned to the user.
	 */
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	SimpleMessageDTO authenticationExceptionHandler(AuthenticationException ex) {
		log.error("An unauthorized access to secured elements occurred: " + ex.getMessage());

		return new SimpleMessageDTO(ex.getMessage());
	}

	/**
	 * Exception handler for {@link RegistrationException}.
	 * @param ex The exception
	 * @return A {@link SimpleMessageDTO} with {@link CustomException#detailMessage}, that is returned to the user.
	 */
	@ExceptionHandler(RegistrationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	SimpleMessageDTO registrationExceptionHandler(RegistrationException ex) {
		log.error("An error during registration: " + ex.getMessage());

		return new SimpleMessageDTO(ex.getMessage());
	}

	/**
	 * Exception handler for {@link AnilistException}.
	 * @param ex The exception
	 * @return A {@link SimpleMessageDTO} with {@link CustomException#detailMessage}, that is returned to the user.
	 */
	@ExceptionHandler(AnilistException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	SimpleMessageDTO anilistExceptionHandler(AnilistException ex) {
		log.error("{} on date: {}", ex.getLogMessage(), LocalDateTime.now().format(dateTimeFormatter));

		return new SimpleMessageDTO(ex.getMessage());
	}

	/**
	 * Exception handler for {@link ObjectNotFoundException}.
	 * @param ex The exception
	 * @return A {@link SimpleMessageDTO} with {@link CustomException#detailMessage}, that is returned to the user.
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	SimpleMessageDTO objectNotFoundExceptionHandler(ObjectNotFoundException ex) {
		log.error(ex.getLogMessage());

		return new SimpleMessageDTO(ex.getMessage());
	}

	/**
	 * Exception handler for {@link ObjectNotFoundException}.
	 * @param ex The exception
	 * @return A {@link SimpleMessageDTO} with {@link CustomException#detailMessage}, that is returned to the user.
	 */
	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	SimpleMessageDTO dataExceptionHandler(DataException ex) {
		log.error(ex.getMessage());

		return new SimpleMessageDTO(ex.getMessage());
	}

	/**
	 * Custom {@link HttpMessageNotReadableException} handler. If the root cause is {@link CustomDeserializationException},
	 * proces the error further with a {@link SimpleMessageDTO} containing a translated user message.
	 * If the type is different, process further with a translated default error message.
	 * @param ex The exception
	 * @param headers Headers of the request
	 * @param status Status of the request
	 * @param request The web request
	 * @return Further proces the exception in the default {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)} handler.
	 */
	@Override
	@NonNull
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (ex.getCause() != null) {
			if (ExceptionUtils.getRootCause(ex) instanceof CustomDeserializationException) {
				CustomDeserializationException innerException = (CustomDeserializationException) ExceptionUtils.getRootCause(ex);

				log.error("Error during deserialization: {}", innerException.getLogMessage());

				return handleExceptionInternal(
						ex, new SimpleMessageDTO(i18nService.getTranslation(innerException.getMessage())),
						headers, HttpStatus.BAD_REQUEST, request
				);
			}

			String innerCauseMessage = ex.getCause().getMessage();

			log.error("Error during deserialization: {}", Objects.nonNull(innerCauseMessage) ? innerCauseMessage : "Not given");

			return handleExceptionInternal(
					ex, new SimpleMessageDTO(i18nService.getTranslation("general.an-error-occurred")),
					headers, HttpStatus.BAD_REQUEST, request
			);
		}

		return handleExceptionInternal(ex, new SimpleMessageDTO(i18nService.getTranslation("general.an-error-occurred"))
				, headers, status, request);
	}

	/**
	 * Custom {@link MethodArgumentNotValidException} handler. Handle the exception further, with a translated default error message returned to the user.
	 * @param ex The exception
	 * @param headers Headers of the request
	 * @param status Status of the request
	 * @param request The web request
	 * @return Further proces the exception in the default {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)} handler.
	 */
	@Override
	@NonNull
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
		String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		log.error(errorMessage);

		return handleExceptionInternal(ex, new SimpleMessageDTO(i18nService.getTranslation("general.an-error-occurred")), headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}

	/**
	 * Custom {@link HttpMediaTypeNotSupportedException} handler. Handle the exception further, with a translated default error message returned to the user.
	 * @param ex The exception
	 * @param headers Headers of the request
	 * @param status Status of the request
	 * @param request The web request
	 * @return Further proces the exception in the default {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)} handler.
	 */
	@Override
	@NonNull
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(@NonNull HttpMediaTypeNotSupportedException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
		String message = Objects.nonNull(ex.getMessage()) ? ex.getMessage() : "No message given";
		log.error("This media type is not supported: {}", ex.getMessage());

		return handleExceptionInternal(ex, new SimpleMessageDTO(i18nService.getTranslation("general.an-error-occurred")), headers, status, request);
	}
}
