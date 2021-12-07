package pwr.pracainz.integrationtests.config;

public class TestConstants {
	//Endpoints
	public static final String GET_ANIME_BY_ID_ENDPOINT = "/api/anime/{animeId}";
	public static final String GET_CURRENT_SEASON_ANIME_ENDPOINT = "/api/anime/season/current";
	public static final String GET_TOP_ANIME_OF_ALL_TIME_ENDPOINT = "/api/anime/ranking/topAllTime/{pageNumber}";
	public static final String GET_TOP_AIRING_ENDPOINT = "/api/anime/ranking/topAiring/{pageNumber}";
	public static final String GET_TOP_MOVIES_ENDPOINT = "/api/anime/ranking/topMovies/{pageNumber}";
	public static final String SUBSCRIBE_TO_ACHIEVEMENTS_ENDPOINT = "/api/achievements/subscribe";
	public static final String CANCEL_ACHIEVEMENTS_SUBSCRIPTION_ENDPOINT = "/api/achievements/cancel";
	public static final String POST_POST_FOR_THREAD_ENDPOINT = "/api/forum/thread/{threadId}/post";
	public static final String LOGIN_ENDPOINT = "/api/auth/login";

	//Query elements

	//jsonPaths
	public static final String LOCAL_ANIME_INFORMATION = "localAnimeInformation";
	public static final String REVIEWS = "reviews";
	public static final String ANIME_USER_INFORMATION = "animeUserInformation";
	public static final String CURRENT_SEASON = "currentSeason";

	public static final String MESSAGE = "message";

	//Users
	public static final String USER_WITH_DATA_ID = "467a809a-d893-48c2-85e2-82f9ce4b1560";
	public static final String USER_WITH_DATA_USERNAME = "UserWithData";

	public static final String SECOND_FORUM_USER_ID = "eabc11d0-e6da-49f8-a6db-6bceb84a06bc";
	public static final String SECOND_FORUM_USER_USERNAME = "SecondForumUser";

	public static final String USER_WITH_NO_DATA_ID = "51d75b0a-9d45-4fe8-bf0e-01f7abd07c0b";
	public static final String USER_WITH_NO_DATA_USERNAME = "UserWithNoData";
}
