package pwr.pracainz.entities.anime.query.parameters.media;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.enums.TitleLanguages;

import java.util.LinkedHashSet;
import java.util.Set;

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
        private final Set<ParameterString> languages = new LinkedHashSet<>();

        public MediaTitleBuilder romajiLanguage() {
            languages.add(new ParameterString(TitleLanguages.Romaji.getProperFieldString()));
            return this;
        }

        public MediaTitleBuilder romajiLanguageStylized() {
            languages.add(new ParameterString(TitleLanguages.Romaji.getProperFieldString() + "(stylised: true)"));
            return this;
        }

        public MediaTitleBuilder englishLanguage() {
            languages.add(new ParameterString(TitleLanguages.English.getProperFieldString()));
            return this;
        }

        public MediaTitleBuilder englishLanguageStylized() {
            languages.add(new ParameterString(TitleLanguages.English.getProperFieldString() + "(stylised: true)"));
            return this;
        }

        public MediaTitleBuilder nativeLanguage() {
            languages.add(new ParameterString(TitleLanguages.Native.getProperFieldString()));
            return this;
        }

        public MediaTitleBuilder nativeLanguageStylized() {
            languages.add(new ParameterString(TitleLanguages.Native.getProperFieldString() + "(stylised: true)"));
            return this;
        }

        public MediaTitle buildMediaTitle() {
            if (languages.isEmpty()) { throw new IllegalStateException("At least 1 language must be selected!"); }

            StringBuilder languageBuilder = new StringBuilder("title {\n");

            languages.forEach(lan -> languageBuilder.append(lan).append("\n"));

            languageBuilder.append("}");

            return new MediaTitle(languageBuilder.toString());
        }
    }
}
