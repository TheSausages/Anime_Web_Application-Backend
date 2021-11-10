package pwr.pracainz.configuration.configuration;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configure the App to enable asynchronous operations. It's configured to remember the security data from the request.
 * Additional configurations:
 * <ul>
 *     <li>Max pool size: 10</li>
 *     <li>There is no {@link AsyncUncaughtExceptionHandler} configured</li>
 * </ul>
 *
 * @see pwr.pracainz.achievementlisteners.AchievementListener
 */
@EnableAsync
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(10);
		executor.initialize();

		//In order to access which user is logged in during Async
		return new DelegatingSecurityContextAsyncTaskExecutor(executor);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}
}
