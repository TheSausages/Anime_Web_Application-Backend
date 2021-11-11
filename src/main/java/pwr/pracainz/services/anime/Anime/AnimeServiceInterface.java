package pwr.pracainz.services.anime.Anime;

import com.fasterxml.jackson.databind.node.ObjectNode;
import pwr.pracainz.DTO.animeInfo.AnimeQuery;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSeason;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface for an Anime Service. Each implementation must use this interface.
 * <p>
 * The anilist api information are available at:
 * <ul>
 *     <li>Api documentation: <a href="https://anilist.github.io/ApiV2-GraphQL-Docs/">https://anilist.github.io/ApiV2-GraphQL-Docs/</a></li>
 *     <li>Query tester: <a href="https://anilist.co/graphiql">https://anilist.co/graphiql</a></li>
 * </ul>
 *
 * The <i>request</i> parameter for each method is used to retrieve the {@link org.springframework.http.HttpHeaders#ACCEPT_LANGUAGE}
 * header from the request for a {@link pwr.pracainz.services.i18n.I18nServiceInterface} implementation to use.
 */
public interface AnimeServiceInterface {
	/**
	 * Retrieve the current season Anime.
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return The current season Anime in an Anilist Query JSON tree
	 */
	ObjectNode getCurrentSeasonAnime(HttpServletRequest request);

	/**
	 * Retrieve anime for a given season in a given year.
	 * @param season A given anime media season
	 * @param year a given year
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return Anime for a given season in a given year in an Anilist Query JSON tree
	 */
	ObjectNode getSeasonAnime(MediaSeason season, int year, HttpServletRequest request);

	/**
	 * Retrieve a page from the Top Anime Movies ranking.
	 * @param pageNumber Which page should be retrieved from the ranking
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return Given page of Anime from the ranking in an Anilist Query JSON tree
	 */
	ObjectNode getTopAnimeMovies(int pageNumber, HttpServletRequest request);

	/**
	 * Retrieve a page from the Top Airing Anime ranking.
	 * @param pageNumber Which page should be retrieved from the ranking
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return Given page of Anime from the ranking in an Anilist Query JSON tree
	 */
	ObjectNode getTopAnimeAiring(int pageNumber, HttpServletRequest request);

	/**
	 * Retrieve a page from the Top Anime of All Time ranking.
	 * @param pageNumber Which page should be retrieved from the ranking
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return Given page of Anime from the ranking in an Anilist Query JSON tree
	 */
	ObjectNode getTopAnimeAllTime(int pageNumber, HttpServletRequest request);

	/**
	 * Retrieve detailed Anime information using the id.
	 * @param id Anilist anime id used to get detailed information
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return Detailed information about an Anime in an Anilist Query JSON tree
	 */
	ObjectNode getAnimeById(int id, HttpServletRequest request);

	/**
	 * Get a page from the list of Anime that meet the {@link AnimeQuery} query conditions.
	 * @param query Query used to search the anime list
	 * @param pageNumber Which page should be retrieved from the Anime list
	 * @param request {@link pwr.pracainz.services.i18n.I18nServiceInterface#getTranslation(String, HttpServletRequest, Object...)}
	 * @return Given page of Anime from the list in an Anilist Query JSON tree
	 */
	ObjectNode searchByQuery(AnimeQuery query, int pageNumber, HttpServletRequest request);
}
