package pwr.pracainz.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.animeInfo.AnimeQuery;
import pwr.pracainz.services.anime.Anime.AnimeServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/anime")
public class AnimeController {
	private final AnimeServiceInterface animeService;

	@Autowired
	public AnimeController(AnimeServiceInterface animeService) {
		this.animeService = animeService;
	}

	@GetMapping(value = "/{id}")
	public ObjectNode getAnimeById(@PathVariable int id, HttpServletRequest request) {
		return animeService.getAnimeById(id, request);
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
}
