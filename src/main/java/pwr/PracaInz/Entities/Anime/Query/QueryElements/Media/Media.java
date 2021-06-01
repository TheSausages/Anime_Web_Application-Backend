package pwr.PracaInz.Entities.Anime.Query.QueryElements.Media;

import lombok.Getter;
import net.minidev.json.JSONObject;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Enums.Genre;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Enums.Tags;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateValue;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.*;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.QueryElements;

import java.util.*;

@Getter
public class Media implements QueryElements {
    private final String elementString;
    private final Set<ParameterString> queryParameters;
    private final JSONObject variables;

    private Media(String elementString, Set<ParameterString> queryParameters, JSONObject variables) {
        this.elementString = elementString;
        this.queryParameters = queryParameters;
        this.variables = variables;
    }

    public String getMediaStringWithoutFieldName() {
        return elementString.substring(elementString.indexOf(')') + 2);
    }

    public static MediaBuilder getMediaBuilder(Field field) {
        return new MediaBuilder(field);
    }

    public static class MediaBuilder {
        private final Field field;
        private final Set<ParameterString> mediaParameters = new LinkedHashSet<>();
        private final Set<ParameterString> queryParameters = new LinkedHashSet<>();
        private final Map<String, Object> parametersValue = new LinkedHashMap<>();

        public MediaBuilder(Field field) {
            this.field = field;
        }

        public MediaBuilder id(int id) {
            mediaParameters.add(new ParameterString("id: $id, "));
            queryParameters.add(new ParameterString("$id: Int, "));
            parametersValue.putIfAbsent("id", id);
            return this;
        }

        public MediaBuilder idMal(int idMal) {
            mediaParameters.add(new ParameterString("idMal: $idMal, "));
            queryParameters.add(new ParameterString("$idMal: Int, "));
            parametersValue.putIfAbsent("idMal", idMal);
            return this;
        }

        public MediaBuilder startDate(FuzzyDateValue fuzzyDateValue) {
            mediaParameters.add(new ParameterString("startDate: $startDate, "));
            queryParameters.add(new ParameterString("$startDate: FuzzyDateInt, "));
            parametersValue.putIfAbsent("startDate", fuzzyDateValue.getFuzzyDateNumber());
            return this;
        }

        public MediaBuilder endDate(FuzzyDateValue fuzzyDateValue) {
            mediaParameters.add(new ParameterString("endDate: $endDate, "));
            queryParameters.add(new ParameterString("$endDate: FuzzyDateInt, "));
            parametersValue.putIfAbsent("endDate", fuzzyDateValue.getFuzzyDateNumber());
            return this;
        }

        public MediaBuilder season(MediaSeason season) {
            mediaParameters.add(new ParameterString("season: $season, "));
            queryParameters.add(new ParameterString("$season: MediaSeason, "));
            parametersValue.putIfAbsent("season", season.name());
            return this;
        }

        public MediaBuilder seasonYear(int seasonYear) {
            mediaParameters.add(new ParameterString("seasonYear: $seasonYear, "));
            queryParameters.add(new ParameterString("$seasonYear: Int, "));
            parametersValue.putIfAbsent("seasonYear", seasonYear);
            return this;
        }

        public MediaBuilder type(MediaType type) {
            mediaParameters.add(new ParameterString("type: $type, "));
            queryParameters.add(new ParameterString("$type: MediaType, "));
            parametersValue.putIfAbsent("type", type);
            return this;
        }

        public MediaBuilder format(MediaFormat format) {
            mediaParameters.add(new ParameterString("format: $format, "));
            queryParameters.add(new ParameterString("$format: MediaFormat, "));
            parametersValue.putIfAbsent("format", format);
            return this;
        }

        public MediaBuilder status(MediaStatus status) {
            mediaParameters.add(new ParameterString("status: $status, "));
            queryParameters.add(new ParameterString("$status: MediaStatus, "));
            parametersValue.putIfAbsent("status", status);
            return this;
        }

        public MediaBuilder episodes(int episodes) {
            mediaParameters.add(new ParameterString("episodes: $episodes, "));
            queryParameters.add(new ParameterString("$episodes: Int, "));
            parametersValue.putIfAbsent("episodes", episodes);
            return this;
        }

        public MediaBuilder duration(int duration) {
            mediaParameters.add(new ParameterString("duration: $duration, "));
            queryParameters.add(new ParameterString("$duration: Int, "));
            parametersValue.putIfAbsent("duration", duration);
            return this;
        }

        public MediaBuilder chapters(int chapters) {
            mediaParameters.add(new ParameterString("chapters: $chapters, "));
            queryParameters.add(new ParameterString("$chapters: Int, "));
            parametersValue.putIfAbsent("chapters", chapters);
            return this;
        }

        public MediaBuilder volumes(int volumes) {
            mediaParameters.add(new ParameterString("volumes: $volumes, "));
            queryParameters.add(new ParameterString("$volumes: Int, "));
            parametersValue.putIfAbsent("volumes", volumes);
            return this;
        }

        public MediaBuilder isAdult(boolean isAdult) {
            mediaParameters.add(new ParameterString("isAdult: $isAdult, "));
            queryParameters.add(new ParameterString("$isAdult: Boolean, "));
            parametersValue.putIfAbsent("isAdult", isAdult);
            return this;
        }

        public MediaBuilder genre(Genre genre) {
            mediaParameters.add(new ParameterString("genre: $genre, "));
            queryParameters.add(new ParameterString("$genre: String, "));
            parametersValue.putIfAbsent("genre", genre);
            return this;
        }

        public MediaBuilder tag(Tags tag) {
            mediaParameters.add(new ParameterString("tag: $tag, "));
            queryParameters.add(new ParameterString("$tag: String, "));
            parametersValue.putIfAbsent("tag", tag);
            return this;
        }

        public MediaBuilder onList(boolean onList) {
            mediaParameters.add(new ParameterString("onList: $onList, "));
            queryParameters.add(new ParameterString("$onList: Boolean, "));
            parametersValue.putIfAbsent("onList", onList);
            return this;
        }

        public MediaBuilder licensedBy(String licensedBy) {
            mediaParameters.add(new ParameterString("licensedBy: $licensedBy, "));
            queryParameters.add(new ParameterString("$licensedBy: String, "));
            parametersValue.putIfAbsent("licensedBy", licensedBy);
            return this;
        }

        public MediaBuilder averageScore(int averageScore) {
            mediaParameters.add(new ParameterString("averageScore: $averageScore, "));
            queryParameters.add(new ParameterString("$averageScore: Int, "));
            parametersValue.putIfAbsent("averageScore", averageScore);
            return this;
        }

        public MediaBuilder source(MediaSource source) {
            mediaParameters.add(new ParameterString("source: $source, "));
            queryParameters.add(new ParameterString("$source: MediaSource, "));
            parametersValue.putIfAbsent("source", source);
            return this;
        }

        //https://pl.wikipedia.org/wiki/ISO_3166-1_alfa-2
        public MediaBuilder countryOfOrigin(String countryCode) {
            mediaParameters.add(new ParameterString("countryOfOrigin: $countryOfOrigin, "));
            queryParameters.add(new ParameterString("$countryOfOrigin: CountryCode, "));
            parametersValue.putIfAbsent("countryOfOrigin", countryCode);
            return this;
        }

        public MediaBuilder popularity(int popularity) {
            mediaParameters.add(new ParameterString("popularity: $popularity, "));
            queryParameters.add(new ParameterString("$popularity: Int, "));
            parametersValue.putIfAbsent("popularity", popularity);
            return this;
        }

        public MediaBuilder search(String search) {
            mediaParameters.add(new ParameterString("search: $search, "));
            queryParameters.add(new ParameterString("$search: String, "));
            parametersValue.putIfAbsent("search", search);
            return this;
        }

        public MediaBuilder idNot(int idNot) {
            mediaParameters.add(new ParameterString("id_not: $id_not, "));
            queryParameters.add(new ParameterString("$id_not: Int, "));
            parametersValue.putIfAbsent("id_not", idNot);
            return this;
        }

        public MediaBuilder idIn(int[] ids) {
            mediaParameters.add(new ParameterString("id_in: $id_in, "));
            queryParameters.add(new ParameterString("$id_in: [Int], "));
            parametersValue.putIfAbsent("id_in", ids);
            return this;
        }

        public MediaBuilder idNotIn(int[] ids) {
            mediaParameters.add(new ParameterString("id_not_in: $id_not_in, "));
            queryParameters.add(new ParameterString("$id_not_in: [Int], "));
            parametersValue.putIfAbsent("id_not_in", ids);
            return this;
        }

        public MediaBuilder idMalNot(int idNot) {
            mediaParameters.add(new ParameterString("idMal_not: $idMal_not, "));
            queryParameters.add(new ParameterString("$idMal_not: Int, "));
            parametersValue.putIfAbsent("idMal_not", idNot);
            return this;
        }

        public MediaBuilder idMalIn(int[] ids) {
            mediaParameters.add(new ParameterString("idMal_in: $idMal_in, "));
            queryParameters.add(new ParameterString("$idMal_in: [Int], "));
            parametersValue.putIfAbsent("idMal_in", ids);
            return this;
        }

        public MediaBuilder idMalNotIn(int[] ids) {
            mediaParameters.add(new ParameterString("idMal_not_in: $idMal_not_in, "));
            queryParameters.add(new ParameterString("$idMal_not_in: [Int], "));
            parametersValue.putIfAbsent("idMal_not_in", ids);
            return this;
        }

        public MediaBuilder startDateGreater(FuzzyDateValue fuzzyDateValue) {
            mediaParameters.add(new ParameterString("startDate_greater: $startDate_greater, "));
            queryParameters.add(new ParameterString("$startDate_greater: FuzzyDateInt, "));
            parametersValue.putIfAbsent("startDate_greater", fuzzyDateValue.getFuzzyDateNumber());
            return this;
        }

        public MediaBuilder startDateLesser(FuzzyDateValue fuzzyDateValue) {
            mediaParameters.add(new ParameterString("startDate_lesser: $startDate_lesser, "));
            queryParameters.add(new ParameterString("$startDate_lesser: FuzzyDateInt, "));
            parametersValue.putIfAbsent("startDate_lesser", fuzzyDateValue.getFuzzyDateNumber());
            return this;
        }

        public MediaBuilder endDateGreater(FuzzyDateValue fuzzyDateValue) {
            mediaParameters.add(new ParameterString("endDate_greater: $endDate_greater, "));
            queryParameters.add(new ParameterString("$endDate_greater: FuzzyDateInt, "));
            parametersValue.putIfAbsent("endDate_greater", fuzzyDateValue.getFuzzyDateNumber());
            return this;
        }

        public MediaBuilder endDateLesser(FuzzyDateValue fuzzyDateValue) {
            mediaParameters.add(new ParameterString("endDate_lesser: $endDate_lesser, "));
            queryParameters.add(new ParameterString("$endDate_lesser: FuzzyDateInt, "));
            parametersValue.putIfAbsent("endDate_lesser", fuzzyDateValue.getFuzzyDateNumber());
            return this;
        }

        public MediaBuilder formatNot(MediaFormat format) {
            mediaParameters.add(new ParameterString("format_not: $format_not, "));
            queryParameters.add(new ParameterString("$format_not: MediaFormat, "));
            parametersValue.putIfAbsent("format_not", format);
            return this;
        }

        public MediaBuilder formatIn(MediaFormat[] formats) {
            mediaParameters.add(new ParameterString("format_in: $format_in, "));
            queryParameters.add(new ParameterString("$format_in: [MediaFormat], "));
            parametersValue.putIfAbsent("format_in", formats);
            return this;
        }

        public MediaBuilder formatNotIn(MediaFormat[] formats) {
            mediaParameters.add(new ParameterString("format_not_in: $format_not_in, "));
            queryParameters.add(new ParameterString("$format_not_in: [MediaFormat], "));
            parametersValue.putIfAbsent("format_not_in", formats);
            return this;
        }

        public MediaBuilder statusNot(MediaStatus status) {
            mediaParameters.add(new ParameterString("status_not: $status_not, "));
            queryParameters.add(new ParameterString("$status_not: MediaStatus, "));
            parametersValue.putIfAbsent("status_not", status);
            return this;
        }

        public MediaBuilder statusIn(MediaStatus[] statuses) {
            mediaParameters.add(new ParameterString("status_in: $status_in, "));
            queryParameters.add(new ParameterString("$status_in: [MediaStatus], "));
            parametersValue.putIfAbsent("status_in", statuses);
            return this;
        }

        public MediaBuilder statusNotIn(MediaStatus[] statuses) {
            mediaParameters.add(new ParameterString("status_not_in: $status_not_in, "));
            queryParameters.add(new ParameterString("$status_not_in: [MediaStatus], "));
            parametersValue.putIfAbsent("status_not_in", statuses);
            return this;
        }

        public MediaBuilder episodesGreater(int episodes) {
            mediaParameters.add(new ParameterString("episodes_greater: $episodes_greater, "));
            queryParameters.add(new ParameterString("$episodes_greater: Int, "));
            parametersValue.putIfAbsent("episodes_greater", episodes);
            return this;
        }

        public MediaBuilder episodesLesser(int episodes) {
            mediaParameters.add(new ParameterString("episodes_lesser: $episodes_lesser, "));
            queryParameters.add(new ParameterString("$episodes_lesser: Int, "));
            parametersValue.putIfAbsent("episodes_lesser", episodes);
            return this;
        }

        public MediaBuilder durationGreater(int duration) {
            mediaParameters.add(new ParameterString("duration_greater: $duration_greater, "));
            queryParameters.add(new ParameterString("$duration_greater: Int, "));
            parametersValue.putIfAbsent("duration_greater", duration);
            return this;
        }

        public MediaBuilder durationLesser(int duration) {
            mediaParameters.add(new ParameterString("duration_lesser: $duration_lesser, "));
            queryParameters.add(new ParameterString("$duration_lesser: Int, "));
            parametersValue.putIfAbsent("duration_lesser", duration);
            return this;
        }

        public MediaBuilder chaptersGreater(int chapters) {
            mediaParameters.add(new ParameterString("chapters_greater: $chapters_greater, "));
            queryParameters.add(new ParameterString("$chapters_greater: Int, "));
            parametersValue.putIfAbsent("chapters_greater", chapters);
            return this;
        }

        public MediaBuilder chaptersLesser(int chapters) {
            mediaParameters.add(new ParameterString("chapters_lesser: $chapters_lesser, "));
            queryParameters.add(new ParameterString("$chapters_lesser: Int, "));
            parametersValue.putIfAbsent("chapters_lesser", chapters);
            return this;
        }

        public MediaBuilder volumesGreater(int volumes) {
            mediaParameters.add(new ParameterString("volumes_greater: $volumes_greater, "));
            queryParameters.add(new ParameterString("$volumes_greater: Int, "));
            parametersValue.putIfAbsent("volumes_greater", volumes);
            return this;
        }

        public MediaBuilder volumesLesser(int volumes) {
            mediaParameters.add(new ParameterString("volumes_lesser: $volumes_lesser, "));
            queryParameters.add(new ParameterString("$volumes_lesser: Int, "));
            parametersValue.putIfAbsent("volumes_lesser", volumes);
            return this;
        }

        public MediaBuilder genreIn(Genre[] genres) {
            mediaParameters.add(new ParameterString("genre_in: $genre_in, "));
            queryParameters.add(new ParameterString("$genre_in: [String], "));
            parametersValue.putIfAbsent("genre_in", genres);
            return this;
        }

        public MediaBuilder genreNotIn(Genre[] genres) {
            mediaParameters.add(new ParameterString("genre_not_in: $genre_not_in, "));
            queryParameters.add(new ParameterString("$genre_not_in: [String], "));
            parametersValue.putIfAbsent("genre_not_in", genres);
            return this;
        }

        public MediaBuilder tagIn(Tags[] tags) {
            mediaParameters.add(new ParameterString("tag_in: $tag_in, "));
            queryParameters.add(new ParameterString("$tag_in: [String], "));
            parametersValue.putIfAbsent("tag_in", tags);
            return this;
        }

        public MediaBuilder tagNotIn(Tags[] tags) {
            mediaParameters.add(new ParameterString("tag_not_in: $tag_not_in, "));
            queryParameters.add(new ParameterString("$tag_not_in: [String], "));
            parametersValue.putIfAbsent("tag_not_in", tags);
            return this;
        }

        public MediaBuilder licensedByIn(String[] licensedBys) {
            mediaParameters.add(new ParameterString("licensedBy_in: $licensedBy_in, "));
            queryParameters.add(new ParameterString("$licensedBy_in: [String], "));
            parametersValue.putIfAbsent("licensedBy_in", licensedBys);
            return this;
        }

        public MediaBuilder averageScoreNot(int averageScore) {
            mediaParameters.add(new ParameterString("averageScore_not: $averageScore_not, "));
            queryParameters.add(new ParameterString("$averageScore_not: Int, "));
            parametersValue.putIfAbsent("averageScore_not", averageScore);
            return this;
        }

        public MediaBuilder averageScoreGreater(int averageScore) {
            mediaParameters.add(new ParameterString("averageScore_greater: $averageScore_greater, "));
            queryParameters.add(new ParameterString("$averageScore_greater: Int, "));
            parametersValue.putIfAbsent("averageScore_greater", averageScore);
            return this;
        }

        public MediaBuilder averageScoreLesser(int averageScore) {
            mediaParameters.add(new ParameterString("averageScore_lesser: $averageScore_lesser, "));
            queryParameters.add(new ParameterString("$averageScore_lesser: Int, "));
            parametersValue.putIfAbsent("averageScore_lesser", averageScore);
            return this;
        }

        public MediaBuilder popularityNot(int popularity) {
            mediaParameters.add(new ParameterString("popularity_not: $popularity_not, "));
            queryParameters.add(new ParameterString("$popularity_not: Int, "));
            parametersValue.putIfAbsent("popularity_not", popularity);
            return this;
        }

        public MediaBuilder popularityGreater(int popularity) {
            mediaParameters.add(new ParameterString("popularity_greater: $popularity_greater, "));
            queryParameters.add(new ParameterString("$popularity_greater: Int, "));
            parametersValue.putIfAbsent("popularity_greater", popularity);
            return this;
        }

        public MediaBuilder popularityLesser(int popularity) {
            mediaParameters.add(new ParameterString("popularity_lesser: $popularity_lesser, "));
            queryParameters.add(new ParameterString("$popularity_lesser: Int, "));
            parametersValue.putIfAbsent("popularity_lesser", popularity);
            return this;
        }

        public MediaBuilder sourceIn(MediaSource[] source) {
            mediaParameters.add(new ParameterString("source_in: $source_in, "));
            queryParameters.add(new ParameterString("$source_in: [MediaSource], "));
            parametersValue.putIfAbsent("source_in", source);
            return this;
        }

        public MediaBuilder sort(MediaSort[] source) {
            mediaParameters.add(new ParameterString("sort: $sort, "));
            queryParameters.add(new ParameterString("$sort: [MediaSort], "));
            parametersValue.putIfAbsent("sort", source);
            return this;
        }

        public Media buildMedia() {
            if (mediaParameters.isEmpty()) { throw new IllegalStateException("Media must posses at least 1 parameter!"); }

            StringBuilder mediaBuilder = new StringBuilder("Media(");
            mediaParameters.forEach(mediaBuilder::append);
            mediaBuilder.delete(mediaBuilder.length() - 2, mediaBuilder.length())
                    .append(") ")
                    .append(field.getField());
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(parametersValue);

            return new Media(mediaBuilder.toString(), queryParameters, jsonObject);
        }
    }
}
