package pwr.pracainz.services.i18n;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for an I18n Service. Each implementation must use this interface.
 */
public interface I18nServiceInterface {
	/**
	 * Get a translation using a selected path. This method uses the locale set from the {@link org.springframework.http.HttpHeaders#ACCEPT_LANGUAGE} header.
	 * @param path       Path to the translation
	 * @param parameters Parameters used in the translation, can be empty
	 * @return Translation with parameters inserted in order
	 */
	String getTranslation(String path, Object... parameters);

	/**
	 * Variant of {@link #getTranslation(String, Object...)}.When the error is from a request to another site (ex. anilist) the headers would be from there, so in order to get
	 * the correct locale we need to insert our request here
	 * @param path       Path to the translation
	 * @param request    Selected request, should posses the language header
	 * @param parameters Parameters used in the translation, can be empty
	 * @return Translation with parameters inserted in order
	 */
	String getTranslation(String path, HttpServletRequest request, Object... parameters);
}
