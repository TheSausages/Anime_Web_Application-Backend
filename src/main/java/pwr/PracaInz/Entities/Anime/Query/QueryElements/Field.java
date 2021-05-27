package pwr.PracaInz.Entities.Anime.Query.QueryElements;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldString;
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
        private final Set<FieldString> fieldParameters = new LinkedHashSet<>();

        public FieldBuilder parameter(FieldParameters parameter) {
            if (fieldParameters.contains(new FieldString(parameter.name()))) return this;

            fieldParameters.add(new FieldString(parameter.name()));
            return this;
        }

        public FieldBuilder title(MediaTitle titles) {
            if (fieldParameters.contains(new FieldString(titles.toString()))) return this;

            fieldParameters.add(new FieldString(titles.toString()));
            return this;
        }

        public FieldBuilder trailer() {
            if (fieldParameters.contains(new FieldString("trailer {\nid\nsite\nthumbnail\n}"))) return this;

            fieldParameters.add(new FieldString("trailer {\nid\nsite\nthumbnail\n}"));
            return this;
        }

        public FieldBuilder tags() {
            if (fieldParameters.contains(new FieldString("tags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}"))) return this;

            fieldParameters.add(new FieldString("tags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}"));
            return this;
        }

        public FieldBuilder nextAiringEpisode() {
            if (fieldParameters.contains(new FieldString("nextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}"))) return this;

            fieldParameters.add(new FieldString("nextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}"));
            return this;
        }

        public FieldBuilder status() {
            if (fieldParameters.contains(new FieldString("status"))) {
                return this;
            }

            fieldParameters.add(new FieldString("status"));
            return this;
        }

        public FieldBuilder status(int version) {
            if (fieldParameters.contains(new FieldString("status"))) return this;

            fieldParameters.add(new FieldString("status(version: " + version + ")"));
            return this;
        }

        public FieldBuilder description(boolean asHtml) {
            if (fieldParameters.contains(new FieldString("description"))) return this;

            fieldParameters.add(new FieldString(asHtml ? "description(asHtml: true)" : "description"));
            return this;
        }

        public FieldBuilder source() {
            if (fieldParameters.contains(new FieldString("source"))) return this;

            fieldParameters.add(new FieldString("source"));
            return this;
        }

        public FieldBuilder source(int version) {
            if (fieldParameters.contains(new FieldString("source"))) return this;

            fieldParameters.add(new FieldString("source(version: " + version + ")"));
            return this;
        }

        public FieldBuilder externalLinks(MediaExternalLinks externalLink) {
            if (fieldParameters.contains(new FieldString(externalLink.getMediaExternalLinkString()))) return this;

            fieldParameters.add(new FieldString(externalLink.getMediaExternalLinkString()));
            return this;
        }

        public FieldBuilder ranking(MediaRank rank) {
            if (fieldParameters.contains(new FieldString(rank.getMediaRankString()))) return this;

            fieldParameters.add(new FieldString(rank.getMediaRankString()));
            return this;
        }

        public FieldBuilder fuzzyDate(FuzzyDateField fuzzyDate) {
            if (fieldParameters.contains(new FieldString(fuzzyDate.getFuzzyDateString()))) return this;

            fieldParameters.add(new FieldString(fuzzyDate.getFuzzyDateString()));
            return this;
        }

        public FieldBuilder streamingEpisodes(MediaStreamingEpisodes episode) {
            if (fieldParameters.contains(new FieldString(episode.getMediaStreamingEpisodeString()))) return this;

            fieldParameters.add(new FieldString(episode.getMediaStreamingEpisodeString()));
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
