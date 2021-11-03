package pwr.pracainz.exceptions.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


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
