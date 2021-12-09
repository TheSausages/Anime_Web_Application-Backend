package pwr.pracainz.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import pwr.pracainz.DTO.SimpleMessageDTO;
import pwr.pracainz.DTO.animeInfo.*;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateValue;
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
								.willReturn(aResponse()
										.withStatus(200)
										.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
										.withJsonBody(responseBody))
						);
			}

			@Test
			public void getAnimeById_NotLoggedIn_ReturnWithOnlyLocalAnimeData() {
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
			public void getAnimeById_NotLoggedIn_ReturnWithoutUserInfoWithReview() {
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
			public void getAnimeById_LoggedIn_ReturnWithExistingUserInfo() {
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
			public void getAnimeById_LoggedIn_ReturnWithEmptyUserInfo() {
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
			public void getCurrentSeasonAnime_NotLoggedIn_ReturnWithoutError() {
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
			public void getCurrentSeasonAnime_LoggedIn_ReturnWithoutError() {
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
			@ParameterizedTest
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

			@ParameterizedTest
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
			@ParameterizedTest
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

			@ParameterizedTest
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
			@ParameterizedTest
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

			@ParameterizedTest
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
		class WithoutError {
			@ParameterizedTest
			@MethodSource("queries")
			public void searchUsingAnimeQuery_NotLoggedIn_ReturnWithoutError(
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
				LocalDateTime testingDateTime = LocalDateTime.now();
				int testingFuzzyDateNumber = FuzzyDateValue
						.getFuzzyDateValueBuilder()
						.fromDate(testingDateTime)
						.buildFuzzyDateValue()
						.getFuzzyDateNumber();

				return Stream.of(
						Arguments.of(
								AnimeQuery
										.builder()
										.title("Title")
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) ->
									assertThat((String) variables.get("search"), allOf(
											notNullValue(),
											instanceOf(String.class),
											is(query.getTitle())
									))

						),
						Arguments.of(
								AnimeQuery
										.builder()
										.status(MediaStatus.FINISHED)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) ->
									assertThat(MediaStatus.valueOf((String) variables.get("status")), allOf(
											notNullValue(),
											instanceOf(MediaStatus.class),
											is(query.getStatus())
									))
						),
						Arguments.of(
								AnimeQuery
										.builder()
										.maxStartDate(testingDateTime)
										.build(),
								(BiConsumer<Map<String, Object>, AnimeQuery>) (variables, query) ->
										assertThat((int) variables.get("startDate_lesser"), allOf(
												notNullValue(),
												instanceOf(int.class),
												is(testingFuzzyDateNumber)
										))
						)
				);
			}
		}

		@Nested
		@DisplayName("With Error")
		class WithError {

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
