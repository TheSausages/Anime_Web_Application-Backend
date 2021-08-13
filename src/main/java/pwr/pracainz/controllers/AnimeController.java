package pwr.pracainz.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.services.anime.AnimeService;
import pwr.pracainz.services.anime.AnimeServiceInterface;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    private final AnimeServiceInterface animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ObjectNode getAnimeById(@PathVariable int id) {
        return animeService.getAnimeById(id);
    }

    @GetMapping(value = "/season/current")
    @ResponseBody
    public ObjectNode getCurrentSeasonAnime() {
        return animeService.getCurrentSeasonAnime();
    }

    @GetMapping(value = "/ranking/topAllTime/{pageNumber}")
    @ResponseBody
    public ObjectNode getTopAnimeOfAllTime(@PathVariable int pageNumber) {
        return animeService.getTopAnimeAllTime(pageNumber);
    }

    @GetMapping(value = "/ranking/topAiring/{pageNumber}")
    @ResponseBody
    public ObjectNode getTopAiringAnime(@PathVariable int pageNumber) {
        return animeService.getTopAnimeAiring(pageNumber);
    }

    @GetMapping(value = "/ranking/topMovies/{pageNumber}")
    @ResponseBody
    public ObjectNode getTopAnimeMovies(@PathVariable int pageNumber) {
        return animeService.getTopAnimeMovies(pageNumber);
    }
}
