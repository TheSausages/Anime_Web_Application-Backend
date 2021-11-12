package pwr.pracainz.exceptions.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This error occurs during custom deserialization. Example is {@link pwr.pracainz.configuration.customMappers.threadStatus.ThreadStatusDeserializer}.
 * The {@link CustomDeserializationException#detailMessage} is used as an I18n code to get the translated user message.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class CustomDeserializationException extends JsonProcessingException {
	private String logMessage = "Custom Deserialization Exception, no message given";

	public CustomDeserializationException(String i18nCode) {
		super(i18nCode);
	}

	public CustomDeserializationException(String i18nCode, String logMessage) {
		super(i18nCode);
		this.logMessage = logMessage;
	}
}
