package pwr.pracainz.services.anime.Anime;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSeason;

public interface AnimeServiceInterface {
    ObjectNode getCurrentSeasonAnime();

    ObjectNode getSeasonAnime(MediaSeason season, int year);

    ObjectNode getTopAnimeMovies(int pageNumber);

    ObjectNode getTopAnimeAiring(int pageNumber);

    ObjectNode getTopAnimeAllTime(int pageNumber);

    ObjectNode getAnimeById(int id);
}
