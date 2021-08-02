package pwr.pracainz.entities.anime.query.parameters;

public enum QueryParameters {
    id("Int"),
    idMal("Int"),
    startDate("FuzzyDateInt"),
    endDate("FuzzyDateInt"),
    season("MediaSeason"),
    seasonYear("Int"),
    type("MediaType"),
    format("MediaFormat"),
    status("MediaStatus"),
    episodes("Int"),
    duration("Int"),
    chapters("Int"),
    volumes("Int"),
    isAdult("Boolean"),
    genre("String"),
    tag("String"),
    minimumTagRank("Int"),
    tagCategory("String"),
    averageScore("Int"),
    popularity("Int"),
    source("MediaSource"),
    countryOfOrigin(" CountryCode"),
    search("String"),
    id_not("Int"),
    id_in("Int"),
    id_not_in("Int"),
    idMal_not("Int"),
    idMal_in("Int"),
    idMal_not_in("Int"),
    startDate_greater("FuzzyDateInt"),
    startDate_lesser("FuzzyDateInt"),
    startDate_like("FuzzyDateString"),
    endDate_greater("FuzzyDateInt"),
    endDate_lesser("FuzzyDateInt"),
    endDate_like("FuzzyDateString"),
    format_not("MediaFormat"),
    format_in("MediaFormat"),
    format_not_in("MediaFormat"),
    status_not("MediaStatus"),
    status_in("MediaStatus"),
    status_not_in("MediaStatus"),
    episodes_greater("Int"),
    episodes_lesser("Int"),
    duration_greater("Int"),
    duration_lesser("Int"),
    chapters_greater("Int"),
    chapters_lesser("Int"),
    volumes_greater("Int"),
    volumes_lesser("Int"),
    genre_in("String"),
    genre_not_in("String"),
    tag_in("String"),
    tag_not_in("String"),
    tagCategory_in("String"),
    tagCategory_not_in("String"),
    averageScore_not("Int"),
    averageScore_greater("Int"),
    averageScore_lesser("Int"),
    popularity_not("Int"),
    popularity_greater("Int"),
    popularity_lesser("Int"),
    source_in("MediaSource"),
    sort("MediaSort");

    private final String parameterType;

    QueryParameters(String type) {
        this.parameterType = type;
    }

    public String getType() {
        return switch (this.parameterType) {
            case "Int" -> "int";
            case "Boolean" -> "boolean";
            case "[Int]" -> "int[]";
            case "[String]" -> "String[]";
            default -> this.parameterType;
        };
    }
}
