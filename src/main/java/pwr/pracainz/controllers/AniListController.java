package pwr.pracainz.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.services.AnimeService;

@RestController
@RequestMapping("/anime")
public class AniListController {
    private final AnimeService animeService;

    @Autowired
    public AniListController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ObjectNode> getAnimeById(@PathVariable int id) {
        return animeService.getAnimeById(id);
    }

    @GetMapping(value = "/season/current")
    @ResponseBody
    public ResponseEntity<ObjectNode> getCurrentSeasonAnime() {
        return animeService.getCurrentSeasonAnime();
    }

    @GetMapping(value = "/season/current/info")
    @ResponseBody
    public ObjectNode getCurrentSeasonInformation() {
        return animeService.getCurrentSeasonInformation();
    }

    @GetMapping(value = "/ranking/topAllTime/{pageNumber}")
    @ResponseBody
    public ResponseEntity<ObjectNode> getTopAnimeOfAllTime(@PathVariable int pageNumber) {
        return animeService.getTopAnimeAllTime(pageNumber);
    }

    @GetMapping(value = "/ranking/topAiring/{pageNumber}")
    @ResponseBody
    public ResponseEntity<ObjectNode> getTopAiringAnime(@PathVariable int pageNumber) {
        return animeService.getTopAnimeAiring(pageNumber);
    }

    @GetMapping(value = "/ranking/topMovies/{pageNumber}")
    @ResponseBody
    public ResponseEntity<ObjectNode> getTopAnimeMovies(@PathVariable int pageNumber) {
        return animeService.getTopAnimeMovies(pageNumber);
    }
}
