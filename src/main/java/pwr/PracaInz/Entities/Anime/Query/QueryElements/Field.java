package pwr.PracaInz.Entities.Anime.Query.QueryElements;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateField;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaExternalLinks;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaRank;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaStreamingEpisodes;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaTitle;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class Field {
    private final String field;

    private Field(String field) {
        this.field = field;
    }

    public static FieldBuilder getFieldBuilder() {
        return new FieldBuilder();
    }

    @Override
    public String toString() {
        return field;
    }

    public static final class FieldBuilder {
        private final Set<ParameterString> fieldParameters = new LinkedHashSet<>();

        public FieldBuilder parameter(FieldParameters parameter) {
            fieldParameters.add(new ParameterString(parameter.name()));
            return this;
        }

        public FieldBuilder title(MediaTitle titles) {
            fieldParameters.add(new ParameterString(titles.toString()));
            return this;
        }

        public FieldBuilder trailer() { ;
            fieldParameters.add(new ParameterString("trailer {\nid\nsite\nthumbnail\n}"));
            return this;
        }

        public FieldBuilder tags() {
            fieldParameters.add(new ParameterString("tags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}"));
            return this;
        }

        public FieldBuilder nextAiringEpisode() {
            fieldParameters.add(new ParameterString("nextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}"));
            return this;
        }

        public FieldBuilder status() {
            fieldParameters.add(new ParameterString("status"));
            return this;
        }

        public FieldBuilder status(int version) {
            fieldParameters.add(new ParameterString("status(version: " + version + ")"));
            return this;
        }

        public FieldBuilder description(boolean asHtml) {
            fieldParameters.add(new ParameterString(asHtml ? "description(asHtml: true)" : "description"));
            return this;
        }

        public FieldBuilder source() {
            fieldParameters.add(new ParameterString("source"));
            return this;
        }

        public FieldBuilder source(int version) {
            fieldParameters.add(new ParameterString("source(version: " + version + ")"));
            return this;
        }

        public FieldBuilder externalLinks(MediaExternalLinks externalLink) {
            fieldParameters.add(new ParameterString(externalLink.getMediaExternalLinkString()));
            return this;
        }

        public FieldBuilder ranking(MediaRank rank) {
            fieldParameters.add(new ParameterString(rank.getMediaRankString()));
            return this;
        }

        public FieldBuilder fuzzyDate(FuzzyDateField fuzzyDate) {
            fieldParameters.add(new ParameterString(fuzzyDate.getFuzzyDateString()));
            return this;
        }

        public FieldBuilder streamingEpisodes(MediaStreamingEpisodes episode) {
            fieldParameters.add(new ParameterString(episode.getMediaStreamingEpisodeString()));
            return this;
        }

        public Field buildField() {
            if (fieldParameters.isEmpty()) { throw new IllegalStateException("Field must have at least 1 Parameter"); }

            StringBuilder builder = new StringBuilder("{\n");

            fieldParameters.forEach(param -> {
                builder.append(param).append("\n");
            });

            builder.append("}");

            return new Field(builder.toString());
        }

        //public FieldBuilder addCharacter
        //public FieldBuilder addStaff
        //public FieldBuilder addStudios
        //public FieldBuilder addAiringSchedule
        //public FieldBuilder addTrends
        //public FieldBuilder addReviews
        //public FieldBuilder addRecommendation
    }
}
