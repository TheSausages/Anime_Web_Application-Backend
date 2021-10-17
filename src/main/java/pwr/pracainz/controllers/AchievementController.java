package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pwr.pracainz.services.achievement.AchievementServiceInterface;

@RestController
@RequestMapping("/achievement")
public class AchievementController {
	private final AchievementServiceInterface achievementService;

	@Autowired
	AchievementController(AchievementServiceInterface achievementService) {
		this.achievementService = achievementService;
	}

	@GetMapping(value = "/emitting", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter emitAchievements() {
		return achievementService.achievementEmitter();
	}
}
