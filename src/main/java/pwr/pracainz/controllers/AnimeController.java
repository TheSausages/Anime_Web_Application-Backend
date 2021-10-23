package pwr.pracainz.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.animeInfo.AnimeQuery;
import pwr.pracainz.services.anime.Anime.AnimeServiceInterface;

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
	public ObjectNode getAnimeById(@PathVariable int id) {
		return animeService.getAnimeById(id);
	}

	@GetMapping(value = "/season/current")
	public ObjectNode getCurrentSeasonAnime() {
		return animeService.getCurrentSeasonAnime();
	}

	@GetMapping(value = "/ranking/topAllTime/{pageNumber}")
	public ObjectNode getTopAnimeOfAllTime(@PathVariable int pageNumber) {
		return animeService.getTopAnimeAllTime(pageNumber);
	}

	@GetMapping(value = "/ranking/topAiring/{pageNumber}")
	public ObjectNode getTopAiringAnime(@PathVariable int pageNumber) {
		return animeService.getTopAnimeAiring(pageNumber);
	}

	@GetMapping(value = "/ranking/topMovies/{pageNumber}")
	public ObjectNode getTopAnimeMovies(@PathVariable int pageNumber) {
		return animeService.getTopAnimeMovies(pageNumber);
	}

	@PostMapping(value = "/search/{pageNumber}")
	public ObjectNode searchByQuery(@RequestBody @Valid AnimeQuery query, @PathVariable int pageNumber) {
		return animeService.searchByQuery(query, pageNumber);
	}
}
