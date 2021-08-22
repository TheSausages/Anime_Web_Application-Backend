package pwr.pracainz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.services.anime.AnimeUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/animeUser")
public class AnimeUserController {
    private final AnimeUserService animeUserService;

    @Autowired
    AnimeUserController(AnimeUserService animeUserService) {
        this.animeUserService = animeUserService;
    }

    @PostMapping("/updateUserAnime")
    @ResponseBody
    public AnimeUserInfoDTO register(@RequestBody @Valid AnimeUserInfoDTO animeUserInfoDTO) {
        return animeUserService.updateCurrentUserAnimeInfo(animeUserInfoDTO);
    }
}
