package pwr.pracainz.services.anime.Anime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.pracainz.DTO.animeInfo.AnimeQuery;
import pwr.pracainz.configuration.properties.AnilistProperties;
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
import pwr.pracainz.utils.UserAuthorizationUtilities;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

@Log4j2
@Service
public class AnimeService implements AnimeServiceInterface {
	private final AnilistProperties anilistProperties;
	private final AnimeRepository animeRepository;
	private final AnimeUserServiceInterface animeUserService;
	private final DTOConversionInterface dtoConversion;
	private final WebClient client;
	private final ObjectMapper mapper;

	AnimeService(AnilistProperties anilistProperties,
	             AnimeRepository animeRepository,
	             AnimeUserServiceInterface animeUserService,
	             DTOConversionInterface dtoConversion,
	             ObjectMapper mapper) {
		this.anilistProperties = anilistProperties;
		this.animeRepository = animeRepository;
		this.animeUserService = animeUserService;
		this.dtoConversion = dtoConversion;
		this.mapper = mapper;

		client = WebClient.create(anilistProperties.getApiUrl());

		//this.populateTagFile();
	}

	@Override
	public ObjectNode getCurrentSeasonAnime() {
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
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res, "currentSeason", getCurrentSeasonInformation(), "Successfully got Current Season Information and Anime"))
				.block();
	}

	@Override
	public ObjectNode getSeasonAnime(MediaSeason season, int year) {
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
				.flatMap(res -> evaluateClientResponse(QueryElements.Media, res, "Successfully got Anime from " + season + " of " + year))
				.block();
	}

	@Override
	public ObjectNode getTopAnimeMovies(int pageNumber) {
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
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res, "Successfully got " + pageNumber + " Page of Top Anime Movies"))
				.block();
	}

	@Override
	public ObjectNode getTopAnimeAiring(int pageNumber) {
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
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res, "Successfully got " + pageNumber + " Page of Top Airing Anime"))
				.block();
	}

	@Override
	public ObjectNode getTopAnimeAllTime(int pageNumber) {
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
				.flatMap(res -> evaluateClientResponse(QueryElements.Page, res, "Successfully got " + pageNumber + " Page of Top Anime of All Time"))
				.block();
	}

	@Override
	public ObjectNode getAnimeById(int id) {
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
				.flatMap(res -> evaluateClientResponse(QueryElements.Media, res, "Successfully got Anime with id:" + id))
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

	@Override
	public ObjectNode searchByQuery(AnimeQuery query) {
		return null;
	}

	private Mono<ObjectNode> evaluateClientResponse(QueryElements element, ObjectNode response, String positiveResponse) {
		return Mono.just(response)
				.map(res -> removeDataAndQueryElementFromJson(res, element))
				.doOnSuccess(s -> log.info(positiveResponse))
				.doOnError(e -> {
					throw new AnilistException(anilistProperties.getErrorMessage());
				});
	}

	private Mono<ObjectNode> evaluateClientResponse(QueryElements element, ObjectNode response, String additionalBodyName, ObjectNode additionalBody, String positiveResponse) {
		return Mono.just(response)
				.map(res -> removeDataAndQueryElementFromJson(res, element).<ObjectNode>set(additionalBodyName, additionalBody))
				.doOnSuccess(s -> log.info(positiveResponse))
				.doOnError(e -> {
					throw new AnilistException(anilistProperties.getErrorMessage());
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

	/*private void populateTagFile() {
		log.info("Populate the Tag file with Current Existing Tags from the Database");

		Gson gson = new GsonBuilder()
				.serializeNulls()
				.create();

		String query =
				"query {" +
						"  MediaTagCollection {" +
						"    id" +
						"    name" +
						"    description" +
						"    category" +
						"    isGeneralSpoiler" +
						"    isMediaSpoiler" +
						"    isAdult" +
						"  }" +
						"}";

		JSONObject queryParameters = new JSONObject();

		JSONObject requestBody = new JSONObject();
		requestBody.put("query", query);
		requestBody.put("variables", queryParameters.toJSONString());

		Mono<JSONObject> response = client
				.post()
				.headers(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				})
				.body(BodyInserters.fromValue(requestBody))
				.exchangeToMono(this::evaluateClientResponse);

		Object data = gson.fromJson(response.block().toJSONString(), JSONObject.class).get("data");
		Object tagsMedia = gson.fromJson(gson.toJson(data), JSONObject.class).get("MediaTagCollection");

		TagList tagList = new TagList();
		tagList.addTagSet(gson.fromJson(gson.toJson(tagsMedia), new TypeToken<Set<Tag>>() {}.getType()));

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(".    ags.txt"))) {
			tagList.getTagSet().forEach(tag -> {
				try {
					writer.write(tag.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
