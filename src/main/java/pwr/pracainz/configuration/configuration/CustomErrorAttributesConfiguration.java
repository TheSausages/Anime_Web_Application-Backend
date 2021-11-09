package pwr.pracainz.configuration.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Log4j2
@Component
public class CustomErrorAttributesConfiguration extends DefaultErrorAttributes {
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

		errorInformation.putIfAbsent("message", "No error message available! Please contact the administration");

		return errorInformation;
	}
}
