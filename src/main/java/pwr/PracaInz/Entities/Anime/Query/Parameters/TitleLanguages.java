package pwr.PracaInz.Entities.Anime.Query.Parameters;

import java.util.Locale;

public enum TitleLanguages {
    Romaji,
    English,
    Native;

    public String getProperFieldString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
