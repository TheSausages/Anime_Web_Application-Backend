package pwr.pracainz.services.i18n;

import javax.servlet.http.HttpServletRequest;

public interface I18nServiceInterface {
	String getTranslation(String path);

	String getTranslation(String path, HttpServletRequest request);
}
