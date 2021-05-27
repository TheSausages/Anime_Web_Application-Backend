package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import pwr.PracaInz.Entities.Anime.Query.Parameters.TitleLanguages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MediaTitle {
    private final Map<String, Boolean> languages;

    private MediaTitle(Map<String, Boolean> languages) {
        this.languages = languages;
    }

    public static MediaTitleBuilder getMediaTitleBuilder() {
        return new MediaTitleBuilder();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("title {");

        languages.forEach((lan, stylized) -> {
            builder.append("\n").append(lan);

            if (stylized) {
                builder.append("(stylized: true)");
            }
        });
        builder.append("\n").append("}");

        return builder.toString();
    }

    public String getLanguages() { return this.toString(); }

    public static final class MediaTitleBuilder {
        private final Map<String, Boolean> titles = new LinkedHashMap<>();

        public MediaTitleBuilder romajiLanguage(boolean stylized) {
            titles.put(TitleLanguages.Romaji.getProperFieldString(), stylized);
            return this;
        }

        public MediaTitleBuilder englishLanguage(boolean stylized) {
            titles.put(TitleLanguages.English.getProperFieldString(), stylized);
            return this;
        }

        public MediaTitleBuilder nativeLanguage(boolean stylized) {
            titles.put(TitleLanguages.Native.getProperFieldString(), stylized);
            return this;
        }

        public MediaTitle buildMediaTitle() {
            if (titles.isEmpty()) { throw new IllegalStateException("At least 1 language must be selected!"); }

            return new MediaTitle(titles);
        }
    }
}
