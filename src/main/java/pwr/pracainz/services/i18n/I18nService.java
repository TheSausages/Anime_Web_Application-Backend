package pwr.pracainz.services.i18n;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Service
public class I18nService implements I18nServiceInterface {
	private final MessageSource source;

	@Autowired
	I18nService(MessageSource source) {
		this.source = source;
	}

	/**
	 * Get a translation using a selected path. This method uses the locale set from the {@link org.springframework.http.HttpHeaders#ACCEPT_LANGUAGE} header.
	 *
	 * @param path       Path to the translation
	 * @param parameters Parameters used in the translation, can be empty
	 * @return Translation with parameters inserted in order
	 */
	@Override
	public String getTranslation(String path, Object... parameters) {
		return source.getMessage(path, parameters, LocaleContextHolder.getLocale());
	}
	
	/**
	 * Variant of {@link #getTranslation(String, Object...)}.When the error is from a request to another site (ex. anilist) the headers would be from there, so in order to get
	 * the correct locale we need to insert our request here
	 *
	 * @param path       Path to the translation
	 * @param request    Selected request, should posses the language header
	 * @param parameters Parameters used in the translation, can be empty
	 * @return Translation with parameters inserted in order
	 */
	@Override
	public String getTranslation(String path, HttpServletRequest request, Object... parameters) {
		return source.getMessage(path, null, request.getLocale());
	}
}
