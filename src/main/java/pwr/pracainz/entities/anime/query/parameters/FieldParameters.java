package pwr.pracainz.entities.anime.query.parameters;

public enum FieldParameters {
	id("Int"),
	idMal("Int"),
	type("MediaType"),
	format("MediaFormat"),
	season("MediaSeason"),
	seasonYear("Int"),
	seasonInt("Int"),
	episodes("Int"),
	duration("Int"),
	chapters("Int"),
	volumes("Int"),
	countryOfOrigin("CountryCode"),
	isLicensed("Boolean"),
	hashtag("String"),
	updatedAt("Int"),
	bannerImage("String"),
	genres("[String]"),
	synonyms("[String]"),
	averageScore("Int"),
	meanScore("Int"),
	popularity("Int"),
	isLocked("Boolean"),
	trending("Int"),
	favourites("Int"),
	isAdult("Boolean"),
	stats("MediaStats"),
	siteUrl("String"),
	modNotes("String");


	private final String fieldType;

	FieldParameters(String type) {
		this.fieldType = type;
	}

	public String getType() {
		return switch (this.fieldType) {
			case "[Int]" -> "int[]";
			case "[String]" -> "String[]";
			case "[MediaTag]" -> "MediaTag[]";
			default -> this.fieldType;
		};
	}
}
