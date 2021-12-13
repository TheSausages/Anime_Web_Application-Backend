package pwr.pracainz.integrationtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.*;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;
import pwr.pracainz.integrationtests.config.BaseIntegrationTest;
import pwr.pracainz.integrationtests.config.TestConstants;
import pwr.pracainz.integrationtests.config.keycloakprincipal.KeycloakPrincipalByUserId;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ForumIntegrationTest extends BaseIntegrationTest {
	@Nested
	@DisplayName("Tag tests")
	class Tags {
		@Test
		@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
		public void getTags_LoggedIn_GetAllTests() {
			//given
			List<TagDTO> expectedTags = List.of(
					new TagDTO(1, "Episode Discussion", TagImportance.LOW, "rgb(0, 183, 255)"),
					new TagDTO(2, "New Studio", TagImportance.MEDIUM, "rgb(255, 112, 112)"),
					new TagDTO(3, "Best Girl", TagImportance.HIGH, "rgb(255, 180, 112)"),
					new TagDTO(4, "Best Boy", TagImportance.LOW, "rgb(112, 180, 79)")
			);

			//when
			WebTestClient.ResponseSpec spec = webTestClient
					.get()
					.uri(TestConstants.GET_ALL_TAGS_ENDPOINT)
					.exchange();

			//then
			List<TagDTO> receivedTags = spec.expectStatus()
					.isOk()
					.expectBody(new ParameterizedTypeReference<List<TagDTO>>() {})
					.returnResult()
					.getResponseBody();

			assertThat(receivedTags, allOf(
					notNullValue(),
					instanceOf(List.class),
					containsInAnyOrder(expectedTags.toArray())
			));
		}
	}

	@Nested
	@DisplayName("Categories Test")
	class Categories {
		@Test
		@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
		public void getCategories_LoggedIn_GetAllCategories() {
			//given
			// The categories are sorted alphabetically
			List<ForumCategoryDTO> expectedCategories = Stream.of(
					new ForumCategoryDTO(1, "Suggestions", "Suggestions for enhancing the site and service"),
					new ForumCategoryDTO(2 , "News", "Talks about news from the industry")
			).sorted(Comparator.comparing(ForumCategoryDTO::getCategoryName)).collect(Collectors.toList());

			//when
			WebTestClient.ResponseSpec spec = webTestClient
					.get()
					.uri(TestConstants.GET_ALL_CATEGORIES_ENDPOINT)
					.exchange();

			//then
			List<ForumCategoryDTO> receivedTags = spec.expectStatus()
					.isOk()
					.expectBody(new ParameterizedTypeReference<List<ForumCategoryDTO>>() {})
					.returnResult()
					.getResponseBody();

			assertThat(receivedTags, allOf(
					notNullValue(),
					instanceOf(List.class),
					hasSize(greaterThanOrEqualTo(1)),
					equalTo(expectedCategories)
			));
		}
	}

	@Nested
	@DisplayName("Search Threads test")
	class SearchThreads {
		@TestInstance(TestInstance.Lifecycle.PER_CLASS)
		@Nested
		@DisplayName("Without Errors")
		class SearchThreadsWithoutError {
			@ParameterizedTest(name = "Search using anime query, not logged in test {index}: {argumentsWithNames}")
			@MethodSource("queries")
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void searchThreads_LoggedIn_ReturnWithoutError(
					ForumQuery query,
					Consumer<PageDTO<SimpleThreadDTO>> assertions
					) {
				//given
				//for this given number of test elements, it will always be page 0
				int page = 0;

				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.SEARCH_FORUM_USING_QUERY, page)
						.body(BodyInserters.fromValue(query))
						.exchange();

				//then
				PageDTO<SimpleThreadDTO> threads = spec
						.expectStatus().isOk()
						.expectBody(new ParameterizedTypeReference<PageDTO<SimpleThreadDTO>>() {})
						.returnResult()
						.getResponseBody();

				assertions.accept(threads);
			}

			Stream<Arguments> queries() {
				SimpleUserDTO userDTO = new SimpleUserDTO(
						TestConstants.USER_WITH_DATA_ID,
						TestConstants.USER_WITH_DATA_USERNAME,
						2,
						10,
						25
				);

				SimpleThreadDTO firstThread = new SimpleThreadDTO(
						1, "First Thread on the forum!", 1, ThreadStatus.CLOSED,
						LocalDateTime.of(2021,9,1,12,11,32),
						LocalDateTime.of(2021,9,2,15,45,40),
						new SimpleUserDTO(
								TestConstants.SECOND_FORUM_USER_ID,
								TestConstants.SECOND_FORUM_USER_USERNAME,
								2,
								0,
								0
						),
						new ForumCategoryDTO(
								2,
								"News",
								"Talks about news from the industry"
						),
						List.of(
								new TagDTO(3,
										"Best Girl",
										TagImportance.HIGH,
										"rgb(255, 180, 112)"),
								new TagDTO(2,
										"New Studio",
										TagImportance.MEDIUM,
										"rgb(255, 112, 112)"),
								new TagDTO(1,
										"Episode Discussion",
										TagImportance.LOW,
										"rgb(0, 183, 255)"),
								new TagDTO(4,
										"Best Boy",
										TagImportance.LOW,
										"rgb(112, 180, 79)")
						),
						new ThreadUserStatusDTO(
								new ThreadUserStatusIdDTO(
										userDTO,
										new SimpleThreadDTO(
												1, "First Thread on the forum!", 1, ThreadStatus.CLOSED,
												LocalDateTime.of(2021,9,1,12,11,32),
												LocalDateTime.of(2021,9,2,15,45,40),
												new SimpleUserDTO(
														TestConstants.SECOND_FORUM_USER_ID,
														TestConstants.SECOND_FORUM_USER_USERNAME,
														2,
														0,
														0
												),
												new ForumCategoryDTO(
														2,
														"News",
														"Talks about news from the industry"
												),
												List.of(
														new TagDTO(3,
																"Best Girl",
																TagImportance.HIGH,
																"rgb(255, 180, 112)"),
														new TagDTO(2,
																"New Studio",
																TagImportance.MEDIUM,
																"rgb(255, 112, 112)"),
														new TagDTO(1,
																"Episode Discussion",
																TagImportance.LOW,
																"rgb(0, 183, 255)"),
														new TagDTO(4,
																"Best Boy",
																TagImportance.LOW,
																"rgb(112, 180, 79)")
												),
												null
										)
								),
								false,
								false
						)
				);

				SimpleThreadDTO secondThread = new SimpleThreadDTO(
						2, "Second Thread on the forum!", 3, ThreadStatus.OPEN,
						LocalDateTime.of(2021, 9, 2, 14, 5, 4),
						LocalDateTime.of(2021, 9 ,4, 12, 12, 12),
						userDTO,
						new ForumCategoryDTO(
								1,
								"Suggestions",
								"Suggestions for enhancing the site and service"
						),
						List.of(
								new TagDTO(3,
										"Best Girl",
										TagImportance.HIGH,
										"rgb(255, 180, 112)"),
								new TagDTO(2,
										"New Studio",
										TagImportance.MEDIUM,
										"rgb(255, 112, 112)")
						),
						new ThreadUserStatusDTO(
								new ThreadUserStatusIdDTO(
										userDTO,
										new SimpleThreadDTO(
												2, "Second Thread on the forum!", 3, ThreadStatus.OPEN,
												LocalDateTime.of(2021, 9, 2, 14, 5, 4),
												LocalDateTime.of(2021, 9 ,4, 12, 12, 12),
												userDTO,
												new ForumCategoryDTO(
														1,
														"Suggestions",
														"Suggestions for enhancing the site and service"
												),
												List.of(
														new TagDTO(3,
																"Best Girl",
																TagImportance.HIGH,
																"rgb(255, 180, 112)"),
														new TagDTO(2,
																"New Studio",
																TagImportance.MEDIUM,
																"rgb(255, 112, 112)")
												),
												null
										)
								),
								false,
								false
						)
				);

				PageDTO<SimpleThreadDTO> typicalThreadPage = new PageDTO<>(
						List.of(firstThread, secondThread), 2, 30,
						0, 1, true, false
				);
				PageDTO<SimpleThreadDTO> emptyThreadPage = new PageDTO<>(
						Collections.emptyList(), 0, 30,
						0, 0, true, true
				);

				return Stream.of(
						Arguments.of(
								ForumQuery.builder()
										.category(new ForumCategoryDTO(
												2,
												"News",
												"Talks about news from the industry"
										))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(firstThread))
												)),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.status(ThreadStatus.OPEN)
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(secondThread))
												)),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.title("Thread")
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content"),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(secondThread), equalTo(firstThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.creatorUsername("WithData")
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(secondThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.minCreation(LocalDateTime.of(2021, 9, 1 ,18, 10))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(secondThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.maxCreation(LocalDateTime.of(2021, 9, 1 ,18, 10))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(firstThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.minModification(LocalDateTime.of(2021, 9, 3 ,18, 10))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(secondThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.maxModification(LocalDateTime.of(2021, 9, 3 ,18, 10))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(firstThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.minNrOfPosts(2)
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(secondThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.maxNrOfPosts(2)
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(firstThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.tags(List.of(new TagDTO(2, "New Studio", TagImportance.MEDIUM, "rgb(255, 112, 112)")))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content"),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(secondThread), equalTo(firstThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.tags(List.of(
												new TagDTO(2, "New Studio", TagImportance.MEDIUM, "rgb(255, 112, 112)"),
												new TagDTO(4, "Best Boy", TagImportance.LOW, "rgb(112, 180, 79)")
										))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(firstThread))
												))
										))
						),
						//more complicated queries
						Arguments.of(
								ForumQuery.builder()
										.status(ThreadStatus.CLOSED)
										.minCreation(LocalDateTime.of(2021, 8, 1, 5, 5))
										.creatorUsername("User")
										.tags(List.of(
												new TagDTO(2, "New Studio", TagImportance.MEDIUM, "rgb(255, 112, 112)"),
												new TagDTO(4, "Best Boy", TagImportance.LOW, "rgb(112, 180, 79)")
										))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content", "numberOfElements"),
												hasProperty("numberOfElements", allOf(
														notNullValue(),
														instanceOf(int.class),
														equalTo(1)
												)),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(firstThread))
												))
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.creatorUsername("User")
										.title("Thread")
										.minNrOfPosts(0)
										.maxNrOfPosts(15)
										.maxModification(LocalDateTime.of(2021, 10, 10, 6, 9))
										.tags(List.of(new TagDTO(2, "New Studio", TagImportance.MEDIUM, "rgb(255, 112, 112)")))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												samePropertyValuesAs(typicalThreadPage, "content"),
												hasProperty("content", allOf(
														notNullValue(),
														instanceOf(List.class),
														containsInAnyOrder(equalTo(secondThread), equalTo(firstThread))
												))
										))
						),
						//none should be returned
						Arguments.of(
								ForumQuery.builder()
										.minNrOfPosts(15)
										.maxNrOfPosts(0)
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												equalTo(emptyThreadPage)
										))
						),
						Arguments.of(
								ForumQuery.builder()
										.maxModification(LocalDateTime.of(2021, 9, 4, 10, 10))
										.minCreation(LocalDateTime.of(2021, 10, 5, 20, 20))
										.build(),
								(Consumer<PageDTO<SimpleThreadDTO>>) (PageDTO<SimpleThreadDTO> threads) ->
										assertThat(threads, allOf(
												notNullValue(),
												instanceOf(PageDTO.class),
												equalTo(emptyThreadPage)
										))
						)
				);
			}
		}
	}
}
