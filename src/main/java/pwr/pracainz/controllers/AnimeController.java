package pwr.pracainz.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.animeInfo.AnimeQuery;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.services.anime.Anime.AnimeServiceInterface;
import pwr.pracainz.services.anime.AnimeUser.AnimeUserServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller for request connected to Anime and Anime User Statuses.
 */
@RestControllerWithBasePath("${api.default-path}/anime")
public class AnimeController {
	private final AnimeServiceInterface animeService;
	private final AnimeUserServiceInterface animeUserService;

	@Autowired
	public AnimeController(AnimeServiceInterface animeService, AnimeUserServiceInterface animeUserService) {
		this.animeService = animeService;
		this.animeUserService = animeUserService;
	}

	@GetMapping(value = "/{animeId}")
	public ObjectNode getAnimeById(@PathVariable int animeId, HttpServletRequest request) {
		return animeService.getAnimeById(animeId, request);
	}

	@GetMapping(value = "/season/current")
	public ObjectNode getCurrentSeasonAnime(HttpServletRequest request) {
		return animeService.getCurrentSeasonAnime(request);
	}

	@GetMapping(value = "/ranking/topAllTime/{pageNumber}")
	public ObjectNode getTopAnimeOfAllTime(@PathVariable int pageNumber, HttpServletRequest request) {
		return animeService.getTopAnimeAllTime(pageNumber, request);
	}

	@GetMapping(value = "/ranking/topAiring/{pageNumber}")
	public ObjectNode getTopAiringAnime(@PathVariable int pageNumber, HttpServletRequest request) {
		return animeService.getTopAnimeAiring(pageNumber, request);
	}

	@GetMapping(value = "/ranking/topMovies/{pageNumber}")
	public ObjectNode getTopAnimeMovies(@PathVariable int pageNumber, HttpServletRequest request) {
		return animeService.getTopAnimeMovies(pageNumber, request);
	}

	@PostMapping(value = "/search/{pageNumber}")
	public ObjectNode searchByQuery(@RequestBody @Valid AnimeQuery query, @PathVariable int pageNumber, HttpServletRequest request) {
		return animeService.searchByQuery(query, pageNumber, request);
	}

	@PutMapping("/updateUserAnime")
	public AnimeUserInfoDTO updateAnimeIfo(@RequestBody @Valid AnimeUserInfoDTO animeUserInfoDTO) {
		return animeUserService.updateCurrentUserAnimeInfo(animeUserInfoDTO);
	}
}
