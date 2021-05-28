package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.Parameters.TitleLanguages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class MediaTitle {
    private final String languages;

    private MediaTitle(String languages) {
        this.languages = languages;
    }

    public static MediaTitleBuilder getMediaTitleBuilder() {
        return new MediaTitleBuilder();
    }

    @Override
    public String toString() {
        return languages;
    }

    public String getLanguages() { return this.toString(); }

    public static final class MediaTitleBuilder {
        private final Map<ParameterString, Boolean> languages = new LinkedHashMap<>();

        public MediaTitleBuilder romajiLanguage(boolean stylized) {
            languages.put(new ParameterString(TitleLanguages.Romaji.getProperFieldString()), stylized);
            return this;
        }

        public MediaTitleBuilder englishLanguage(boolean stylized) {
            languages.put(new ParameterString(TitleLanguages.English.getProperFieldString()), stylized);
            return this;
        }

        public MediaTitleBuilder nativeLanguage(boolean stylized) {
            languages.put(new ParameterString(TitleLanguages.Native.getProperFieldString()), stylized);
            return this;
        }

        public MediaTitle buildMediaTitle() {
            if (languages.isEmpty()) { throw new IllegalStateException("At least 1 language must be selected!"); }

            StringBuilder languageBuilder = new StringBuilder("title {");

            languages.forEach((lan, stylized) -> {
                languageBuilder.append("\n").append(lan);

                if (stylized) {
                    languageBuilder.append("(stylized: true)");
                }
            });
            languageBuilder.append("\n").append("}");

            return new MediaTitle(languageBuilder.toString());
        }
    }
}
