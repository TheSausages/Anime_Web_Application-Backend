package pwr.pracainz.configuration.factories.sseemitter;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterFactoryInterface {
	SseEmitter createNewSseEmitter();
}
