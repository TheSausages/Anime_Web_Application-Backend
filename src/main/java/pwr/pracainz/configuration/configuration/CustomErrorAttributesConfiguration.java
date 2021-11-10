package pwr.pracainz.configuration.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import pwr.pracainz.services.i18n.I18nServiceInterface;

import java.util.Map;

/**
 * Configure the returning information for all Exceptions not configured in {@link pwr.pracainz.exceptions.ExceptionControllerAdvice}.
 * The error should only return the message (if none is given use a default i18n one).
 */
@Log4j2
@Component
public class CustomErrorAttributesConfiguration extends DefaultErrorAttributes {
	private final I18nServiceInterface i18nService;

	CustomErrorAttributesConfiguration(I18nServiceInterface i18nService) {
		this.i18nService = i18nService;
	}

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		Map<String, Object> errorInformation = super.getErrorAttributes(webRequest, options);

		log.error(String.format("An error occurred with message: %s on path: %s (%s %s)",
				errorInformation.get("message"),
				errorInformation.get("path"),
				errorInformation.get("status"),
				errorInformation.get("error")
		));

		errorInformation.remove("timestamp");
		errorInformation.remove("error");
		errorInformation.remove("path");

		errorInformation.putIfAbsent("message", i18nService.getTranslation("general.no-error-message-available"));

		return errorInformation;
	}
}
