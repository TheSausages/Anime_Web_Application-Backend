package pwr.pracainz.integrationtests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pwr.pracainz.DTO.animeInfo.AnimeDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.DTO.animeInfo.ReviewDTO;
import pwr.pracainz.DTO.forum.Post.CreatePostDTO;
import pwr.pracainz.DTO.user.AchievementDTO;
import pwr.pracainz.DTO.user.SimpleUserDTO;
import pwr.pracainz.configuration.factories.sseemitter.SseEmitterFactoryInterface;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;
import pwr.pracainz.entities.databaseerntities.user.Achievement;
import pwr.pracainz.integrationtests.config.BaseIntegrationTest;
import pwr.pracainz.integrationtests.config.TestConstants;
import pwr.pracainz.integrationtests.config.keycloakprincipal.KeycloakPrincipalByUserId;
import pwr.pracainz.services.achievement.AchievementService;
import pwr.pracainz.services.icon.IconService;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.doThrow;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AchievementIntegrationTest extends BaseIntegrationTest {
	@Autowired
	private AchievementService achievementService;

	@Autowired
	@Qualifier("AsyncTestExec")
	DelegatingSecurityContextAsyncTaskExecutor executor;

	@SpyBean
	IconService iconService;

	@TestConfiguration
	static class DefaultTestConfig {
		@Bean(name = "AsyncTestExec")
		DelegatingSecurityContextAsyncTaskExecutor getAsyncExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setMaxPoolSize(10);
			executor.initialize();

			//In order to access which user is logged in during Async
			return new DelegatingSecurityContextAsyncTaskExecutor(executor);
		}

		/**
		 * Small SseEmitter subclass, that can be closed many times
		 * (need to close for request, and them by subscription canceling)
		 */
		static class MiniSseEmitter extends SseEmitter {
			private boolean isCompleted = false;

			MiniSseEmitter(long timeout) {
				super(timeout);
			}

			@Override
			public synchronized void complete() {
				if (!isCompleted) {
					this.isCompleted = true;
					super.complete();
				}
			}
		}

		@Primary
		@Component
		public class TestSseEmitterFactory implements SseEmitterFactoryInterface {
			@Autowired
			@Qualifier("AsyncTestExec")
			DelegatingSecurityContextAsyncTaskExecutor executor;

			@Override
			public SseEmitter createNewSseEmitter() {
				MiniSseEmitter emitter = new DefaultTestConfig.MiniSseEmitter(2000L);

				//Complete the emitter to unlock the request
				executor.execute(
						emitter::complete,
						1950
				);

				return emitter;
			}
		}
	}

	@Test
	@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
	public void achievementEmission_LoggedInSimpleSubscribeAndCancel_ReturnCreatedSseEmitter() {
		//given

		//when
		webTestClient
				.get()
				.uri(TestConstants.SUBSCRIBE_TO_ACHIEVEMENTS_ENDPOINT)
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus().isOk();

		//then
		Map<String, SseEmitter> emitterMap = getSseEmitters();

		assertThat(emitterMap, allOf(
				notNullValue(),
				aMapWithSize(1),
				hasKey(TestConstants.USER_WITH_DATA_ID)
		));

		//Clean Up
		cancelSubscription();
	}

	@Nested
	@DisplayName("Check if same SseEmitter is returned when same user calls subscribe many times, then cancel")
			//We don't clean the subscription here, but if others pass this would too
	class MultipleSubscribeAndCancel {
		String sseEmitterInstance = null;

		@RepeatedTest(5)
		@KeycloakPrincipalByUserId(TestConstants.USER_WITH_DATA_ID)
		public void achievementEmission_LoggedInSubscribeMultipleTimes_ReturnSameSseEmitter() {
			//given

			//when
			webTestClient
					.get()
					.uri(TestConstants.SUBSCRIBE_TO_ACHIEVEMENTS_ENDPOINT)
					.accept(MediaType.TEXT_EVENT_STREAM)
					.exchange()
					.expectStatus().isOk();

			//then
			Map<String, SseEmitter> emitterMap = getSseEmitters();

			assertThat(emitterMap, allOf(
					notNullValue(),
					aMapWithSize(1),
					hasKey(TestConstants.USER_WITH_DATA_ID)
			));

			if (Objects.isNull(sseEmitterInstance)) sseEmitterInstance = emitterMap.get(TestConstants.USER_WITH_DATA_ID).toString();

			assertThat(emitterMap.get(TestConstants.USER_WITH_DATA_ID).toString(), equalTo(sseEmitterInstance));
		}
	}

	@Test
	@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
	public void achievementEmission_LoggedInEmitSingleAchievement_EmitAchievementWithoutError() throws IOException {
		//given
		schedulePostNumberAchievementEmission(100);

		AchievementDTO expectedAchievement = new AchievementDTO(
				1,
				"First Post!",
				"The first is never the last",
				iconService.getAchievementIcon(1),
				15,
				1
		);

		//when
		FluxExchangeResult<AchievementDTO> achievementResult = webTestClient
				.get()
				.uri(TestConstants.SUBSCRIBE_TO_ACHIEVEMENTS_ENDPOINT)
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus().isOk()
				.returnResult(AchievementDTO.class);

		//then
		Map<String, SseEmitter> emitterMap = getSseEmitters();

		assertThat(emitterMap, allOf(
				notNullValue(),
				aMapWithSize(1),
				hasKey(TestConstants.USER_WITH_NO_DATA_ID)
		));

		StepVerifier.create(achievementResult.getResponseBody())
				.expectNext(expectedAchievement)
				.thenCancel()
				.verify();

		//Clean Up
		cancelSubscription();
	}

	@Test
	@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
	public void achievementEmission_LoggedInEmitMultipleAchievements_EmitAchievementsWithoutError() throws IOException {
		//given
		schedulePostNumberAchievementEmission(100);
		scheduleReviewNumberAchievementEmission(
				200,
				TestConstants.USER_WITH_NO_DATA_ID,
				TestConstants.USER_WITH_NO_DATA_USERNAME
		);

		AchievementDTO expectedPostAchievement = new AchievementDTO(
				1,
				"First Post!",
				"The first is never the last",
				iconService.getAchievementIcon(1),
				15,
				1
		);

		AchievementDTO expectedReviewAchievement = new AchievementDTO(
				4,
				"First Review!",
				"Getting started",
				iconService.getAchievementIcon(4),
				10,
				1
		);

		//when
		FluxExchangeResult<AchievementDTO> achievementResult = webTestClient
				.get()
				.uri(TestConstants.SUBSCRIBE_TO_ACHIEVEMENTS_ENDPOINT)
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus().isOk()
				.returnResult(AchievementDTO.class);

		//then
		Map<String, SseEmitter> emitterMap = getSseEmitters();

		assertThat(emitterMap, allOf(
				notNullValue(),
				aMapWithSize(1),
				hasKey(TestConstants.USER_WITH_NO_DATA_ID)
		));

		StepVerifier.create(achievementResult.getResponseBody())
				.expectNext(expectedPostAchievement)
				.expectNext(expectedReviewAchievement)
				.thenCancel()
				.verify();

		//Clean Up
		cancelSubscription();
	}

	@Test
	@KeycloakPrincipalByUserId(TestConstants.USER_WITH_NO_DATA_ID)
	public void achievementEmission_LoggedInErrorDuringEmission_ThrowError() throws IOException {
		//given
		schedulePostNumberAchievementEmission(5000);
		doThrow(new IOException()).when(iconService).getAchievementIcon(ArgumentMatchers.any(Achievement.class));

		//when
		webTestClient
				.get()
				.uri(TestConstants.SUBSCRIBE_TO_ACHIEVEMENTS_ENDPOINT)
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

		//then
		Map<String, SseEmitter> emitterMap = getSseEmitters();

		assertThat(emitterMap, allOf(
				notNullValue(),
				anEmptyMap()
		));

		//Clean Up
		cancelSubscription();
	}

	private void cancelSubscription() {
		//given

		//when
		webTestClient
				.get()
				.uri(TestConstants.CANCEL_ACHIEVEMENTS_SUBSCRIPTION_ENDPOINT)
				.exchange()
				.expectStatus().isOk();

		//then
		Map<String, SseEmitter> emitterMap = getSseEmitters();

		assertThat(emitterMap, allOf(
				notNullValue(),
				anEmptyMap()
		));
	}

	private void schedulePostNumberAchievementEmission(int delay) {
		int threadId = 2;
		CreatePostDTO createdPost = new CreatePostDTO(
				"Title",
				"Text"
		);

		executor.execute(() ->webTestClient
					.post()
					.uri(TestConstants.POST_POST_FOR_THREAD_ENDPOINT, threadId)
					.body(BodyInserters.fromValue(createdPost))
					.exchange()
					.expectStatus().isOk(),
				delay);
	}

	private void scheduleReviewNumberAchievementEmission(int delay, String userId, String username) {
		AnimeUserInfoDTO animeUserInfoDTO = new AnimeUserInfoDTO(
				new AnimeUserInfoIdDTO(
						new SimpleUserDTO(
								userId,
								username,
								0,
								0,
								0
						),
						new AnimeDTO(
								1,
								0,
								0,
								0
						)
				),
				AnimeUserStatus.NO_STATUS,
				null,
				null,
				0, false,
				null,
				true,
				null,
				new ReviewDTO(
						10,
						"Title",
						"Text",
						0,
						0,
						0
				)
		);

		executor.execute(() ->webTestClient
						.put()
						.uri(TestConstants.PUT_USER_ANIME_INFO_ENDPOINT)
						.body(BodyInserters.fromValue(animeUserInfoDTO))
						.exchange()
						.expectStatus().isOk(),
				delay);
	}

	@SuppressWarnings("unchecked")
	private Map<String, SseEmitter> getSseEmitters() {
		return (Map<String, SseEmitter>) ReflectionTestUtils.getField(achievementService, "emitterMap");
	}
}
