package pwr.pracainz.entities.anime.query.parameters.enums;

import java.util.Locale;

public enum TitleLanguages {
	Romaji,
	English,
	Native;

	public String getProperFieldString() {
		return this.name().toLowerCase(Locale.ROOT);
	}
}
