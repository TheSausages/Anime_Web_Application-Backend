package pwr.PracaInz.Controllers;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pwr.PracaInz.Entities.LoginCredentials;
import pwr.PracaInz.Services.AnimeService;

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
    public ResponseEntity<JsonObject> getAnimeById(@PathVariable int id) {
        return animeService.getAnimeById(id);
    }

    @GetMapping(value = "/season/current")
    @ResponseBody
    public ResponseEntity<JsonObject> getCurrentSeasonAnime() {
        return animeService.getCurrentSeasonAnime();
    }

    @GetMapping(value = "/season/current/info")
    @ResponseBody
    public JsonObject getCurrentSeasonInformation() {
        return animeService.getCurrentSeasonInformation();
    }

    @GetMapping(value = "/ranking/topAllTime/{pageNumber}")
    @ResponseBody
    public ResponseEntity<JsonObject> getTopAnimeOfAllTime(@PathVariable int pageNumber) {
        return animeService.getTopAnimeAllTime(pageNumber);
    }

    @GetMapping(value = "/ranking/topAiring/{pageNumber}")
    @ResponseBody
    public ResponseEntity<JsonObject> getTopAiringAnime(@PathVariable int pageNumber) {
        return animeService.getTopAnimeAiring(pageNumber);
    }

    @GetMapping(value = "/ranking/topMovie/{pageNumber}")
    @ResponseBody
    public ResponseEntity<JsonObject> getTopAnimeMovies(@PathVariable int pageNumber) {
        return animeService.getTopAnimeMovies(pageNumber);
    }
}
