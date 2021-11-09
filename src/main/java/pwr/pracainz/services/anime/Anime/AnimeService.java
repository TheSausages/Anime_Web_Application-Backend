package pwr.pracainz.services.anime.Anime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.pracainz.DTO.animeInfo.AnimeQuery;
import pwr.pracainz.entities.anime.query.Query;
import pwr.pracainz.entities.anime.query.parameters.FieldParameters;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;
import pwr.pracainz.entities.anime.query.parameters.connections.charackters.Character;
import pwr.pracainz.entities.anime.query.parameters.connections.charackters.*;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaConnection;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaEdge;
import pwr.pracainz.entities.anime.query.parameters.connections.staff.Staff;
import pwr.pracainz.entities.anime.query.parameters.connections.staff.StaffArguments;
import pwr.pracainz.entities.anime.query.parameters.connections.staff.StaffConnection;
import pwr.pracainz.entities.anime.query.parameters.connections.staff.StaffSort;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateField;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateValue;
import pwr.pracainz.entities.anime.query.parameters.media.*;
import pwr.pracainz.entities.anime.query.queryElements.Media.Field;
import pwr.pracainz.entities.anime.query.queryElements.Media.Media;
import pwr.pracainz.entities.anime.query.queryElements.Page.Page;
import pwr.pracainz.entities.anime.query.queryElements.QueryElements;
import pwr.pracainz.entities.databaseerntities.animeInfo.Anime;
import pwr.pracainz.exceptions.exceptions.AnilistException;
import pwr.pracainz.repositories.animeInfo.AnimeRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.anime.AnimeUser.AnimeUserServiceInterface;
import pwr.pracainz.services.i18n.I18nServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

@Log4j2
@Service
public class AnimeService implements AnimeServiceInterface {
	private final AnimeRepository animeRepository;
	private final AnimeUserServiceInterface animeUserService;
	private final I18nServiceInterface i18nService;
	private final DTOConversionInterface dtoConversion;
	private final WebClient client;
	private final ObjectMapper mapper;

	AnimeService(I18nServiceInterface i18nService,
	             AnimeRepository animeRepository,
	             AnimeUserServiceInterface animeUserService,
	             DTOConversionInterface dtoConversion,
	             ObjectMapper mapper,
	             @Qualifier("anilistWebClient") WebClient anilistWenClient) {
		this.animeRepository = animeRepository;
		this.animeUserService = animeUserService;
		this.i18nService = i18nService;
		this.dtoConversion = dtoConversion;
		this.mapper = mapper;
		this.client = anilistWenClient;
	}

	@Override
	public ObjectNode getCurrentSeasonAnime(HttpServletRequest request) {
		Field field = Field.getFieldBuilder()
				.parameter(FieldParameters.id)
				.title(MediaTitle.getMediaTitleBuilder()
						.englishLanguage()
						.nativeLanguage()
						.romajiLanguage()
						.buildMediaTitle())
				.coverImage()
				.buildField();

		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.total()
				.currentPage()
				.lastPage()
				.hasNextPage()
				.perPage()
				.buildPageInfo();

		Page page = Page.getPageBuilder(1, 40)
				.pageInfo(pageInfo)
				.media(Media.getMediaBuilder(field)
						.seasonYear(LocalDateTime.now().getYear())
						.season(MediaSeason.getCurrentSeason())
						.type(pwr.pracainz.entities.anime.query.parameters.media.MediaType.ANIME)
						.sort(new MediaSort[]{MediaSort.POPULARITY_DESC})
						.buildMedia())
				.buildPage();

		return client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Query.fromQueryElement(page))
				.retrieve()
				.bodyToMono(ObjectNode.class)
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res, "currentSeason",
						getCurrentSeasonInformation(), "Successfully got Current Season Information and Anime"))
				.block();
	}

	@Override
	public ObjectNode getSeasonAnime(MediaSeason season, int year, HttpServletRequest request) {
		Field field = Field.getFieldBuilder()
				.parameter(FieldParameters.id)
				.title(MediaTitle.getMediaTitleBuilder()
						.englishLanguage()
						.romajiLanguage()
						.buildMediaTitle())
				.coverImage()
				.buildField();

		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.total()
				.currentPage()
				.lastPage()
				.hasNextPage()
				.perPage()
				.buildPageInfo();

		Page page = Page.getPageBuilder(1, 40)
				.pageInfo(pageInfo)
				.media(Media.getMediaBuilder(field)
						.seasonYear(year)
						.season(season)
						.type(pwr.pracainz.entities.anime.query.parameters.media.MediaType.ANIME)
						.buildMedia())
				.buildPage();

		return client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Query.fromQueryElement(page))
				.retrieve()
				.bodyToMono(ObjectNode.class)
				.flatMap(res -> evaluateClientResponse(QueryElements.Media, res,
						String.format("Successfully got Anime from %s of %s", season, year)))
				.block();
	}

	@Override
	public ObjectNode getTopAnimeMovies(int pageNumber, HttpServletRequest request) {
		Field field = Field.getFieldBuilder()
				.parameter(FieldParameters.id)
				.coverImage()
				.title(MediaTitle.getMediaTitleBuilder()
						.englishLanguage()
						.nativeLanguage()
						.romajiLanguage()
						.buildMediaTitle())
				.buildField();

		Page page = Page.getPageBuilder(pageNumber, 49)
				.pageInfo(PageInfo.getPageInfoBuilder()
						.currentPage()
						.lastPage()
						.total()
						.hasNextPage()
						.buildPageInfo())
				.media(Media.getMediaBuilder(field)
						.type(pwr.pracainz.entities.anime.query.parameters.media.MediaType.ANIME)
						.format(MediaFormat.MOVIE)
						.sort(new MediaSort[]{MediaSort.SCORE_DESC}) //score desc gives highest score for some reason
						.buildMedia())
				.buildPage();

		return client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Query.fromQueryElement(page))
				.retrieve()
				.bodyToMono(ObjectNode.class)
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res,
						String.format("Successfully got %s Page of Top Anime Movies", pageNumber)))
				.block();
	}

	@Override
	public ObjectNode getTopAnimeAiring(int pageNumber, HttpServletRequest request) {
		Field field = Field.getFieldBuilder()
				.parameter(FieldParameters.id)
				.coverImage()
				.title(MediaTitle.getMediaTitleBuilder()
						.englishLanguage()
						.nativeLanguage()
						.romajiLanguage()
						.buildMediaTitle())
				.buildField();

		Page page = Page.getPageBuilder(pageNumber, 49)
				.pageInfo(PageInfo.getPageInfoBuilder()
						.currentPage()
						.lastPage()
						.total()
						.hasNextPage()
						.buildPageInfo())
				.media(Media.getMediaBuilder(field)
						.type(pwr.pracainz.entities.anime.query.parameters.media.MediaType.ANIME)
						.status(MediaStatus.RELEASING)
						.sort(new MediaSort[]{MediaSort.SCORE_DESC}) //score desc gives highest score for some reason
						.buildMedia())
				.buildPage();

		return client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Query.fromQueryElement(page))
				.retrieve()
				.bodyToMono(ObjectNode.class)
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res,
						String.format("Successfully got %s Page of Top Airing Anime", pageNumber)))
				.block();
	}

	@Override
	public ObjectNode getTopAnimeAllTime(int pageNumber, HttpServletRequest request) {
		Field field = Field.getFieldBuilder()
				.parameter(FieldParameters.id)
				.coverImage()
				.title(MediaTitle.getMediaTitleBuilder()
						.englishLanguage()
						.nativeLanguage()
						.romajiLanguage()
						.buildMediaTitle())
				.buildField();

		Page page = Page.getPageBuilder(pageNumber, 49)
				.pageInfo(PageInfo.getPageInfoBuilder()
						.currentPage()
						.total()
						.lastPage()
						.hasNextPage()
						.buildPageInfo())
				.media(Media.getMediaBuilder(field)
						.type(pwr.pracainz.entities.anime.query.parameters.media.MediaType.ANIME)
						.sort(new MediaSort[]{MediaSort.SCORE_DESC}) //score desc gives highest score for some reason
						.buildMedia())
				.buildPage();

		return client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Query.fromQueryElement(page))
				.retrieve()
				.bodyToMono(ObjectNode.class)
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res,
						String.format("Successfully got %s Page of Top Anime of All Time", pageNumber)))
				.block();
	}

	@Override
	public ObjectNode searchByQuery(AnimeQuery query, int pageNumber, HttpServletRequest request) {
		Field field = Field.getFieldBuilder()
				.parameter(FieldParameters.id)
				.coverImage()
				.title(MediaTitle.getMediaTitleBuilder()
						.englishLanguage()
						.nativeLanguage()
						.romajiLanguage()
						.buildMediaTitle())
				.buildField();

		Media.MediaBuilder mediaBuilder = Media.getMediaBuilder(field);

		if (Objects.nonNull(query.getTitle()) && !query.getTitle().isEmpty()) {
			mediaBuilder.search(query.getTitle());
		}

		if (Objects.nonNull(query.getMaxStartDate())) {
			FuzzyDateValue date = FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(query.getMaxStartDate())
					.buildFuzzyDateValue();

			mediaBuilder.startDateLesser(date);
		}

		if (Objects.nonNull(query.getMinStartDate())) {
			FuzzyDateValue date = FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(query.getMinStartDate())
					.buildFuzzyDateValue();

			mediaBuilder.startDateGreater(date);
		}

		if (Objects.nonNull(query.getMaxEndDate())) {
			FuzzyDateValue date = FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(query.getMaxEndDate())
					.buildFuzzyDateValue();

			mediaBuilder.endDateLesser(date);
		}

		if (Objects.nonNull(query.getMinEndDate())) {
			FuzzyDateValue date = FuzzyDateValue.getFuzzyDateValueBuilder()
					.fromDate(query.getMinEndDate())
					.buildFuzzyDateValue();

			mediaBuilder.endDateGreater(date);
		}

		if (Objects.nonNull(query.getMaxNrOfEpisodes())) {
			mediaBuilder.episodesLesser(query.getMaxNrOfEpisodes());
		}

		if (Objects.nonNull(query.getMinNrOfEpisodes())) {
			mediaBuilder.episodesGreater(query.getMinNrOfEpisodes());
		}

		if (Objects.nonNull(query.getMaxAverageScore())) {
			mediaBuilder.averageScoreLesser(query.getMaxAverageScore());
		}

		if (Objects.nonNull(query.getMinAverageScore())) {
			mediaBuilder.averageScoreGreater(query.getMinAverageScore());
		}

		if (Objects.nonNull(query.getSeason())) {
			mediaBuilder.season(query.getSeason().getSeason());
			mediaBuilder.seasonYear(query.getSeason().getYear());
		}

		if (Objects.nonNull(query.getStatus())) {
			mediaBuilder.status(query.getStatus());
		}

		if (Objects.nonNull(query.getFormat())) {
			mediaBuilder.format(query.getFormat());
		}

		Page page = Page.getPageBuilder(pageNumber, 49)
				.pageInfo(PageInfo.getPageInfoBuilder()
						.currentPage()
						.total()
						.lastPage()
						.hasNextPage()
						.buildPageInfo())
				.media(mediaBuilder
						.type(pwr.pracainz.entities.anime.query.parameters.media.MediaType.ANIME)
						.sort(new MediaSort[]{MediaSort.SCORE_DESC}) //score desc gives highest score for some reason
						.buildMedia())
				.buildPage();

		return client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Query.fromQueryElement(page))
				.retrieve()
				.bodyToMono(ObjectNode.class)
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res,
						String.format("Successfully got %s Page of Search query with conditions: %s", pageNumber, query)))
				.block();
	}

	@Override
	public ObjectNode getAnimeById(int id, HttpServletRequest request) {
		Field field = Field.getFieldBuilder()
				.parameter(FieldParameters.id)
				.parameter(FieldParameters.season)
				.parameter(FieldParameters.seasonYear)
				.parameter(FieldParameters.episodes)
				.parameter(FieldParameters.duration)
				.parameter(FieldParameters.genres)
				.parameter(FieldParameters.averageScore)
				.parameter(FieldParameters.format)
				.parameter(FieldParameters.type)
				.parameter(FieldParameters.favourites)
				.parameter(FieldParameters.isAdult)
				.title(MediaTitle.getMediaTitleBuilder()
						.englishLanguageStylized()
						.romajiLanguageStylized()
						.nativeLanguageStylized()
						.buildMediaTitle())
				.status()
				.coverImage()
				.descriptionAsHtml()
				.source(2)
				.fuzzyDate(FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate).allAndBuild())
				.fuzzyDate(FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.endDate).allAndBuild())
				.nextAiringEpisode()
				.relations(MediaConnection.getMediaConnectionBuilder()
						.edge(MediaEdge.getMediaConnectionBuilder()
								.node(Media.getMediaBuilder(Field.getFieldBuilder()
										.parameter(FieldParameters.id)
										.parameter(FieldParameters.type)
										.title(MediaTitle.getMediaTitleBuilder()
												.englishLanguage()
												.romajiLanguage()
												.nativeLanguage()
												.buildMediaTitle())
										.coverImage()
										.status()
										.buildField()).buildMedia())
								.relationType(2)
								.buildMediaEdge())
						.buildMediaConnection())
				.characters(CharacterArguments.getCharacterArgumentsBuilder()
								.mediaSort(new CharacterSort[]{CharacterSort.ROLE})
								.perPage(15)
								.buildCharacterMediaArguments()
						, CharacterConnection.getCharacterConnectionBuilder()
								.edges(CharacterEdge.getCharacterEdgeBuilder()
										.node(Character.getCharacterBuilder()
												.id()
												.name()
												.image()
												.buildCharacter())
										.id()
										.role()
										.name()
										.voiceActors(Staff.getStaffBuilder()
												.name()
												.image()
												.languageV2()
												.buildStaff())
										.buildCharacterEdge())
								.pageInfo(PageInfo.getPageInfoBuilder()
										.hasNextPage()
										.lastPage()
										.currentPage()
										.buildPageInfo())
								.buildCharacterConnection())
				.staff(StaffArguments.getStaffArgumentsBuilder()
								.sort(new StaffSort[]{StaffSort.RELEVANCE})
								.perPage(4)
								.buildStaffArguments()
						, StaffConnection.getMediaConnectionBuilder()
								.nodes(Staff.getStaffBuilder()
										.name()
										.image()
										.primaryOccupations()
										.buildStaff())
								.pageInfo(PageInfo.getPageInfoBuilder()
										.currentPage()
										.lastPage()
										.hasNextPage()
										.buildPageInfo())
								.buildStaffConnection())
				.buildField();

		Media media = Media.getMediaBuilder(field)
				.id(id)
				.buildMedia();

		ObjectNode node = client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(Query.fromQueryElement(media))
				.retrieve()
				.bodyToMono(ObjectNode.class)
				.flatMap(res -> evaluateClientResponse(QueryElements.Media, res,
						String.format("Successfully got Anime with id: %s", id)))
				.block();

		if (Objects.nonNull(node)) {
			Anime anime = animeRepository.findById(id)
					.orElseGet(() -> animeRepository.save(new Anime(id)));

			node.set("localAnimeInformation", mapper.valueToTree(dtoConversion.convertToDTO(anime)));

			if (UserAuthorizationUtilities.checkIfLoggedUser()) {
				node.set("animeUserInformation",
						mapper.valueToTree(
								animeUserService.getCurrentUserAnimeInfo(anime)
						));
			}
		}

		return node;
	}

	private Mono<ObjectNode> evaluateClientResponse(QueryElements element, ObjectNode response, String positiveLogResponse) {
		return Mono.just(response)
				.map(res -> removeDataAndQueryElementFromJson(res, element))
				.doOnSuccess(s -> log.info(positiveLogResponse))
				.doOnError(e -> {
					throw new AnilistException(i18nService.getTranslation("anime.anilist-server-no-response"));
				});
	}

	private Mono<ObjectNode> evaluateClientResponse(QueryElements element, ObjectNode response, String additionalBodyName, ObjectNode additionalBody, String positiveLogResponse) {
		return Mono.just(response)
				.map(res -> removeDataAndQueryElementFromJson(res, element).<ObjectNode>set(additionalBodyName, additionalBody))
				.doOnSuccess(s -> log.info(positiveLogResponse))
				.doOnError(e -> {
					throw new AnilistException(i18nService.getTranslation("anime.anilist-server-no-response"));
				});
	}

	private ObjectNode removeDataAndQueryElementFromJson(ObjectNode json, QueryElements element) {
		return json.get("data").get(element.name()).deepCopy();
	}

	private ObjectNode getCurrentSeasonInformation() {
		ObjectNode seasonInformation = mapper.createObjectNode();
		seasonInformation.put("year", LocalDateTime.now().getYear());
		seasonInformation.put("season", MediaSeason.getCurrentSeason().toString());

		return seasonInformation;
	}
}
