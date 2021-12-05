package pwr.pracainz.integrationtests.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import pwr.pracainz.services.i18n.I18nServiceInterface;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Used in tests. Has an additional method, that enables to get translations for a selected Locale.
 */
@Primary
@Service
@Profile("test")
public class TestI18nService implements I18nServiceInterface {
	public static final Locale POLISH_LOCALE = new Locale("pl", "pl");

	private final MessageSource source;

	@Autowired
	TestI18nService(MessageSource source) {
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
		return source.getMessage(path, parameters, request.getLocale());
	}

	/**
	 * Get the translation for a given Locale
	 */
	public String getTranslation(String path, Locale locale, Object... parameters) {
		return source.getMessage(path, parameters, locale);
	}
}
