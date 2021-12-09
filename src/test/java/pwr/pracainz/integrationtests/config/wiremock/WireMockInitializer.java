package pwr.pracainz.integrationtests.config.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.Getter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

@Getter
public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	@Override
	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
		if (!configurableApplicationContext.getEnvironment().containsProperty("wire-mock.port")) {
			throw new BeanInitializationException("Test configuration must have wire-mock.port property");
		}

		@SuppressWarnings("ConstantConditions")
		int wireMockPort = configurableApplicationContext.getEnvironment().getProperty("wire-mock.port", int.class);
		WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
				.port(wireMockPort)
				.extensions(
						WireMockExtensionSummarizer.getInstance(),
						WireMockAnimeSearchExtension.getInstance(),
						WireMockPageExtension.getInstance()
				)
				.notifier(new ConsoleNotifier(true))
		);

		wireMockServer.start();
		configurableApplicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

		configurableApplicationContext.addApplicationListener(applicationEvent -> {
			if (applicationEvent instanceof ContextClosedEvent) {
				wireMockServer.stop();
			}
		});

	}

}
