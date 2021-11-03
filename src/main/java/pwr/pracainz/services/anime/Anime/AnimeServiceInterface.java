package pwr.pracainz.services.anime.Anime;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pwr.pracainz.DTO.animeInfo.AnimeQuery;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSeason;

import javax.servlet.http.HttpServletRequest;

public interface AnimeServiceInterface {
	ObjectNode getCurrentSeasonAnime(HttpServletRequest request);

	ObjectNode getSeasonAnime(MediaSeason season, int year, HttpServletRequest request);

	ObjectNode getTopAnimeMovies(int pageNumber, HttpServletRequest request);

	ObjectNode getTopAnimeAiring(int pageNumber, HttpServletRequest request);

	ObjectNode getTopAnimeAllTime(int pageNumber, HttpServletRequest request);

	ObjectNode getAnimeById(int id, HttpServletRequest request);

	ObjectNode searchByQuery(AnimeQuery query, int pageNumber, HttpServletRequest request);
}
