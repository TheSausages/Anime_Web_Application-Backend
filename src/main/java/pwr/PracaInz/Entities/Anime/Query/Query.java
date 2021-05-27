package pwr.PracaInz.Entities.Anime.Query;

import pwr.PracaInz.Entities.Anime.Query.Parameters.QueryParameters;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Field;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateValue;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.*;

import java.util.*;

public class Query {
    private String query;

    private final Map<QueryParameters, String> queryParameters = new HashMap<>();
    private String media;
    private String page;

    public MediaBuilder mediaBuilder() {
        return new MediaBuilder(this);
    }

    public class MediaBuilder {
        public MediaBuilder(Query query) {
            this.query = query;
        }

        private final Query query;
        private final Map<QueryParameters, String> mediaParameters = new HashMap<>();
        private String field;

        public MediaBuilder addId(int id) {
            mediaParameters.put(QueryParameters.id, Integer.toString(id));
            return this;
        }

        public MediaBuilder addIntParameter(QueryParameters parameter, int value) {
            mediaParameters.put(parameter, Integer.toString(value));
            return this;
        }

        public MediaBuilder addFuzzyDateParameter(QueryParameters parameter, FuzzyDateValue date) {
            mediaParameters.put(parameter, Integer.toString(date.getFuzzyDateNumber()));
            return this;
        }

        public MediaBuilder addMediaSeasonParameter(QueryParameters parameter, MediaSeason mediaSeason) {
            mediaParameters.put(parameter, mediaSeason.name());
            return this;
        }

        public MediaBuilder addMediaTypeParameter(QueryParameters parameter, MediaType mediaType) {
            mediaParameters.put(parameter, mediaType.name());
            return this;
        }

        public MediaBuilder addMediaFormatParameter(QueryParameters parameter, MediaFormat mediaFormat) {
            mediaParameters.put(parameter, mediaFormat.name());
            return this;
        }

        public MediaBuilder addMediaStatusParameter(QueryParameters parameter, MediaStatus mediaStatus) {
            mediaParameters.put(parameter, mediaStatus.name());
            return this;
        }

        public MediaBuilder addBooleanParameter(QueryParameters parameter, Boolean value) {
            mediaParameters.put(parameter, Boolean.toString(value));
            return this;
        }

        public MediaBuilder addStringParameter(QueryParameters parameter, String value) {
            mediaParameters.put(parameter, value);
            return this;
        }

        public MediaBuilder addMediaSourceParameter(QueryParameters parameter, MediaSource source) {
            mediaParameters.put(parameter, source.name());
            return this;
        }

        public MediaBuilder addIntArrayParameter(QueryParameters parameter, int[] numbers) {
            mediaParameters.put(parameter, Arrays.toString(numbers));
            return this;
        }

        public MediaBuilder addMediaFormatArrayParameter(QueryParameters parameter, MediaFormat[] formats) {
            mediaParameters.put(parameter, Arrays.toString(formats));
            return this;
        }

        public MediaBuilder addMediaStatusArrayParameter(QueryParameters parameter, MediaStatus[] statuses) {
            mediaParameters.put(parameter, Arrays.toString(statuses));
            return this;
        }

        public MediaBuilder addStringArrayParameter(QueryParameters parameter, String[] values) {
            mediaParameters.put(parameter, Arrays.toString(values));
            return this;
        }

        public MediaBuilder addMediaSourceArrayParameter(QueryParameters parameter, MediaSource[] sources) {
            mediaParameters.put(parameter, Arrays.toString(sources));
            return this;
        }

        public MediaBuilder addMediaSortArrayParameter(QueryParameters parameter, MediaSort[] sorts) {
            mediaParameters.put(parameter, Arrays.toString(sorts));
            return this;
        }

        public MediaBuilder addTypeParameter(QueryParameters parameter, MediaType mediaType) {
            mediaParameters.put(parameter, mediaType.name());
            return this;
        }

        public MediaBuilder addField(Field field) {
            this.field = field.toString();
            return this;
        }

        public Query buildMediaString() {
            StringBuilder mediaString = new StringBuilder("Media (");

            mediaParameters.forEach((parameter, value) -> {
                mediaString.append(parameter).append(": $").append(parameter).append(", ");
            });
            mediaString.deleteCharAt(mediaString.length() - 1);
            mediaString.append(") {");

            mediaString.append("}");
            query.media = mediaString.toString();
            query.queryParameters.putAll(mediaParameters);

            return query;
        }
    }
}
