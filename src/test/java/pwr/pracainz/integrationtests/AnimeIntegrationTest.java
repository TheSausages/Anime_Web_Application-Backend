package pwr.pracainz.integrationtests;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import pwr.pracainz.DTO.animeInfo.AnimeDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.DTO.animeInfo.ReviewDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.entities.anime.query.queryElements.QueryElements;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;
import pwr.pracainz.integrationtests.config.BaseIntegrationTest;
import pwr.pracainz.integrationtests.config.TestConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;

public class AnimeIntegrationTest extends BaseIntegrationTest {
	@BeforeEach
	public void setUp() {
		JsonNode responseBody = basicAnilistResponse();

		wireMockServer
				.stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
						.willReturn(aResponse()
								.withStatus(200)
								.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
								.withJsonBody(responseBody))
				);
	}

	@DisplayName("Get Anime by Id")
	@Nested
	class GetAnimeById {
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
		@WithMockAuthentication(authType = KeycloakAuthenticationToken.class)
		public void getAnimeById_LoggedIn_ReturnWithUserInfo() {
			//given
			changeLoggedInUserTo("467a809a-d893-48c2-85e2-82f9ce4b1560");

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
									"467a809a-d893-48c2-85e2-82f9ce4b1560",
									"UserWithData",
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

	private JsonNode basicAnilistResponse() {
		return addElementToCreateGraphQlQueryAnwser(
				mapper.createObjectNode()
						.put("Field1", "value1")
						.set("Field2", mapper.createObjectNode()
								.put("Field2-field1", 1)
								.put("Field2-field2", "value2")
						),
				QueryElements.Media
		);
	}

	private JsonNode addElementToCreateGraphQlQueryAnwser(JsonNode answer, QueryElements element) {
		return mapper.createObjectNode().set("data", mapper.createObjectNode().set(element.name(), answer));
	}
}
