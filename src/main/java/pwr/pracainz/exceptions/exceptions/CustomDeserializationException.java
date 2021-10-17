package pwr.pracainz.exceptions.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomDeserializationException extends JsonProcessingException {
	public CustomDeserializationException(String msg) {
		super(msg);
	}
}
