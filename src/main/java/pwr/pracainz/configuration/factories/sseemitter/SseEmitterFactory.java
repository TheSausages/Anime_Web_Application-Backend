package pwr.pracainz.configuration.factories.sseemitter;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Log4j2
@Component
public class SseEmitterFactory implements SseEmitterFactoryInterface {
	@Override
	public SseEmitter createNewSseEmitter() {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		emitter.onCompletion(() -> log.info("Achievement emitter ended"));
		emitter.onTimeout(() -> log.info("Achievement sending timeout"));

		return emitter;
	}
}
