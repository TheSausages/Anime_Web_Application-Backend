package pwr.PracaInz.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String getAnimeById(@PathVariable int id) {
        return animeService.getAnimeById(id);
    }

    @GetMapping(value = "/season/current")
    @ResponseBody
    public String getSeasonAnime() {
        return animeService.getCurrentSeasonAnime();
    }

    @GetMapping(value = "/season/current/info")
    @ResponseBody
    public String getCurrentSeasonInformation() {
        return animeService.getCurrentSeasonInformation();
    }
}
