package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.services.anime.AnimeUser.AnimeUserServiceInterface;

import javax.validation.Valid;

@RestController
@RequestMapping("/animeUser")
public class AnimeUserController {
	private final AnimeUserServiceInterface animeUserService;

	@Autowired
	AnimeUserController(AnimeUserServiceInterface animeUserService) {
		this.animeUserService = animeUserService;
	}

	@PostMapping("/updateUserAnime")
	public AnimeUserInfoDTO updateAnimeIfo(@RequestBody @Valid AnimeUserInfoDTO animeUserInfoDTO) {
		return animeUserService.updateCurrentUserAnimeInfo(animeUserInfoDTO);
	}
}
