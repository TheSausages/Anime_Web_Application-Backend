package pwr.pracainz.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pwr.pracainz.DTO.SimpleMessageDTO;
import pwr.pracainz.DTO.animeInfo.AnimeDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.DTO.animeInfo.ReviewDTO;
import pwr.pracainz.DTO.forum.ForumCategoryDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.TagDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusIdDTO;
import pwr.pracainz.DTO.user.AchievementDTO;
import pwr.pracainz.DTO.user.CompleteUserDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.DTO.userauthetification.AuthenticationTokenDTO;
import pwr.pracainz.DTO.userauthetification.LoginCredentialsDTO;
import pwr.pracainz.DTO.userauthetification.RefreshTokenDTO;
import pwr.pracainz.DTO.userauthetification.RegistrationBodyDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;
import pwr.pracainz.integrationtests.config.BaseIntegrationTest;
import pwr.pracainz.integrationtests.config.TestConstants;
import pwr.pracainz.integrationtests.config.TestI18nService;
import pwr.pracainz.integrationtests.config.keycloakprincipal.KeycloakPrincipalByUserId;
import pwr.pracainz.services.icon.IconService;
import pwr.pracainz.services.keycloak.KeycloakService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static pwr.pracainz.integrationtests.UserIntegrationTest.UserControllerConfig.mockAuthorizationHeader;

public class UserIntegrationTest extends BaseIntegrationTest {

	@Autowired
	ObjectMapper mapper;

	@SpyBean
	IconService iconService;

	static class TagDTOComparator implements Comparator<TagDTO> {
		@Override
		public int compare(TagDTO o1, TagDTO o2) {
			return Integer.compare(o1.getTagId(), o2.getTagId());
		}
	}

	static class CompletePostDTOComparator implements Comparator<CompletePostDTO> {
		@Override
		public int compare(CompletePostDTO o1, CompletePostDTO o2) {
			return Integer.compare(o1.getPostId(), o2.getPostId());
		}
	}

	//Because we cant use The Authorization header (it overwrites mock authorization),
	//we overwrite the logout endpoint to take a header with another name
	@TestConfiguration
	static class UserControllerConfig {
		static final String mockAuthorizationHeader = "mockAuthorization";

		@Autowired
		KeycloakService keycloakService;

		class MockLogoutController {
			@ResponseBody
			@PostMapping(value = "/api/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
			public SimpleMessageDTO logout(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO, @RequestHeader(mockAuthorizationHeader) String accessToken, HttpServletRequest request) {
				return keycloakService.logout(refreshTokenDTO, accessToken, request);
			}
		}

		@Bean
		MockLogoutController mockLogoutController() {
			return new MockLogoutController();
		}

		@Autowired
		public void registerOverriddenControllerEndpoint(final RequestMappingHandlerMapping requestMappingHandlerMapping,
		                                                 final MockLogoutController controller) throws NoSuchMethodException {
			final RequestMappingInfo mapping = RequestMappingInfo.paths("/api/auth/logout")
					.methods(RequestMethod.POST)
					.build();

			requestMappingHandlerMapping.unregisterMapping(mapping);
			Class<?>[] argTypes = new Class[]{RefreshTokenDTO.class, String.class, HttpServletRequest.class};
			requestMappingHandlerMapping.registerMapping(mapping, controller, MockLogoutController.class.getMethod("logout", argTypes));


		}
	}


	@Nested
	@DisplayName("User Profile Tests")
	class UserProfile {
		@Nested
		@DisplayName("Current User Profile")
		class CurrentUserProfile {
			@Test
			public void currentUserProfile_NotLoggedIn_ThrowErrorDefaultLocale() {
				//given
				SimpleMessageDTO expectedError = new SimpleMessageDTO(
						i18nService.getTranslation(
								"authentication.not-logged-in"
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_USER_PROFILE_ENDPOINT)
						.exchange();

				//then
				SimpleMessageDTO receivedError = spec.expectStatus()
						.isUnauthorized()
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(receivedError, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(expectedError)
				));
			}

			@Test
			public void currentUserProfile_NotLoggedIn_ThrowErrorPolishLocale() {
				//given
				SimpleMessageDTO expectedError = new SimpleMessageDTO(
						i18nService.getTranslation(
								"authentication.not-logged-in",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_USER_PROFILE_ENDPOINT)
						.header("Accept-Language", "pl")
						.exchange();

				//then
				SimpleMessageDTO receivedError = spec.expectStatus()
						.isUnauthorized()
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(receivedError, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(expectedError)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void currentUserProfile_LoggedInWithAchievements_ReturnCurrentUserProfile() throws IOException {
				//given
				SimpleUserDTO expectingSimpleUserProfile = new SimpleUserDTO(
						TestConstants.USER_WITH_DATA_ID,
						TestConstants.USER_WITH_DATA_USERNAME,
						2,
						10,
						25
				);

				CompleteUserDTO expectingUserProfile = new CompleteUserDTO(
						TestConstants.USER_WITH_DATA_ID,
						TestConstants.USER_WITH_DATA_USERNAME,
						2,
						10,
						25,
						Set.of(
								new AchievementDTO(
										1,
										"First Post!",
										"The first is never the last",
										iconService.getAchievementIcon(1),
										15,
										1
								),
								new AchievementDTO(
										4,
										"First Review!",
										"Getting started",
										iconService.getAchievementIcon(4),
										10,
										1
								)
						),
						Set.of(
								new AnimeUserInfoDTO(
										new AnimeUserInfoIdDTO(
												expectingSimpleUserProfile,
												new AnimeDTO(
														2,
														5.0,
														1,
														1
												)
										),
										AnimeUserStatus.COMPLETED,
										LocalDate.of(2021, 10, 1),
										LocalDate.of(2021, 10, 10),
										1,
										true,
										LocalDateTime.of(2021, 10, 10, 0, 0),
										true,
										5,
										new ReviewDTO(
												2,
												"Second Test Review",
												"Second Test Review Text",
												0,
												8,
												1
										)
								),
								new AnimeUserInfoDTO(
										new AnimeUserInfoIdDTO(
												expectingSimpleUserProfile,
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
										new ReviewDTO(
												1,
												"First Test Review",
												"First Test Review Text",
												8,
												3,
												2
										)
								)
						),
						Set.of(
								new ThreadUserStatusDTO(
										new ThreadUserStatusIdDTO(
												expectingSimpleUserProfile,
												new SimpleThreadDTO(
														1,
														"First Thread on the forum!",
														1,
														ThreadStatus.CLOSED,
														LocalDateTime.of(2021, 9, 1, 12, 11, 32),
														LocalDateTime.of(2021, 9 ,2 ,15 ,45 ,40),
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
																new TagDTO(1,
																		"Episode Discussion",
																		TagImportance.LOW,
																		"rgb(0, 183, 255)"),
																new TagDTO(2,
																		"New Studio",
																		TagImportance.MEDIUM,
																		"rgb(255, 112, 112)"),
																new TagDTO(3,
																		"Best Girl",
																		TagImportance.HIGH,
																		"rgb(255, 180, 112)"),
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
						),
						Set.of(
								new SimpleThreadDTO(
										2,
										"Second Thread on the forum!",
										3,
										ThreadStatus.OPEN,
										LocalDateTime.of(2021, 9, 2, 14, 5, 4),
										LocalDateTime.of(2021, 9 ,4, 12, 12, 12),
										expectingSimpleUserProfile,
										new ForumCategoryDTO(
												1,
												"Suggestions",
												"Suggestions for enhancing the site and service"
										),
										List.of(
												new TagDTO(2,
														"New Studio",
														TagImportance.MEDIUM,
														"rgb(255, 112, 112)"),
												new TagDTO(3,
														"Best Girl",
														TagImportance.HIGH,
														"rgb(255, 180, 112)")
										),
										null
								)
						),
						Set.of(
								new CompletePostDTO(
										1,
										"First Post on the forum!",
										false,
										LocalDateTime.of(2021,9,1,14,10,12),
										LocalDateTime.of(2021,9,2,18,30,59),
										expectingSimpleUserProfile,
										"Text of the first post on the forum",
										13,
										16,
										null
								),
								new CompletePostDTO(
										3,
										"Third Post on the forum!",
										true,
										LocalDateTime.of(2021,9,3,16, 45, 1),
										LocalDateTime.of(2021,9,3,16, 45, 1),
										expectingSimpleUserProfile,
										"Text of the third post on the forum",
										0,
										35,
										null
								)
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_CURRENT_USER_PROFILE_ENDPOINT)
						.exchange();

				//then
				CompleteUserDTO receivedUserProfile = spec.expectStatus()
						.isOk()
						.expectBody(CompleteUserDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(receivedUserProfile, allOf(
						notNullValue(),
						instanceOf(CompleteUserDTO.class),
						samePropertyValuesAs(expectingUserProfile, "achievements", "'animeUserInfos", "posts", "threadUserStatuses", "threads")
				));

				assertThat(receivedUserProfile.getAchievements(), allOf(
						notNullValue(),
						containsInAnyOrder(expectingUserProfile.getAchievements().toArray())
				));

				assertThat(receivedUserProfile.getAnimeUserInfos(), allOf(
						notNullValue(),
						containsInAnyOrder(expectingUserProfile.getAnimeUserInfos().toArray())
				));


				//containsInAnyOrder does not work well with inheriting classes
				//Even worse with nested collections
				//Because of this we use assertIterableEquals with sorted lists
				//Beware, each nested list must also be sorted
				//This is not optimal, but at least it works
				assertThat(receivedUserProfile.getThreadUserStatuses(), notNullValue());
				assertIterableEquals(
						receivedUserProfile.getThreadUserStatuses().stream()
								.peek(sts -> sts.getIds().getThread().setTags(
										sts.getIds().getThread().getTags().stream()
												.sorted(new TagDTOComparator()).collect(Collectors.toList())
								))
								.sorted().collect(Collectors.toList()),
						expectingUserProfile.getThreadUserStatuses().stream()
								.peek(sts -> sts.getIds().getThread().setTags(
										sts.getIds().getThread().getTags().stream()
												.sorted(new TagDTOComparator()).collect(Collectors.toList())
								))
								.sorted().collect(Collectors.toList())
				);

				//Same as ThreadUserStatuses
				assertThat(receivedUserProfile.getPosts(), notNullValue());
				assertIterableEquals(
						expectingUserProfile.getPosts().stream()
								.sorted(new CompletePostDTOComparator()).collect(Collectors.toList()),
						receivedUserProfile.getPosts().stream()
								.sorted(new CompletePostDTOComparator()).collect(Collectors.toList())
				);

				//Same as ThreadUserStatuses
				assertThat(receivedUserProfile.getThreads(), notNullValue());
				assertIterableEquals(
						expectingUserProfile.getThreads().stream()
								.peek(thr -> thr.setTags(thr.getTags().stream()
										.sorted(new TagDTOComparator()).collect(Collectors.toList())))
								.sorted().collect(Collectors.toList()),
						receivedUserProfile.getThreads().stream()
								.peek(thr -> thr.setTags(thr.getTags().stream()
										.sorted(new TagDTOComparator()).collect(Collectors.toList())))
								.sorted().collect(Collectors.toList())
				);
			}
		}

		@Nested
		@DisplayName("Other User Profile")
		class OtherUserProfile {
			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
			public void otherUserProfile_LoggedInWithAchievements_ReturnCurrentUserProfile() throws IOException {
				//given
				SimpleUserDTO expectingSimpleUserProfile = new SimpleUserDTO(
						TestConstants.USER_WITH_DATA_ID,
						TestConstants.USER_WITH_DATA_USERNAME,
						2,
						10,
						25
				);

				CompleteUserDTO expectingUserProfile = new CompleteUserDTO(
						TestConstants.USER_WITH_DATA_ID,
						TestConstants.USER_WITH_DATA_USERNAME,
						2,
						10,
						25,
						Set.of(
								new AchievementDTO(
										1,
										"First Post!",
										"The first is never the last",
										iconService.getAchievementIcon(1),
										15,
										1
								),
								new AchievementDTO(
										4,
										"First Review!",
										"Getting started",
										iconService.getAchievementIcon(4),
										10,
										1
								)
						),
						Set.of(
								new AnimeUserInfoDTO(
										new AnimeUserInfoIdDTO(
												expectingSimpleUserProfile,
												new AnimeDTO(
														2,
														5.0,
														1,
														1
												)
										),
										AnimeUserStatus.COMPLETED,
										LocalDate.of(2021, 10, 1),
										LocalDate.of(2021, 10, 10),
										1,
										true,
										LocalDateTime.of(2021, 10, 10, 0, 0),
										true,
										5,
										new ReviewDTO(
												2,
												"Second Test Review",
												"Second Test Review Text",
												0,
												8,
												1
										)
								),
								new AnimeUserInfoDTO(
										new AnimeUserInfoIdDTO(
												expectingSimpleUserProfile,
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
										new ReviewDTO(
												1,
												"First Test Review",
												"First Test Review Text",
												8,
												3,
												2
										)
								)
						),
						Set.of(
								new ThreadUserStatusDTO(
										new ThreadUserStatusIdDTO(
												expectingSimpleUserProfile,
												new SimpleThreadDTO(
														1,
														"First Thread on the forum!",
														1,
														ThreadStatus.CLOSED,
														LocalDateTime.of(2021, 9, 1, 12, 11, 32),
														LocalDateTime.of(2021, 9 ,2 ,15 ,45 ,40),
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
																new TagDTO(1,
																		"Episode Discussion",
																		TagImportance.LOW,
																		"rgb(0, 183, 255)"),
																new TagDTO(2,
																		"New Studio",
																		TagImportance.MEDIUM,
																		"rgb(255, 112, 112)"),
																new TagDTO(3,
																		"Best Girl",
																		TagImportance.HIGH,
																		"rgb(255, 180, 112)"),
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
						),
						Set.of(
								new SimpleThreadDTO(
										2,
										"Second Thread on the forum!",
										3,
										ThreadStatus.OPEN,
										LocalDateTime.of(2021, 9, 2, 14, 5, 4),
										LocalDateTime.of(2021, 9 ,4, 12, 12, 12),
										expectingSimpleUserProfile,
										new ForumCategoryDTO(
												1,
												"Suggestions",
												"Suggestions for enhancing the site and service"
										),
										List.of(
												new TagDTO(2,
														"New Studio",
														TagImportance.MEDIUM,
														"rgb(255, 112, 112)"),
												new TagDTO(3,
														"Best Girl",
														TagImportance.HIGH,
														"rgb(255, 180, 112)")
										),
										null
								)
						),
						Set.of(
								new CompletePostDTO(
										1,
										"First Post on the forum!",
										false,
										LocalDateTime.of(2021,9,1,14,10,12),
										LocalDateTime.of(2021,9,2,18,30,59),
										expectingSimpleUserProfile,
										"Text of the first post on the forum",
										13,
										16,
										null
								),
								new CompletePostDTO(
										3,
										"Third Post on the forum!",
										true,
										LocalDateTime.of(2021,9,3,16, 45, 1),
										LocalDateTime.of(2021,9,3,16, 45, 1),
										expectingSimpleUserProfile,
										"Text of the third post on the forum",
										0,
										35,
										null
								)
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.get()
						.uri(TestConstants.GET_OTHER_USER_PROFILE_ENDPOINT, TestConstants.USER_WITH_DATA_ID)
						.exchange();

				//then
				CompleteUserDTO receivedUserProfile = spec.expectStatus()
						.isOk()
						.expectBody(CompleteUserDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(receivedUserProfile, allOf(
						notNullValue(),
						instanceOf(CompleteUserDTO.class),
						samePropertyValuesAs(expectingUserProfile, "achievements", "'animeUserInfos", "posts", "threadUserStatuses", "threads")
				));

				assertThat(receivedUserProfile.getAchievements(), allOf(
						notNullValue(),
						containsInAnyOrder(expectingUserProfile.getAchievements().toArray())
				));

				assertThat(receivedUserProfile.getAnimeUserInfos(), allOf(
						notNullValue(),
						containsInAnyOrder(expectingUserProfile.getAnimeUserInfos().toArray())
				));


				//containsInAnyOrder does not work well with inheriting classes
				//Even worse with nested collections
				//Because of this we use assertIterableEquals with sorted lists
				//Beware, each nested list must also be sorted
				//This is not optimal, but at least it works
				assertThat(receivedUserProfile.getThreadUserStatuses(), notNullValue());
				assertIterableEquals(
						receivedUserProfile.getThreadUserStatuses().stream()
								.peek(sts -> sts.getIds().getThread().setTags(
										sts.getIds().getThread().getTags().stream()
												.sorted(new TagDTOComparator()).collect(Collectors.toList())
								))
								.sorted().collect(Collectors.toList()),
						expectingUserProfile.getThreadUserStatuses().stream()
								.peek(sts -> sts.getIds().getThread().setTags(
										sts.getIds().getThread().getTags().stream()
												.sorted(new TagDTOComparator()).collect(Collectors.toList())
								))
								.sorted().collect(Collectors.toList())
				);

				//Same as ThreadUserStatuses
				assertThat(receivedUserProfile.getPosts(), notNullValue());
				assertIterableEquals(
						expectingUserProfile.getPosts().stream()
								.sorted(new CompletePostDTOComparator()).collect(Collectors.toList()),
						receivedUserProfile.getPosts().stream()
								.sorted(new CompletePostDTOComparator()).collect(Collectors.toList())
				);

				//Same as ThreadUserStatuses
				assertThat(receivedUserProfile.getThreads(), notNullValue());
				assertIterableEquals(
						expectingUserProfile.getThreads().stream()
								.peek(thr -> thr.setTags(thr.getTags().stream()
										.sorted(new TagDTOComparator()).collect(Collectors.toList())))
								.sorted().collect(Collectors.toList()),
						receivedUserProfile.getThreads().stream()
								.peek(thr -> thr.setTags(thr.getTags().stream()
										.sorted(new TagDTOComparator()).collect(Collectors.toList())))
								.sorted().collect(Collectors.toList())
				);
			}
		}
	}

	@Nested
	@DisplayName("Keycloak Tests")
	class Keycloak {
		@Nested
		@DisplayName("Login")
		class Login {
			@Test
			public void login_CorrectData_ReturnAuthenticationToken() {
				//given + when + then
				logIn(TestConstants.USER_WITH_DATA_USERNAME, TestConstants.USER_WITH_DATA_PASSWORD);
			}

			@Nested
			@DisplayName("With Error")
			@TestInstance(TestInstance.Lifecycle.PER_CLASS)
			class LoginError {
				@Test
				public void login_WrongData_ThrowErrorDefaultLocale() {
					//given
					LoginCredentialsDTO wrongCredentials = new LoginCredentialsDTO(
							TestConstants.USER_WITH_NO_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"authentication.login-not-successful"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.body(BodyInserters.fromValue(wrongCredentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isUnauthorized()
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				public void login_WrongData_ThrowErrorPolishLocale() {
					//given
					LoginCredentialsDTO wrongCredentials = new LoginCredentialsDTO(
							TestConstants.USER_WITH_NO_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"authentication.login-not-successful",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.header("Accept-Language", "pl")
							.body(BodyInserters.fromValue(wrongCredentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isUnauthorized()
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				public void login_BlankUsername_ThrowExceptionDefaultLocale() {
					//given
					LoginCredentialsDTO credentials = new LoginCredentialsDTO(
							"",
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.body(BodyInserters.fromValue(credentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				public void login_BlankUsername_ThrowExceptionPolishLocale() {
					//given
					LoginCredentialsDTO credentials = new LoginCredentialsDTO(
							"",
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.header("Accept-Language", "pl")
							.body(BodyInserters.fromValue(credentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				public void login_UsernameTooShort_ThrowExceptionDefaultLocale() {
					//given
					LoginCredentialsDTO credentials = new LoginCredentialsDTO(
							"Us",
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.body(BodyInserters.fromValue(credentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				public void login_UsernameTooShort_ThrowExceptionPolishLocale() {
					//given
					LoginCredentialsDTO credentials = new LoginCredentialsDTO(
							"Us",
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.header("Accept-Language", "pl")
							.body(BodyInserters.fromValue(credentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				public void login_BlankPassword_ThrowExceptionDefaultLocale() {
					//given
					LoginCredentialsDTO credentials = new LoginCredentialsDTO(
							TestConstants.USER_WITH_DATA_USERNAME,
							""
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.body(BodyInserters.fromValue(credentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				public void login_BlankPassword_ThrowExceptionPolishLocale() {
					//given
					LoginCredentialsDTO credentials = new LoginCredentialsDTO(
							TestConstants.USER_WITH_DATA_USERNAME,
							""
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.header("Accept-Language", "pl")
							.body(BodyInserters.fromValue(credentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@ParameterizedTest(name = "Password with wrong pattern, default locale {index}: {argumentsWithNames}")
				@MethodSource("passwords")
				public void login_PasswordWrongPattern_ThrowExceptionDefaultLocale(String password) {
					//given
					LoginCredentialsDTO credentials = new LoginCredentialsDTO(
							TestConstants.USER_WITH_DATA_USERNAME,
							password
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.body(BodyInserters.fromValue(credentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@ParameterizedTest(name = "Password with wrong pattern, polish locale {index}: {argumentsWithNames}")
				@MethodSource("passwords")
				public void login_PasswordWrongPattern_ThrowExceptionPolishLocale() {
					//given
					LoginCredentialsDTO credentials = new LoginCredentialsDTO(
							TestConstants.USER_WITH_DATA_USERNAME,
							""
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient.post()
							.uri(TestConstants.LOGIN_ENDPOINT)
							.header("Accept-Language", "pl")
							.body(BodyInserters.fromValue(credentials))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				Stream<Arguments> passwords() {
					return Stream.of(
							Arguments.of("pass"),
							Arguments.of("Pass"),
							Arguments.of("Password"),
							Arguments.of("12345"),
							Arguments.of("Pa123"),
							Arguments.of("PasswordWithoutNumber")
					);
				}
			}
		}

		@Nested
		@DisplayName("Logout")
		class Logout {

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void logout_LoggedInCorrectHeaderAndToken_LogoutSuccessfullyDefaultLocal() {
				//given
				AuthenticationTokenDTO tokens = logIn(
						TestConstants.USER_WITH_DATA_USERNAME,
						TestConstants.USER_WITH_DATA_PASSWORD
				);
				String accessToken = createAccessToken(tokens);

				SimpleMessageDTO expectedError = new SimpleMessageDTO(
						i18nService.getTranslation(
								"authentication.logout-was-successful"
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.LOGOUT_ENDPOINT)
						.header(mockAuthorizationHeader, accessToken)
						.body(BodyInserters.fromValue(
								new RefreshTokenDTO(
										tokens.getRefresh_token()
								)
						))
						.exchange();

				//then
				SimpleMessageDTO receivedMessage = spec.expectStatus()
						.isOk()
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(receivedMessage, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(expectedError)
				));
			}

			@Test
			@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
			public void logout_LoggedInCorrectHeaderAndToken_LogoutSuccessfullyPolishLocal() {
				//given
				AuthenticationTokenDTO tokens = logIn(
						TestConstants.USER_WITH_DATA_USERNAME,
						TestConstants.USER_WITH_DATA_PASSWORD
				);
				String accessToken = createAccessToken(tokens);

				SimpleMessageDTO expectedError = new SimpleMessageDTO(
						i18nService.getTranslation(
								"authentication.logout-was-successful",
								TestI18nService.POLISH_LOCALE
						)
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.LOGOUT_ENDPOINT)
						.header(mockAuthorizationHeader, accessToken)
						.header(HttpHeaders.ACCEPT_LANGUAGE, TestI18nService.POLISH_LOCALE.getLanguage())
						.body(BodyInserters.fromValue(
								new RefreshTokenDTO(
										tokens.getRefresh_token()
								)
						))
						.exchange();

				//then
				SimpleMessageDTO receivedMessage = spec.expectStatus()
						.isOk()
						.expectBody(SimpleMessageDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(receivedMessage, allOf(
						notNullValue(),
						instanceOf(SimpleMessageDTO.class),
						equalTo(expectedError)
				));
			}

			@Nested
			@DisplayName("With Error")
			class LogoutWithError {
				@Test
				@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
				public void logout_LoggedInBlankHeaderCorrectToken_LogoutNotSuccessfulDefaultLocal() {
					//given
					AuthenticationTokenDTO tokens = logIn(
							TestConstants.USER_WITH_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);
					String accessToken = "";

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"authentication.credentials-wrong-structure"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.LOGOUT_ENDPOINT)
							.header(mockAuthorizationHeader, accessToken)
							.body(BodyInserters.fromValue(
									new RefreshTokenDTO(
											tokens.getRefresh_token()
									)
							))
							.exchange();

					//then
					SimpleMessageDTO receivedMessage = spec.expectStatus()
							.isUnauthorized()
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedMessage, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
				public void logout_LoggedInBlankHeaderCorrectToken_LogoutNotSuccessfullPolishLocal() {
					//given
					AuthenticationTokenDTO tokens = logIn(
							TestConstants.USER_WITH_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);
					String accessToken = "";

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"authentication.credentials-wrong-structure",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.LOGOUT_ENDPOINT)
							.header(mockAuthorizationHeader, accessToken)
							.header(HttpHeaders.ACCEPT_LANGUAGE, TestI18nService.POLISH_LOCALE.getLanguage())
							.body(BodyInserters.fromValue(
									new RefreshTokenDTO(
											tokens.getRefresh_token()
									)
							))
							.exchange();

					//then
					SimpleMessageDTO receivedMessage = spec.expectStatus()
							.isUnauthorized()
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedMessage, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
				public void logout_LoggedInCorrectHeaderBlankToken_LogoutNotSuccessfulDefaultLocal() {
					//given
					AuthenticationTokenDTO tokens = logIn(
							TestConstants.USER_WITH_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);
					String accessToken = createAccessToken(tokens);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.LOGOUT_ENDPOINT)
							.header(mockAuthorizationHeader, accessToken)
							.body(BodyInserters.fromValue(
									new RefreshTokenDTO(
											""
									)
							))
							.exchange();

					//then
					SimpleMessageDTO receivedMessage = spec.expectStatus()
							.isEqualTo(org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedMessage, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
				public void logout_LoggedInCorrectHeaderBlankToken_LogoutNotSuccessfulPolishLocal() {
					//given
					AuthenticationTokenDTO tokens = logIn(
							TestConstants.USER_WITH_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);
					String accessToken = createAccessToken(tokens);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.LOGOUT_ENDPOINT)
							.header(mockAuthorizationHeader, accessToken)
							.header(HttpHeaders.ACCEPT_LANGUAGE, TestI18nService.POLISH_LOCALE.getLanguage())
							.body(BodyInserters.fromValue(
									new RefreshTokenDTO(
											""
									)
							))
							.exchange();

					//then
					SimpleMessageDTO receivedMessage = spec.expectStatus()
							.isEqualTo(org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedMessage, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}
			}
		}

		@Nested
		@DisplayName("Logout")
		class Registration {
			@Test
			public void register_CorrectData_RegisterCorrectly() {
				//given
				RegistrationBodyDTO registration = new RegistrationBodyDTO(
						"SomeUser",
						"SomePassword123",
						"SomePassword123",
						"someMail@mail.com"
				);

				//when
				WebTestClient.ResponseSpec spec = webTestClient
						.post()
						.uri(TestConstants.REGISTER_ENDPOINT)
						.body(BodyInserters.fromValue(registration))
						.exchange();

				//then
				AuthenticationTokenDTO receivedToken = spec.expectStatus()
						.isOk()
						.expectBody(AuthenticationTokenDTO.class)
						.returnResult()
						.getResponseBody();

				assertThat(receivedToken, allOf(
						notNullValue(),
						instanceOf(AuthenticationTokenDTO.class),
						hasProperty("token_type", allOf(
								notNullValue(),
								equalTo("Bearer")
						))
				));

				//check if it's possible to login after registration
				webTestClient
						.post()
						.uri(TestConstants.LOGIN_ENDPOINT)
						.body(BodyInserters.fromValue(new LoginCredentialsDTO(
								registration.getUsername(),
								registration.getPassword()
						)))
						.exchange()
						.expectStatus().isOk()
						.expectBody(AuthenticationTokenDTO.class);
			}

			@TestInstance(TestInstance.Lifecycle.PER_CLASS)
			@Nested
			@DisplayName("With Errors")
			class RegistrationWithError {
				@ParameterizedTest(name = "Registration, with default locale test {index}, {argumentsWithNames}")
				@MethodSource("registrationBodiesWithError")
				public void registration_UnprocessableRegistrationBody_RegistrationNotSuccessfulDefaultLocal(RegistrationBodyDTO registration) {
					//given
					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.REGISTER_ENDPOINT)
							.body(BodyInserters.fromValue(registration))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@ParameterizedTest(name = "Registration, with polish locale test {index}, {argumentsWithNames}")
				@MethodSource("registrationBodiesWithError")
				public void registration_UnprocessableRegistrationBody_RegistrationNotSuccessfulPolishLocal(RegistrationBodyDTO registration) {
					//given
					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.REGISTER_ENDPOINT)
							.header(HttpHeaders.ACCEPT_LANGUAGE, TestI18nService.POLISH_LOCALE.getLanguage())
							.body(BodyInserters.fromValue(registration))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				Stream<Arguments> registrationBodiesWithError() {
					return Stream.of(
							Arguments.of(new RegistrationBodyDTO(
									"",
									"SomePassword123",
									"SomePassword123",
									"someMail@mail.com"
							)),
							Arguments.of(new RegistrationBodyDTO(
									"SomeUser",
									"",
									"SomePassword123",
									"someMail@mail.com"
							)),
							Arguments.of(new RegistrationBodyDTO(
									"SomeUser",
									"SomePassword123",
									"",
									"someMail@mail.com"
							)),
							Arguments.of(new RegistrationBodyDTO(
									"SomeUser",
									"SomePassword123",
									"SomePassword123",
									""
							)),
							Arguments.of(new RegistrationBodyDTO(
									"SomeUser",
									"SomePassword123",
									"SomeOtherPassword456",
									"someMail@mail.com"
							)),
							Arguments.of(new RegistrationBodyDTO(
									"SomeUser",
									"SomePassword123",
									"SomePassword123",
									"wrongMail"
							)),
							Arguments.of(new RegistrationBodyDTO(
									"a",
									"SomePassword123",
									"SomePassword123",
									"someMail@mail.com"
							)),
							Arguments.of(new RegistrationBodyDTO(
									"SomeUser",
									"a1",
									"a1",
									"someMail@mail.com"
							)),
							Arguments.of(new RegistrationBodyDTO(
									"SomeUser",
									"ASa1",
									"ASa1",
									"someMail@mail.com"
							))
					);
				}
			}

			@Nested
			@DisplayName("Refresh tokens")
			class RefreshTokens {
				@Test
				@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
				public void refreshToken_LoggedIn_RefreshCorrectly() {
					//given
					AuthenticationTokenDTO tokens = logIn(
							TestConstants.USER_WITH_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.REFRESH_TOKENS_ENDPOINT)
							.body(BodyInserters.fromValue(new RefreshTokenDTO(
									tokens.getRefresh_token()
							)))
							.exchange();

					AuthenticationTokenDTO receivedToken = spec.expectStatus()
							.isOk()
							.expectBody(AuthenticationTokenDTO.class)
							.returnResult()
							.getResponseBody();

					//then
					assertThat(receivedToken, allOf(
							notNullValue(),
							instanceOf(AuthenticationTokenDTO.class),
							hasProperty("token_type", allOf(
									notNullValue(),
									equalTo("Bearer")
							))
					));
				}

				@Test
				@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
				public void refreshToken_LoggedInBlankToken_ThrowErrorDefaultLocale() {
					//given
					AuthenticationTokenDTO tokens = logIn(
							TestConstants.USER_WITH_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred"
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.REFRESH_TOKENS_ENDPOINT)
							.body(BodyInserters.fromValue(new RefreshTokenDTO(
									""
							)))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}

				@Test
				@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
				public void refreshToken_LoggedInBlankToken_ThrowErrorPolishLocale() {
					//given
					AuthenticationTokenDTO tokens = logIn(
							TestConstants.USER_WITH_DATA_USERNAME,
							TestConstants.USER_WITH_DATA_PASSWORD
					);

					SimpleMessageDTO expectedError = new SimpleMessageDTO(
							i18nService.getTranslation(
									"general.an-error-occurred",
									TestI18nService.POLISH_LOCALE
							)
					);

					//when
					WebTestClient.ResponseSpec spec = webTestClient
							.post()
							.uri(TestConstants.REFRESH_TOKENS_ENDPOINT)
							.header(HttpHeaders.ACCEPT_LANGUAGE, TestI18nService.POLISH_LOCALE.getLanguage())
							.body(BodyInserters.fromValue(new RefreshTokenDTO(
									""
							)))
							.exchange();

					//then
					SimpleMessageDTO receivedError = spec.expectStatus()
							.isEqualTo(org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY)
							.expectBody(SimpleMessageDTO.class)
							.returnResult()
							.getResponseBody();

					assertThat(receivedError, allOf(
							notNullValue(),
							instanceOf(SimpleMessageDTO.class),
							equalTo(expectedError)
					));
				}
			}
		}

		private String createAccessToken(AuthenticationTokenDTO tokens) {
			return String.format("%s %s", tokens.getToken_type(), tokens.getAccess_token());
		}

		private AuthenticationTokenDTO logIn(String username, String password) {
			//given
			LoginCredentialsDTO credentials = new LoginCredentialsDTO(
					username,
					password
			);

			//when
			WebTestClient.ResponseSpec spec = webTestClient
					.post()
					.uri(TestConstants.LOGIN_ENDPOINT)
					.body(BodyInserters.fromValue(credentials))
					.exchange();

			//then
			AuthenticationTokenDTO receivedToken = spec.expectStatus()
					.isOk()
					.expectBody(AuthenticationTokenDTO.class)
					.returnResult()
					.getResponseBody();

			assertThat(receivedToken, allOf(
					notNullValue(),
					instanceOf(AuthenticationTokenDTO.class),
					hasProperty("token_type", allOf(
							notNullValue(),
							equalTo("Bearer")
					))
			));

			return receivedToken;
		}
	}
}
