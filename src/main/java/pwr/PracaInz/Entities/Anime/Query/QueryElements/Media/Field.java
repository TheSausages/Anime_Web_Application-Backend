package pwr.PracaInz.Entities.Anime.Query.QueryElements.Media;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule.AiringScheduleArguments;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule.AiringScheduleConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.CharacterArguments;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.CharacterConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Recommendation.RecommendationArguments;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Recommendation.RecommendationConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Reviews.ReviewArguments;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Reviews.ReviewConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffArguments;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Studio.StudioConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Studio.StudioSort;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Trends.MediaTrendConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Trends.MediaTrendsArguments;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateField;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaExternalLinks;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaRank;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaStreamingEpisodes;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaTitle;

import java.util.Arrays;
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
            fieldParameters.add(new ParameterString(parameter.name() + "\n"));
            return this;
        }

        public FieldBuilder title(MediaTitle titles) {
            fieldParameters.add(new ParameterString(titles.toString() + "\n"));
            return this;
        }

        public FieldBuilder trailer() {
            fieldParameters.add(new ParameterString("trailer {\nid\nsite\nthumbnail\n}\n"));
            return this;
        }

        public FieldBuilder tags() {
            fieldParameters.add(new ParameterString("tags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}\n"));
            return this;
        }

        public FieldBuilder nextAiringEpisode() {
            fieldParameters.add(new ParameterString("nextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}\n"));
            return this;
        }

        public FieldBuilder status() {
            fieldParameters.add(new ParameterString("status\n"));
            return this;
        }

        public FieldBuilder status(int version) {
            fieldParameters.add(new ParameterString("status(version: " + version + ")\n"));
            return this;
        }

        public FieldBuilder description() {
            fieldParameters.add(new ParameterString("description\n"));
            return this;
        }

        public FieldBuilder descriptionAsHtml() {
            fieldParameters.add(new ParameterString("description(asHtml: true)\n"));
            return this;
        }

        public FieldBuilder source() {
            fieldParameters.add(new ParameterString("source\n"));
            return this;
        }

        public FieldBuilder source(int version) {
            fieldParameters.add(new ParameterString("source(version: " + version + ")\n"));
            return this;
        }

        public FieldBuilder externalLinks(MediaExternalLinks externalLink) {
            fieldParameters.add(new ParameterString(externalLink.getMediaExternalLinkString() + "\n"));
            return this;
        }

        public FieldBuilder ranking(MediaRank rank) {
            fieldParameters.add(new ParameterString(rank.getMediaRankString() + "\n"));
            return this;
        }

        public FieldBuilder fuzzyDate(FuzzyDateField fuzzyDate) {
            fieldParameters.add(new ParameterString(fuzzyDate.getFuzzyDateString() + "\n"));
            return this;
        }

        public FieldBuilder streamingEpisodes(MediaStreamingEpisodes episode) {
            fieldParameters.add(new ParameterString(episode.getMediaStreamingEpisodeString() + "\n"));
            return this;
        }

        public FieldBuilder relations(MediaConnection connection) {
            fieldParameters.add(new ParameterString("relations " + connection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder characters(CharacterConnection characterConnection) {
            fieldParameters.add(new ParameterString("characters " + characterConnection.getCharacterConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder characters(CharacterArguments characterArguments, CharacterConnection characterConnection) {
            fieldParameters.add(new ParameterString("characters" + characterArguments + " " + characterConnection.getCharacterConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder staff(StaffConnection staffConnection) {
            fieldParameters.add(new ParameterString("staff " + staffConnection.getStaffConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder staff(StaffArguments staffArguments, StaffConnection staffConnection) {
            fieldParameters.add(new ParameterString("staff" + staffArguments.getStaffArgumentsString() + " " + staffConnection.getStaffConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder studios(StudioConnection studioConnection) {
            fieldParameters.add(new ParameterString("studios " + studioConnection.getStudioConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder studios(StudioSort[] sorts, StudioConnection studioConnection) {
            fieldParameters.add(new ParameterString("studios(sort: " + Arrays.toString(sorts) + ") " + studioConnection.getStudioConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder airingSchedule(AiringScheduleConnection airingScheduleConnection) {
            fieldParameters.add(new ParameterString("airingSchedule " + airingScheduleConnection.getAiringScheduleConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder airingSchedule(AiringScheduleArguments airingScheduleArguments, AiringScheduleConnection airingScheduleConnection) {
            fieldParameters.add(new ParameterString("airingSchedule" + airingScheduleArguments.getAiringScheduleArgumentsString() +  " " + airingScheduleConnection.getAiringScheduleConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder trends(MediaTrendConnection connection) {
            fieldParameters.add(new ParameterString("trends " + connection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder trends(MediaTrendsArguments arguments, MediaTrendConnection connection) {
            fieldParameters.add(new ParameterString("trends" + arguments.getMediaTrendsArgumentsString() + " " + connection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder reviews(ReviewConnection reviewConnection) {
            fieldParameters.add(new ParameterString("reviews " + reviewConnection.getReviewConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder reviews(ReviewArguments reviewArguments, ReviewConnection reviewConnection) {
            fieldParameters.add(new ParameterString("reviews" + reviewArguments.getReviewArgumentsString() + " " + reviewConnection.getReviewConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder recommendation(RecommendationConnection recommendationConnection) {
            fieldParameters.add(new ParameterString("recommendation " + recommendationConnection.getRecommendationConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public FieldBuilder recommendation(RecommendationArguments recommendationArguments, RecommendationConnection recommendationConnection) {
            fieldParameters.add(new ParameterString("recommendation" + recommendationArguments.getRecommendationArgumentsString() + " " + recommendationConnection.getRecommendationConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public Field buildField() {
            if (fieldParameters.isEmpty()) { throw new IllegalStateException("Field must have at least 1 Parameter"); }

            StringBuilder builder = new StringBuilder("{\n");

            fieldParameters.forEach(builder::append);

            builder.append("}");

            return new Field(builder.toString());
        }
    }
}
