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
import pwr.pracainz.DTO.ResponseBodyWithMessageDTO;
import pwr.pracainz.exceptions.exceptions.*;
import pwr.pracainz.services.i18n.I18nServiceInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

	private final I18nServiceInterface i18nService;

	@Autowired
	ExceptionControllerAdvice(I18nServiceInterface i18nService) {
		this.i18nService = i18nService;
	}

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

	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseBodyWithMessageDTO objectNotFoundExceptionHandler(ObjectNotFoundException ex) {
		log.error(ex.getLogMessage());

		return new ResponseBodyWithMessageDTO(ex.getMessage());
	}

	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	ResponseBodyWithMessageDTO dataExceptionHandler(IllegalArgumentException ex) {
		log.error(ex.getMessage());

		return new ResponseBodyWithMessageDTO(ex.getMessage());
	}

	@Override
	@NonNull
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (ex.getCause() != null) {
			if (ExceptionUtils.getRootCause(ex) instanceof CustomDeserializationException) {
				CustomDeserializationException innerException = (CustomDeserializationException) ExceptionUtils.getRootCause(ex);

				log.error("Error during deserialization: {}", innerException.getLogMessage());

				return handleExceptionInternal(
						ex, new ResponseBodyWithMessageDTO(i18nService.getTranslation(innerException.getMessage())),
						headers, HttpStatus.BAD_REQUEST, request
				);
			}

			String innerCauseMessage = ex.getCause().getMessage();

			log.error("Error during deserialization: {}", innerCauseMessage);

			return handleExceptionInternal(
					ex, new ResponseBodyWithMessageDTO(innerCauseMessage),
					headers, HttpStatus.BAD_REQUEST, request
			);
		}

		return handleExceptionInternal(ex, new ResponseBodyWithMessageDTO(i18nService.getTranslation("general.an-error-occurred"))
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

		return handleExceptionInternal(ex, new ResponseBodyWithMessageDTO(i18nService.getTranslation("general.an-error-occurred")), headers, status, request);
	}
}
