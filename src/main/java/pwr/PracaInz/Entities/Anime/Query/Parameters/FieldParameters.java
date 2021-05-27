package pwr.PracaInz.Entities.Anime.Query.Parameters;

public enum FieldParameters {
    id("Int"),
    idMal("Int"),
    type("MediaType"),
    format("MediaFormat"),
    startDate("FuzzyDate"),
    endDate("FuzzyDate"),
    status("MediaStatus"),
    season("MediaSeason"),
    seasonYear("Int"),
    seasonInt("Int"),
    episodes("Int"),
    duration("Int"),
    chapters("Int"),
    volumes("Int"),
    countryOfOrigin("CountryCode"),
    isLicensed("Boolean"),
    source("MediaSource"),
    hashtag("String"),
    updatedAt("Int"),
    coverImage("MediaCoverImage"),
    bannerImage("String"),
    genres("[String]"),
    synonyms("[String]"),
    averageScore("Int"),
    meanScore("Int"),
    popularity("Int"),
    isLocked("Boolean"),
    trending("Int"),
    favorites("Int"),
    relations("MediaConnection"),
    isAdult("Boolean"),
    stats("MediaStats"),
    siteUrl("String"),
    isRecommendationBlocked("Boolean"),
    modNotes("String")
    ;


    private final String fieldType;

    FieldParameters(String type) {
        this.fieldType = type;
    }

    public String getType() {
        return switch (this.fieldType) {
            case "Int" -> "int";
            case "Boolean" -> "boolean";
            case "[Int]" -> "int[]";
            case "[String]" -> "String[]";
            case "[MediaTag]" -> "MediaTag[]";
            default -> this.fieldType;
        };
    }
}
