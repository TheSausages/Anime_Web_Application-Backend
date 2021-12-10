package pwr.pracainz.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import pwr.pracainz.DTO.SimpleMessageDTO;
import pwr.pracainz.DTO.animeInfo.*;
import pwr.pracainz.DTO.user.CompleteUserDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateValue;
import pwr.pracainz.entities.anime.query.parameters.media.MediaFormat;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSeason;
import pwr.pracainz.entities.anime.query.parameters.media.MediaStatus;
import pwr.pracainz.entities.anime.query.queryElements.QueryElements;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;
import pwr.pracainz.integrationtests.config.BaseIntegrationTest;
import pwr.pracainz.integrationtests.config.GraphQlUtils;
import pwr.pracainz.integrationtests.config.TestConstants;
import pwr.pracainz.integrationtests.config.TestI18nService;
import pwr.pracainz.integrationtests.config.keycloakprincipal.KeycloakPrincipalByUserId;
import pwr.pracainz.integrationtests.config.wiremock.WireMockAnimeSearchExtension;
import pwr.pracainz.integrationtests.config.wiremock.WireMockExtensionSummarizer;
import pwr.pracainz.integrationtests.config.wiremock.WireMockInitializer;
import pwr.pracainz.integrationtests.config.wiremock.WireMockPageExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;

//Initialize the wiremock server for Anilist
@ContextConfiguration(initializers = WireMockInitializer.class)
public class AnimeIntegrationTest extends BaseIntegrationTest {

	//For http://localhost:9090 mock url no path besides / is given
	protected String anilistWireMockURL = "/";

	//Will be autowired and is required, but without false there is an error
	@Autowired(required = false)
	private WireMockServer wireMockServer;

	@DisplayName("Get Anime by Id")
	@Nested
	class GetAnimeById {
		@Nested
		@DisplayName("Without Error")
		class GetAnimeByIdWithoutError {
			@BeforeEach
			public void setUp() {
				JsonNode responseBody = basicMediaAnilistResponse();

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody))
						);
			}

			@Test
			public void getAnimeById_NotLoggedInAnimeWithNoReviews_ReturnOnlyLocalAnimeData() {
				//given
				int animeId = 3;

				AnimeDTO expectedLocalAnimeInfo = new AnimeDTO(
						animeId,
						0.0,
						0,
						0
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node);

				assertThat(mapper.convertValue(node.get(TestConstants.LOCAL_ANIME_INFORMATION), AnimeDTO.class),
						allOf(
								notNullValue(),
								instanceOf(AnimeDTO.class),
								equalTo(expectedLocalAnimeInfo)
						));

				assertThat(node.get(TestConstants.REVIEWS), emptyIterable());

				assertThat(node.get(TestConstants.ANIME_USER_INFORMATION), nullValue());
			}

			@Test
			public void getAnimeById_NotLoggedInAnimeWithReviews_ReturnWithoutUserInfoButWithReview() {
				//given
				int animeId = 1;

				AnimeDTO expectedLocalAnimeInfo = new AnimeDTO(
						animeId,
						9.0,
						0,
						1
				);

				List<ReviewDTO> expectedReviews = List.of(
						new ReviewDTO(
								1,
								"First Test Review",
								"First Test Review Text",
								8,
								3,
								2
						));

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node);

				assertThat(mapper.convertValue(node.get(TestConstants.LOCAL_ANIME_INFORMATION), AnimeDTO.class),
						allOf(
								notNullValue(),
								instanceOf(AnimeDTO.class),
								equalTo(expectedLocalAnimeInfo)
						));

				assertThat(mapper.convertValue(node.get(TestConstants.REVIEWS), new TypeReference<List<ReviewDTO>>() {}),
						allOf(
								notNullValue(),
								instanceOf(List.class),
								iterableWithSize(1),
								equalTo(expectedReviews)
						));

				assertThat(node.get(TestConstants.ANIME_USER_INFORMATION), nullValue());
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getAnimeById_LoggedInAnimeWithReviews_ReturnAllInformation() {
				//given
				int animeId = 1;

				AnimeDTO expectedLocalAnimeInfo = new AnimeDTO(
						animeId,
						9.0,
						0,
						1
				);

				List<ReviewDTO> expectedReviews = List.of(
						new ReviewDTO(
								1,
								"First Test Review",
								"First Test Review Text",
								8,
								3,
								2
						));

				AnimeUserInfoDTO expectedAnimeUserInfo = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_DATA_ID,
										TestConstants.USER_WITH_DATA_USERNAME,
										2,
										10,
										25
								),
								new AnimeDTO(
										1,
										9.0,
										0,
										1
								)
						),
						AnimeUserStatus.WATCHING,
						LocalDate.of(2021, 10, 10),
						LocalDate.of(2021, 10, 11),
						0,
						false,
						LocalDateTime.of(2021, 10, 11, 0, 0),
						true,
						9,
						expectedReviews.get(0)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node);

				assertThat(mapper.convertValue(node.get(TestConstants.LOCAL_ANIME_INFORMATION), AnimeDTO.class),
						allOf(
								notNullValue(),
								instanceOf(AnimeDTO.class),
								equalTo(expectedLocalAnimeInfo)
						));

				assertThat(mapper.convertValue(node.get(TestConstants.REVIEWS), new TypeReference<List<ReviewDTO>>() {}),
						allOf(
								notNullValue(),
								instanceOf(List.class),
								iterableWithSize(1),
								equalTo(expectedReviews)
						));

				assertThat(mapper.convertValue(node.get(TestConstants.ANIME_USER_INFORMATION), AnimeUserInfoDTO.class),
						allOf(
								notNullValue(),
								instanceOf(AnimeUserInfoDTO.class),
								equalTo(expectedAnimeUserInfo)
						)
				);
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void getAnimeById_LoggedInAnimeWithoutReviews_ReturnWithEmptyUserInfo() {
				//given

				int animeId = 3;
				AnimeDTO expectedLocalAnimeInfo = new AnimeDTO(
						animeId,
						0.0,
						0,
						0
				);

				AnimeUserInfoDTO expectedAnimeUserInfo = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_NO_DATA_ID,
										TestConstants.USER_WITH_NO_DATA_USERNAME,
										0,
										0,
										0
								),
								new AnimeDTO(
										animeId,
										0.0,
										0,
										0
								)
						),
						AnimeUserStatus.NO_STATUS,
						null,
						null,
						0,
						false,
						LocalDateTime.now(),
						false,
						null,
						null
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node);

				assertThat(mapper.convertValue(node.get(TestConstants.LOCAL_ANIME_INFORMATION), AnimeDTO.class),
						allOf(
								notNullValue(),
								instanceOf(AnimeDTO.class),
								equalTo(expectedLocalAnimeInfo)
						));

				assertThat(node.get(TestConstants.REVIEWS), emptyIterable());

				assertThat(mapper.convertValue(node.get(TestConstants.ANIME_USER_INFORMATION), AnimeUserInfoDTO.class),
						allOf(
								notNullValue(),
								instanceOf(AnimeUserInfoDTO.class),
								samePropertyValuesAs(expectedAnimeUserInfo, "modification")
						)
				);
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void getAnimeById_LoggedInAnimeWithReviews_ReturnWithEmptyUserInfo() {
				//given

				int animeId = 1;

				AnimeDTO expectedLocalAnimeInfo = new AnimeDTO(
						animeId,
						9.0,
						0,
						1
				);

				List<ReviewDTO> expectedReviews = List.of(
						new ReviewDTO(
								1,
								"First Test Review",
								"First Test Review Text",
								8,
								3,
								2
						));

				AnimeUserInfoDTO expectedAnimeUserInfo = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_NO_DATA_ID,
										TestConstants.USER_WITH_NO_DATA_USERNAME,
										0,
										0,
										0
								),
								new AnimeDTO(
										1,
										9.0,
										0,
										1
								)
						),
						AnimeUserStatus.NO_STATUS,
						null,
						null,
						0,
						false,
						LocalDateTime.now(),
						false,
						null,
						null
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node);

				assertThat(mapper.convertValue(node.get(TestConstants.LOCAL_ANIME_INFORMATION), AnimeDTO.class),
						allOf(
								notNullValue(),
								instanceOf(AnimeDTO.class),
								equalTo(expectedLocalAnimeInfo)
						));

				assertThat(mapper.convertValue(node.get(TestConstants.REVIEWS), new TypeReference<List<ReviewDTO>>() {}),
						allOf(
								notNullValue(),
								instanceOf(List.class),
								iterableWithSize(1),
								equalTo(expectedReviews)
						));

				assertThat(mapper.convertValue(node.get(TestConstants.ANIME_USER_INFORMATION), AnimeUserInfoDTO.class),
						allOf(
								notNullValue(),
								instanceOf(AnimeUserInfoDTO.class),
								samePropertyValuesAs(expectedAnimeUserInfo, "modification")
						)
				);
			}
		}

		@Nested
		@DisplayName("With Error")
		class GetAnimeByIdWithError {

			@BeforeEach
			public void setUp() {
				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(aResponse()
										.withStatus(400)
										.withBody("Error"))
						);
			}

			@Test
			public void getAnimeById_NotLoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			public void getAnimeById_NotLoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getAnimeById_LoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getAnimeById_LoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_ANIME_BY_ID_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}
		}
	}

	@DisplayName("Get Current Season Anime")
	@Nested
	class GetCurrentSeasonAnime {
		@Nested
		@DisplayName("Without Error")
		class GetCurrentSeasonAnimeWithoutError {
			@BeforeEach
			public void setUp() {
				//This endpoint doesnt use pages
				JsonNode responseBody = basicPageAnilistResponse();

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody))
						);
			}

			@Test
			public void getCurrentSeasonAnime_NotLoggedIn_ReturnWithCurrentSeasonInformation() {
				//given
				ObjectNode expectedCurrentSeasonInformation = mapper.createObjectNode()
						.put("year", LocalDateTime.now().getYear())
						.put("season", MediaSeason.getCurrentSeason().toString());

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node);

				assertThat(mapper.convertValue(node.get(TestConstants.CURRENT_SEASON), JsonNode.class),
						allOf(
								notNullValue(),
								equalTo(expectedCurrentSeasonInformation)
						));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getCurrentSeasonAnime_LoggedIn_ReturnWithCurrentSeasonInformation() {
				//given
				ObjectNode expectedCurrentSeasonInformation = mapper.createObjectNode()
						.put("year", LocalDateTime.now().getYear())
						.put("season", MediaSeason.getCurrentSeason().toString());

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node);

				assertThat(mapper.convertValue(node.get(TestConstants.CURRENT_SEASON), JsonNode.class),
						allOf(
								notNullValue(),
								equalTo(expectedCurrentSeasonInformation)
						));
			}
		}

		@Nested
		@DisplayName("With Error")
		class GetCurrentSeasonAnimeWithError {

			@BeforeEach
			public void setUp() {
				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(aResponse()
										.withStatus(400)
										.withBody("Error"))
						);
			}

			@Test
			public void getCurrentSeasonAnime_NotLoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			public void getCurrentSeasonAnime_NotLoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getCurrentSeasonAnime_LoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getCurrentSeasonAnime_LoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}
		}
	}

	@DisplayName("Get Top Anime Of All Time")
	@Nested
	class GetTopAnimeOfAllTime {
		@TestInstance(TestInstance.Lifecycle.PER_CLASS)
		@Nested
		@DisplayName("Without Error")
		class GetTopAnimeOfAllTimeWithoutError {
			@ParameterizedTest(name = "Top anime of all time, not logged in test {index}: {argumentsWithNames}")
			@MethodSource("pages")
			public void getTopAnimeOfAllTime_NotLoggedIn_ReturnWithoutError(int page) {
				//given
				JsonNode responseBody = basicPageAnilistResponse();

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody)
										.withTransformers(WireMockPageExtension.wireMockPageExtensionName))
						);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_ANIME_OF_ALL_TIME_ENDPOINT, page)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node, page);
			}

			@ParameterizedTest(name = "Top anime of all time, logged in test {index}: {argumentsWithNames}")
			@MethodSource("pages")
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAnimeOfAllTime_LoggedIn_ReturnWithoutError(int page) {
				//given
				JsonNode responseBody = basicPageAnilistResponse();

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody)
										.withTransformers(WireMockPageExtension.wireMockPageExtensionName))
						);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_ANIME_OF_ALL_TIME_ENDPOINT, page)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node, page);
			}

			Stream<Arguments> pages() {
				return Stream.of(
						Arguments.of(0),
						Arguments.of(1),
						Arguments.of(2),
						Arguments.of(3),
						Arguments.of(20),
						Arguments.of(99)
				);
			}
		}

		@Nested
		@DisplayName("With Error")
		class GetTopAnimeOfAllTimeWithError {

			@BeforeEach
			public void setUp() {
				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(aResponse()
										.withStatus(400)
										.withBody("Error"))
						);
			}

			@Test
			public void getTopAnimeOfAllTime_NotLoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			public void getTopAnimeOfAllTime_NotLoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAnimeOfAllTime_LoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAnimeOfAllTime_LoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}
		}
	}

	@DisplayName("Get Top Anime Of All Time")
	@Nested
	class GetTopAiringAnime {
		@TestInstance(TestInstance.Lifecycle.PER_CLASS)
		@Nested
		@DisplayName("Without Error")
		class GetTopAiringAnimeWithoutError {
			@ParameterizedTest(name = "Top airing anime, not logged in test {index}: {argumentsWithNames}")
			@MethodSource("pages")
			public void getTopAiringAnime_NotLoggedIn_ReturnWithoutError(int page) {
				//given
				JsonNode responseBody = basicPageAnilistResponse();

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody)
										.withTransformers(WireMockPageExtension.wireMockPageExtensionName))
						);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_AIRING_ENDPOINT, page)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node, page);
			}

			@ParameterizedTest(name = "Top airing anime, logged in test {index}: {argumentsWithNames}")
			@MethodSource("pages")
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAiringAnime_LoggedIn_ReturnWithoutError(int page) {
				//given
				JsonNode responseBody = basicPageAnilistResponse();

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody)
										.withTransformers(WireMockPageExtension.wireMockPageExtensionName))
						);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_AIRING_ENDPOINT, page)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node, page);
			}

			Stream<Arguments> pages() {
				return Stream.of(
						Arguments.of(0),
						Arguments.of(1),
						Arguments.of(2),
						Arguments.of(3),
						Arguments.of(20),
						Arguments.of(99)
				);
			}
		}

		@Nested
		@DisplayName("With Error")
		class GetTopAiringAnimeWithError {

			@BeforeEach
			public void setUp() {
				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(aResponse()
										.withStatus(400)
										.withBody("Error"))
						);
			}

			@Test
			public void getTopAiringAnime_NotLoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			public void getTopAiringAnime_NotLoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAiringAnime_LoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAiringAnime_LoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_SEASON_ANIME_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}
		}
	}

	@DisplayName("Get Top Anime Movies")
	@Nested
	class GetTopAnimeMovies {
		@Nested
		@DisplayName("Without Error")
		@TestInstance(TestInstance.Lifecycle.PER_CLASS)
		class GetTopAnimeMoviesWithoutError {
			@ParameterizedTest(name = "Top anime movies, not logged in test {index}: {argumentsWithNames}")
			@MethodSource("pages")
			public void getTopAnimeMovies_NotLoggedIn_ReturnWithoutError(int page) {
				//given
				JsonNode responseBody = basicPageAnilistResponse();

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody)
										.withTransformers(WireMockPageExtension.wireMockPageExtensionName))
						);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_MOVIES_ENDPOINT, page)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node, page);
			}

			@ParameterizedTest(name = "Top anime movies, logged in test {index}: {argumentsWithNames}")
			@MethodSource("pages")
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAnimeMovies_LoggedIn_ReturnWithoutError(int page) {
				//given
				JsonNode responseBody = basicPageAnilistResponse();

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody)
										.withTransformers(WireMockPageExtension.wireMockPageExtensionName))
						);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_MOVIES_ENDPOINT, page)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node, page);
			}

			Stream<Arguments> pages() {
				return Stream.of(
						Arguments.of(0),
						Arguments.of(1),
						Arguments.of(2),
						Arguments.of(3),
						Arguments.of(20),
						Arguments.of(99)
				);
			}
		}

		@Nested
		@DisplayName("With Error")
		class GetTopAiringAnimeWithError {

			@BeforeEach
			public void setUp() {
				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(aResponse()
										.withStatus(400)
										.withBody("Error"))
						);
			}

			@Test
			public void getTopAnimeMovies_NotLoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_MOVIES_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			public void getTopAnimeMovies_NotLoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_MOVIES_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAnimeMovies_LoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_MOVIES_ENDPOINT, animeId)
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void getTopAnimeMovies_LoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int animeId = 1;
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_TOP_MOVIES_ENDPOINT, animeId)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}
		}
	}

	@DisplayName("Search using Anime Query")
	@Nested
	class SearchUsingAnimeQuery {
		@TestInstance(TestInstance.Lifecycle.PER_CLASS)
		@Nested
		@DisplayName("Without Error")
		class SearchUsingAnimeQueryWithoutError {

			@ParameterizedTest(name = "Search using anime query, not logged in test {index}: {argumentsWithNames}")
			@MethodSource("queries")
			public void searchUsingAnimeQuery_NotLoggedIn_ReturnWithoutErrorAndWithCorrectQuery(
					AnimeQuery animeQuery,
					BiConsumer<Map<String, Object>, AnimeQuery> assertions
			) throws JsonProcessingException {
				//given
				int page = 0;
				JsonNode responseBody = basicPageAnilistResponse();

				WireMockExtensionSummarizer.getInstance().addToTransformers(
						WireMockPageExtension.getInstance(),
						WireMockAnimeSearchExtension.getInstance()
				);

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody)
										.withTransformers(
												WireMockExtensionSummarizer.wireMockExtensionSummarizerName
										)
								)
						);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.SEARCH_FOR_ANIME_ENDPOINT, page)
						.body(BodyInserters.fromValue(animeQuery))
						.exchange();

				//then
				JsonNode node = spec
						.expectStatus().isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node, page);

				assertions.accept(
						GraphQlUtils.readStringIntoStringKeyMap(
								node.get(WireMockAnimeSearchExtension.variableFieldName).asText(),
								mapper
						),
						animeQuery
				);
			}

			@ParameterizedTest(name = "Search using anime query, logged in test {index}: {argumentsWithNames}")
			@MethodSource("queries")
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void searchUsingAnimeQuery_LoggedIn_ReturnWithoutErrorAndWithCorrectQuery(
					AnimeQuery animeQuery,
					BiConsumer<Map<String, Object>, AnimeQuery> assertions
			) throws JsonProcessingException {
				//given
				int page = 0;
				JsonNode responseBody = basicPageAnilistResponse();

				WireMockExtensionSummarizer.getInstance().addToTransformers(
						WireMockPageExtension.getInstance(),
						WireMockAnimeSearchExtension.getInstance()
				);

				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(ResponseDefinitionBuilder
										.okForJson(responseBody)
										.withTransformers(
												WireMockExtensionSummarizer.wireMockExtensionSummarizerName
										)
								)
						);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.SEARCH_FOR_ANIME_ENDPOINT, page)
						.body(BodyInserters.fromValue(animeQuery))
						.exchange();

				//then
				JsonNode node = spec
						.expectStatus().isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());
				checkForBasicAnilistResponseFields(node, page);

				assertions.accept(
						GraphQlUtils.readStringIntoStringKeyMap(
								node.get(WireMockAnimeSearchExtension.variableFieldName).asText(),
								mapper
						),
						animeQuery
				);
			}

			Stream<Arguments> queries() {
				//given
				LocalDateTime testingDateTime = LocalDateTime.now();
				int testingFuzzyDateNumber = FuzzyDateValue
						.getFuzzyDateValueBuilder()
						.fromDate(testingDateTime)
						.buildFuzzyDateValue()
						.getFuzzyDateNumber();
				MediaSeasonYear testingSeason = new MediaSeasonYear(MediaSeason.FALL, 2021);

				return Stream.of(
						Arguments.of(
								AnimeQuery
										.builder()
										.title("Title")
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "search");

									assertThat(
											(String) variables.get("search"),
											stringMatcher(query.getTitle())
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.season(testingSeason)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 2, "season");

									assertThat(
											MediaSeason.valueOf((String) variables.get("season")),
											mediaSeasonMatcher(testingSeason.getSeason())
									);

									intAssertion(testingSeason.getYear());
								}

						),
						Arguments.of(
								AnimeQuery
										.builder()
										.status(MediaStatus.FINISHED)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "status");

									assertThat(
											MediaStatus.valueOf((String) variables.get("status")),
											mediaStatusMatcher(query.getStatus())
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.format(MediaFormat.TV)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "format");

									assertThat(
											MediaFormat.valueOf((String) variables.get("format")),
											mediaFormatMatcher(query.getFormat())
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.maxStartDate(testingDateTime)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "startDate_lesser");

									assertThat(
											(int) variables.get("startDate_lesser"),
											intAssertion(testingFuzzyDateNumber)
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.minStartDate(testingDateTime)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "startDate_greater");

									assertThat(
											(int) variables.get("startDate_greater"),
											intAssertion(testingFuzzyDateNumber)
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.maxEndDate(testingDateTime)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "endDate_lesser");

									assertThat(
											(int) variables.get("endDate_lesser"),
											intAssertion(testingFuzzyDateNumber)
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.minEndDate(testingDateTime)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "endDate_greater");

									assertThat(
											(int) variables.get("endDate_greater"),
											intAssertion(testingFuzzyDateNumber)
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.maxNrOfEpisodes(10)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "episodes_lesser");

									assertThat(
											(int) variables.get("episodes_lesser"),
											intAssertion(query.getMaxNrOfEpisodes())
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.minNrOfEpisodes(1)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "episodes_greater");

									assertThat(
											(int) variables.get("episodes_greater"),
											intAssertion(query.getMinNrOfEpisodes())
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.maxAverageScore(90)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "averageScore_lesser");

									assertThat(
											(int) variables.get("averageScore_lesser"),
											intAssertion(query.getMaxAverageScore())
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.minAverageScore(50)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 1, "averageScore_greater");

									assertThat(
											(int) variables.get("averageScore_greater"),
											intAssertion(query.getMinAverageScore())
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.title("Search Title")
										.minAverageScore(50)
										.format(MediaFormat.TV)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 3,
											"search",
											"averageScore_greater",
											"format"
									);

									assertThat(
											(String) variables.get("search"),
											stringMatcher(query.getTitle())
									);

									assertThat(
											(int) variables.get("averageScore_greater"),
											intAssertion(query.getMinAverageScore())
									);

									assertThat(
											MediaFormat.valueOf((String) variables.get("format")),
											mediaFormatMatcher(query.getFormat())
									);
								}
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.season(testingSeason)
										.minStartDate(testingDateTime)
										.maxStartDate(testingDateTime)
										.minNrOfEpisodes(10)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 5,
											"season",
											"startDate_greater",
											"startDate_lesser",
											"episodes_greater"
									);

									assertThat(
											MediaSeason.valueOf((String) variables.get("season")),
											mediaSeasonMatcher(testingSeason.getSeason())
									);

									intAssertion(testingSeason.getYear());

									assertThat(
											(int) variables.get("startDate_greater"),
											intAssertion(testingFuzzyDateNumber)
									);

									assertThat(
											(int) variables.get("startDate_lesser"),
											intAssertion(testingFuzzyDateNumber)
									);

									assertThat(
											(int) variables.get("episodes_greater"),
											intAssertion(query.getMinNrOfEpisodes())
									);
								}
						),
						//Test full Query
						Arguments.of(
								AnimeQuery
										.builder()
										.title("Max Test Title")
										.format(MediaFormat.ONESHOT)
										.status(MediaStatus.HIATUS)
										.season(testingSeason)
										.maxAverageScore(95)
										.minAverageScore(10)
										.minStartDate(testingDateTime)
										.maxStartDate(testingDateTime)
										.minEndDate(testingDateTime)
										.maxEndDate(testingDateTime)
										.minNrOfEpisodes(10)
										.maxNrOfEpisodes(50)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) -> {
									variableMapMatcher(variables, 13,
											"search",
											"format",
											"status",
											"season",
											"averageScore_greater",
											"averageScore_lesser",
											"startDate_greater",
											"startDate_lesser",
											"endDate_greater",
											"endDate_lesser",
											"episodes_greater",
											"episodes_lesser"
									);

									assertThat(
											(String) variables.get("search"),
											stringMatcher(query.getTitle())
									);

									assertThat(
											MediaFormat.valueOf((String) variables.get("format")),
											mediaFormatMatcher(query.getFormat())
									);

									assertThat(
											MediaStatus.valueOf((String) variables.get("status")),
											mediaStatusMatcher(query.getStatus())
									);

									assertThat(
											MediaSeason.valueOf((String) variables.get("season")),
											mediaSeasonMatcher(testingSeason.getSeason())
									);

									intAssertion(testingSeason.getYear());

									assertThat(
											(int) variables.get("averageScore_greater"),
											intAssertion(query.getMinAverageScore())
									);

									assertThat(
											(int) variables.get("averageScore_lesser"),
											intAssertion(query.getMaxAverageScore())
									);

									assertThat(
											(int) variables.get("startDate_greater"),
											intAssertion(testingFuzzyDateNumber)
									);


									assertThat(
											(int) variables.get("startDate_lesser"),
											intAssertion(testingFuzzyDateNumber)
									);

									assertThat(
											(int) variables.get("endDate_greater"),
											intAssertion(testingFuzzyDateNumber)
									);


									assertThat(
											(int) variables.get("endDate_lesser"),
											intAssertion(testingFuzzyDateNumber)
									);

									assertThat(
											(int) variables.get("episodes_greater"),
											intAssertion(query.getMinNrOfEpisodes())
									);

									assertThat(
											(int) variables.get("episodes_lesser"),
											intAssertion(query.getMaxNrOfEpisodes())
									);
								}
						)
				);
			}

			private void variableMapMatcher(Map<String, Object> variables, int expectedSize, String ...expectedElementName) {
				assertThat(
						variables,
						allOf(
								notNullValue(),
								// 4 more additional elements are: type, sort, page, perPage
								// See  page Builder in searchByQuery method
								aMapWithSize(expectedSize + 4)
						)
				);

				assertThat(
						variables.keySet(),
						hasItems(expectedElementName)
				);
			}

			private Matcher<String> stringMatcher(String testString) {
				return allOf(
						notNullValue(),
						instanceOf(String.class),
						is(testString)
				);
			}

			private Matcher<MediaSeason> mediaSeasonMatcher(MediaSeason testSeason) {
				return allOf(
						notNullValue(),
						instanceOf(MediaSeason.class),
						is(testSeason)
				);
			}

			private Matcher<MediaStatus> mediaStatusMatcher(MediaStatus testStatus) {
				return allOf(
						notNullValue(),
						instanceOf(MediaStatus.class),
						is(testStatus)
				);
			}

			private Matcher<MediaFormat> mediaFormatMatcher(MediaFormat testFormat) {
				return allOf(
						notNullValue(),
						instanceOf(MediaFormat.class),
						is(testFormat)
				);
			}

			private Matcher<Integer> intAssertion(int testValue) {
				return allOf(
						notNullValue(),
						instanceOf(int.class),
						is(testValue)
				);
			}
		}

		@Nested
		@DisplayName("With Error")
		class SearchUsingAnimeQueryWithError {

			@BeforeEach
			public void setUp() {
				wireMockServer
						.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
								.willReturn(aResponse()
										.withStatus(400)
										.withBody("Error"))
						);
			}

			@Test
			public void searchUsingAnimeQuery_NotLoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int page = 0;
				AnimeQuery animeQuery = AnimeQuery
						.builder()
						.title("Title")
						.build();
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.SEARCH_FOR_ANIME_ENDPOINT, page)
						.body(BodyInserters.fromValue(animeQuery))
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			public void searchUsingAnimeQuery_NotLoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int page = 0;
				AnimeQuery animeQuery = AnimeQuery
						.builder()
						.title("Title")
						.build();
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.SEARCH_FOR_ANIME_ENDPOINT, page)
						.body(BodyInserters.fromValue(animeQuery))
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void searchUsingAnimeQuery_LoggedIn_ReturnErrorWithDefaultLocal() {
				//given
				int page = 0;
				AnimeQuery animeQuery = AnimeQuery
						.builder()
						.title("Title")
						.build();
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation("anime.anilist-server-no-response")
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.SEARCH_FOR_ANIME_ENDPOINT, page)
						.body(BodyInserters.fromValue(animeQuery))
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void searchUsingAnimeQuery_LoggedIn_ReturnErrorWithPolishLocal() {
				//given
				int page = 0;
				AnimeQuery animeQuery = AnimeQuery
						.builder()
						.title("Title")
						.build();
				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.anilist-server-no-response",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.SEARCH_FOR_ANIME_ENDPOINT, page)
						.body(BodyInserters.fromValue(animeQuery))
						.header("Accept-Language", "pl")
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_SERVICE_UNAVAILABLE)
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(node, notNullValue());

				assertThat(mapper.convertValue(node.get(TestConstants.MESSAGE), SimpleMessageDTO.class), allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}
		}
	}

	@DisplayName("Update Anime User Information")
	@Nested
	class PutAnimeUserInformation {
		@Nested
		@DisplayName("Without Error")
		class PutAnimeUserInformationWithoutError {
			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void putAnimeUserInformation_LoggedInExistingInformationWithoutReview_ReturnWithoutErrorAndUpdatedWatchTime() {
				//given
				int animeId = 1;
				AnimeUserInfoIdDTO sendingUserInfoIdDTO = new AnimeUserInfoIdDTO(
						new SimpleUserDTO(
								TestConstants.USER_WITH_DATA_ID,
								TestConstants.USER_WITH_DATA_USERNAME,
								2,
								10,
								25
						),
						new AnimeDTO(
								animeId,
								7.0,
								0,
								1
						)
				);
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						sendingUserInfoIdDTO,
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						false,
						5,
						null
				);

				AnimeUserInfoIdDTO expectingUserInfoIdDTO = new AnimeUserInfoIdDTO(
						new SimpleUserDTO(
								TestConstants.USER_WITH_DATA_ID,
								TestConstants.USER_WITH_DATA_USERNAME,
								2,
								85,
								25
						),
						new AnimeDTO(
								animeId,
								7.0,
								0,
								1
						)
				);
				AnimeUserInfoDTO expectingUserInfoDTO = new AnimeUserInfoDTO(
						expectingUserInfoIdDTO,
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						false,
						5,
						null
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(mapper.convertValue(node, AnimeUserInfoDTO.class), allOf(
						notNullValue(),
						instanceOf(AnimeUserInfoDTO.class),
						samePropertyValuesAs(expectingUserInfoDTO, "modification")
				));

				//additionally, check if the user watchTime was updated in the profile
				CompleteUserDTO userProfile = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_USER_PROFILE_ENDPOINT)
						.exchange()
						.expectStatus()
						.isOk()
						.expectBody(CompleteUserDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(userProfile, allOf(
						notNullValue(),
						instanceOf(CompleteUserDTO.class),
						hasProperty("watchTime", allOf(
								notNullValue(),
								instanceOf(int.class),
								equalTo(85)
						))
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void putAnimeUserInformation_LoggedInExistingInformationWithReview_ReturnWithoutErrorAndUpdatedWatchTime() {
				//given
				int animeId = 1;
				ReviewDTO review = new ReviewDTO(
						1,
						"Title",
						"Text",
						0,
						0,
						0
				);
				AnimeUserInfoIdDTO sendingUserInfoIdDTO = new AnimeUserInfoIdDTO(
						new SimpleUserDTO(
								TestConstants.USER_WITH_DATA_ID,
								TestConstants.USER_WITH_DATA_USERNAME,
								2,
								10,
								25
						),
						new AnimeDTO(
								animeId,
								7.0,
								0,
								1
						)
				);
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						sendingUserInfoIdDTO,
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						true,
						5,
						review
				);

				AnimeUserInfoIdDTO expectingUserInfoIdDTO = new AnimeUserInfoIdDTO(
						new SimpleUserDTO(
								TestConstants.USER_WITH_DATA_ID,
								TestConstants.USER_WITH_DATA_USERNAME,
								2,
								85,
								25
						),
						new AnimeDTO(
								animeId,
								7.0,
								0,
								1
						)
				);
				AnimeUserInfoDTO expectingUserInfoDTO = new AnimeUserInfoDTO(
						expectingUserInfoIdDTO,
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						true,
						5,
						review
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(mapper.convertValue(node, AnimeUserInfoDTO.class), allOf(
						notNullValue(),
						instanceOf(AnimeUserInfoDTO.class),
						samePropertyValuesAs(expectingUserInfoDTO, "modification", "review"),
						hasProperty("review", samePropertyValuesAs(review, "id"))
				));

				//additionally, check if the user watchTime was updated in the profile
				CompleteUserDTO userProfile = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_USER_PROFILE_ENDPOINT)
						.exchange()
						.expectStatus()
						.isOk()
						.expectBody(CompleteUserDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(userProfile, allOf(
						notNullValue(),
						instanceOf(CompleteUserDTO.class),
						hasProperty("watchTime", allOf(
								notNullValue(),
								instanceOf(int.class),
								equalTo(85)
						))
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void putAnimeUserInformation_LoggedInNonExistingInformationWithoutReview_ReturnWithoutErrorAndUpdatedWatchTime() {
				//given
				int animeId = 3;
				AnimeUserInfoIdDTO sendingUserInfoIdDTO = new AnimeUserInfoIdDTO(
						new SimpleUserDTO(
								TestConstants.USER_WITH_NO_DATA_ID,
								TestConstants.USER_WITH_NO_DATA_USERNAME,
								0,
								0,
								0
						),
						new AnimeDTO(
								animeId,
								0.0,
								0,
								0
						)
				);
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						sendingUserInfoIdDTO,
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						false,
						5,
						null
				);

				SimpleUserDTO userDTO = new SimpleUserDTO(
						TestConstants.USER_WITH_NO_DATA_ID,
						TestConstants.USER_WITH_NO_DATA_USERNAME,
						0,
						66,
						0
				);
				AnimeUserInfoIdDTO expectingUserInfoIdDTO = new AnimeUserInfoIdDTO(
						userDTO,
						new AnimeDTO(
								animeId,
								5.0,
								0,
								0
						)
				);
				AnimeUserInfoDTO expectingUserInfoDTO = new AnimeUserInfoDTO(
						expectingUserInfoIdDTO,
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						false,
						5,
						null
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(mapper.convertValue(node, AnimeUserInfoDTO.class), allOf(
						notNullValue(),
						instanceOf(AnimeUserInfoDTO.class),
						samePropertyValuesAs(expectingUserInfoDTO, "modification", "id", "review"),
						hasProperty("id", allOf(
								notNullValue(),
								instanceOf(AnimeUserInfoIdDTO.class),
								samePropertyValuesAs(expectingUserInfoIdDTO, "user"),
								hasProperty("user", allOf(
										notNullValue(),
										instanceOf(SimpleUserDTO.class),
										samePropertyValuesAs(userDTO, "achievementPoints")
								))
						))
				));

				//additionally, check if the user watchTime was updated in the profile
				CompleteUserDTO userProfile = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_USER_PROFILE_ENDPOINT)
						.exchange()
						.expectStatus()
						.isOk()
						.expectBody(CompleteUserDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(userProfile, allOf(
						notNullValue(),
						instanceOf(CompleteUserDTO.class),
						hasProperty("watchTime", allOf(
								notNullValue(),
								instanceOf(int.class),
								equalTo(66)
						))
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void putAnimeUserInformation_LoggedInNonExistingInformationWithReview_ReturnWithoutErrorAndUpdatedWatchTime() {
				//given
				int animeId = 3;
				ReviewDTO review = new ReviewDTO(
						1,
						"Title",
						"Text",
						0,
						0,
						0
				);
				AnimeUserInfoIdDTO sendingUserInfoIdDTO = new AnimeUserInfoIdDTO(
						new SimpleUserDTO(
								TestConstants.USER_WITH_NO_DATA_ID,
								TestConstants.USER_WITH_NO_DATA_USERNAME,
								0,
								0,
								0
						),
						new AnimeDTO(
								animeId,
								7.0,
								0,
								0
						)
				);
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						sendingUserInfoIdDTO,
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						true,
						5,
						review
				);

				SimpleUserDTO userDTO = new SimpleUserDTO(
						TestConstants.USER_WITH_NO_DATA_ID,
						TestConstants.USER_WITH_NO_DATA_USERNAME,
						0,
						66,
						10
				);
				AnimeUserInfoIdDTO expectingUserInfoIdDTO = new AnimeUserInfoIdDTO(
						userDTO,
						new AnimeDTO(
								animeId,
								5.0,
								0,
								0
						)
				);
				AnimeUserInfoDTO expectingUserInfoDTO = new AnimeUserInfoDTO(
						expectingUserInfoIdDTO,
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						true,
						5,
						review
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.exchange();

				//then
				JsonNode node = spec.expectStatus()
						.isOk()
						.expectBody(JsonNode.class)
						.returnResult()
						.getResponseBody();

				assertThat(mapper.convertValue(node, AnimeUserInfoDTO.class), allOf(
						notNullValue(),
						instanceOf(AnimeUserInfoDTO.class),
						samePropertyValuesAs(expectingUserInfoDTO, "modification", "id", "review"),
						hasProperty("review", allOf(
								notNullValue(),
								instanceOf(ReviewDTO.class),
								samePropertyValuesAs(review, "id")
						)),
						hasProperty("id", allOf(
								notNullValue(),
								instanceOf(AnimeUserInfoIdDTO.class),
								samePropertyValuesAs(expectingUserInfoIdDTO, "user"),
								hasProperty("user", allOf(
										notNullValue(),
										instanceOf(SimpleUserDTO.class),
										samePropertyValuesAs(userDTO, "achievementPoints")
								))
						))
				));

				//additionally, check if the user watchTime was updated in the profile
				CompleteUserDTO userProfile = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_USER_PROFILE_ENDPOINT)
						.exchange()
						.expectStatus()
						.isOk()
						.expectBody(CompleteUserDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(userProfile, allOf(
						notNullValue(),
						instanceOf(CompleteUserDTO.class),
						hasProperty("watchTime", allOf(
								notNullValue(),
								instanceOf(int.class),
								equalTo(66)
						))
				));
			}
		}

		@Nested
		@DisplayName("Without Error")
		class PutAnimeUserInformationWithError {
			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void putAnimeUserInformation_LoggedInNonExistingInformationWrongUserWithoutReview_ThrowErrorDefaultLocale() {
				//given
				int animeId = 3;
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_DATA_ID,
										TestConstants.USER_WITH_DATA_USERNAME,
										2,
										10,
										25
								),
								new AnimeDTO(
										animeId,
										0.0,
										0,
										0
								)
						),
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						false,
						5,
						null
				);

				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.error-during-anime-info-update"
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.exchange();

				//then
				SimpleMessageDTO message = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_UNAUTHORIZED)
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(message, notNullValue());

				assertThat(message, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void putAnimeUserInformation_LoggedInNonExistingInformationWrongUserWithoutReview_ThrowErrorPolishLocale() {
				//given
				int animeId = 3;
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_DATA_ID,
										TestConstants.USER_WITH_DATA_USERNAME,
										2,
										10,
										25
								),
								new AnimeDTO(
										animeId,
										0.0,
										0,
										0
								)
						),
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						false,
						5,
						null
				);

				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"anime.error-during-anime-info-update",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.header("Accept-Language", "pl")
						.exchange();

				//then
				SimpleMessageDTO message = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_UNAUTHORIZED)
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(message, notNullValue());

				assertThat(message, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void putAnimeUserInformation_LoggedInNonExistingInformationReviewTitleTooLong_ThrowErrorDefaultLocale() {
				//given
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_NO_DATA_ID,
										TestConstants.USER_WITH_NO_DATA_USERNAME,
										0,
										0,
										0
								),
								new AnimeDTO(
										3,
										7.0,
										0,
										0
								)
						),
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						true,
						5,
						new ReviewDTO(
								1,
								"TooLongTitle----------------------------------------------------------------------------------------------------",
								"Text",
								0,
								0,
								0
						)
				);

				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"general.an-error-occurred"
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.exchange();

				//then
				SimpleMessageDTO message = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(message, notNullValue());

				assertThat(message, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void putAnimeUserInformation_LoggedInNonExistingInformationReviewTitleTooLong_ThrowErrorPolishLocale() {
				//given
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_NO_DATA_ID,
										TestConstants.USER_WITH_NO_DATA_USERNAME,
										0,
										0,
										0
								),
								new AnimeDTO(
										3,
										7.0,
										0,
										0
								)
						),
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						true,
						5,
						new ReviewDTO(
								1,
								"TooLongTitle--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
								"Text",
								0,
								0,
								0
						)
				);

				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"general.an-error-occurred",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.header("Accept-Language", "pl")
						.exchange();

				//then
				SimpleMessageDTO message = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(message, notNullValue());

				assertThat(message, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void putAnimeUserInformation_LoggedInNonExistingInformationReviewTextTooLong_ThrowErrorDefaultLocale() {
				//given
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_NO_DATA_ID,
										TestConstants.USER_WITH_NO_DATA_USERNAME,
										0,
										0,
										0
								),
								new AnimeDTO(
										3,
										7.0,
										0,
										0
								)
						),
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						true,
						5,
						new ReviewDTO(
								1,
								"Title",
								"TooLongText------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
								0,
								0,
								0
						)
				);

				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"general.an-error-occurred"
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.exchange();

				//then
				SimpleMessageDTO message = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(message, notNullValue());

				assertThat(message, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void putAnimeUserInformation_LoggedInNonExistingInformationReviewTextTooLong_ThrowErrorPolishLocale() {
				//given
				AnimeUserInfoDTO sendingUserInfoDTO = new AnimeUserInfoDTO(
						new AnimeUserInfoIdDTO(
								new SimpleUserDTO(
										TestConstants.USER_WITH_NO_DATA_ID,
										TestConstants.USER_WITH_NO_DATA_USERNAME,
										0,
										0,
										0
								),
								new AnimeDTO(
										3,
										7.0,
										0,
										0
								)
						),
						AnimeUserStatus.WATCHING,
						LocalDate.now().minusDays(2),
						LocalDate.now().minusDays(1),
						3,
						true,
						LocalDateTime.now(),
						true,
						5,
						new ReviewDTO(
								1,
								"Title",
								"TooLongText------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
								0,
								0,
								0
						)
				);

				SimpleMessageDTO errorMessage = new SimpleMessageDTO(
						i18nService.getTranslation(
								"general.an-error-occurred",
								TestI18nService.POLISH_LOCALE
						)
				);


				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(sendingUserInfoDTO))
						.header("Accept-Language", "pl")
						.exchange();

				//then
				SimpleMessageDTO message = spec.expectStatus()
						.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(message, notNullValue());

				assertThat(message, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(errorMessage)
				));
			}
		}
	}

	private void checkForBasicAnilistResponseFields(JsonNode node, int page) {
		assertThat(node.get("page").asInt(), allOf(
				notNullValue(),
				instanceOf(int.class),
				is(page)
				));

		assertThat(node.get("Field1").asText(), allOf(
				notNullValue(),
				instanceOf(String.class),
				is("value1")
		));

		assertThat(node.get("Field2"), allOf(
				notNullValue(),
				instanceOf(JsonNode.class),
				is(mapper.createObjectNode()
						.put("Field2-field1", 1)
						.put("Field2-field2", "value2")
				)
		));
	}

	private void checkForBasicAnilistResponseFields(JsonNode node) {
		assertThat(node.get("Field1").asText(), allOf(
				notNullValue(),
				instanceOf(String.class),
				is("value1")
		));

		assertThat(node.get("Field2"), allOf(
				notNullValue(),
				instanceOf(JsonNode.class),
				is(mapper.createObjectNode()
						.put("Field2-field1", 1)
						.put("Field2-field2", "value2")
				)
		));
	}

	private JsonNode basicPageAnilistResponse() {
		return GraphQlUtils.addElementToCreateGraphQlQueryAnwser(
				mapper.createObjectNode()
						.put("Field1", "value1")
						.set("Field2", mapper.createObjectNode()
								.put("Field2-field1", 1)
								.put("Field2-field2", "value2")
						),
				QueryElements.Page,
				mapper
		);
	}

	private JsonNode basicMediaAnilistResponse() {
		return GraphQlUtils.addElementToCreateGraphQlQueryAnwser(
				mapper.createObjectNode()
						.put("Field1", "value1")
						.set("Field2", mapper.createObjectNode()
								.put("Field2-field1", 1)
								.put("Field2-field2", "value2")
						),
				QueryElements.Media,
				mapper
		);
	}
}
