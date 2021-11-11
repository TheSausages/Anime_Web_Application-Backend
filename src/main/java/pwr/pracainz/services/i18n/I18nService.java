package pwr.pracainz.services.i18n;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Default implementation for the {@link I18nServiceInterface} interface.
 */
@Log4j2
@Service
public class I18nService implements I18nServiceInterface {
	private final MessageSource source;

	@Autowired
	I18nService(MessageSource source) {
		this.source = source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTranslation(String path, Object... parameters) {
		return source.getMessage(path, parameters, LocaleContextHolder.getLocale());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTranslation(String path, HttpServletRequest request, Object... parameters) {
		return source.getMessage(path, null, request.getLocale());
	}
}
