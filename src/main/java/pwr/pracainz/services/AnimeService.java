package pwr.pracainz.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.pracainz.configuration.AnilistProperties;
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
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.repositories.user.UserRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;

import static pwr.pracainz.utils.UserAuthorizationUtilities.checkIfLoggedUser;
import static pwr.pracainz.utils.UserAuthorizationUtilities.getPrincipalOfCurrentUser;

@Log4j2
@Service
public class AnimeService {
    private final AnilistProperties anilistProperties;
    private final UserRepository userRepository;
    private final AnimeUserInfoRepository animeUserInfoRepository;
    private final DTOConversionService conversionService;
    private final WebClient client;
    private final ObjectMapper mapper;

    AnimeService(AnilistProperties anilistProperties, UserRepository userRepository, AnimeUserInfoRepository animeUserInfoRepository, DTOConversionService dtoConversionService, ObjectMapper mapper) {
        this.anilistProperties = anilistProperties;
        this.userRepository = userRepository;
        this.conversionService = dtoConversionService;
        this.animeUserInfoRepository = animeUserInfoRepository;
        this.mapper = mapper;

        client = WebClient.create(anilistProperties.getApiUrl());

        //this.populateTagFile();
    }

    private ObjectNode getCurrentSeasonInformation() {
        log.info("Get current Season Information");

        ObjectNode seasonInformation = mapper.createObjectNode();
        seasonInformation.put("year", LocalDateTime.now().getYear());
        seasonInformation.put("season", MediaSeason.getCurrentSeason().toString());

        return seasonInformation;
    }

    public ResponseEntity<ObjectNode> getCurrentSeasonAnime() {
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
                .flatMap(res -> evaluateClientResponse(QueryElements.Page, res.set("currentSeason", getCurrentSeasonInformation()), "Successfully got Current Season Information and Anime"))
                .block();
    }

    public ResponseEntity<ObjectNode> getSeasonAnime(MediaSeason season, int year) {
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

    public ResponseEntity<ObjectNode> getTopAnimeMovies(int pageNumber) {
        Field field = Field.getFieldBuilder()
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

    public ResponseEntity<ObjectNode> getTopAnimeAiring(int pageNumber) {
        Field field = Field.getFieldBuilder()
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

    public ResponseEntity<ObjectNode> getTopAnimeAllTime(int pageNumber) {
        Field field = Field.getFieldBuilder()
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

    public ResponseEntity<ObjectNode> getAnimeById(int id) {
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

        ResponseEntity<ObjectNode> node = client
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

        if (checkIfLoggedUser()) {
            node.getBody().set("animeUserInformation",
                    mapper.valueToTree(conversionService.convertAnimeUserInfoToDTO(
                            animeUserInfoRepository.findById(AnimeUserInfoId.builder()
                                    .animeId(node.getBody().get("id").asInt())
                                    .user(userRepository.getById(getPrincipalOfCurrentUser().toString()))
                                    .build()).get())
                    ));
        }

        return node;
    }

    private Mono<ResponseEntity<ObjectNode>> evaluateClientResponse(QueryElements element, ObjectNode response, String positiveResponse) {
        return Mono.just(response)
                .map(res -> removeDataAndQueryElementFromJson(res, element))
                .map(res -> ResponseEntity.status(HttpStatus.OK).body(res))
                .doOnSuccess(s -> log.info(positiveResponse))
                .doOnError(e -> log.info("Anilist Server did not Respond!"))
                .onErrorReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(getErrorMessage()));
    }

    private ObjectNode removeDataAndQueryElementFromJson(ObjectNode json, QueryElements element) {
        return json.get("data").get(element.name()).deepCopy();
    }

    private ObjectNode getErrorMessage() {
        return mapper.createObjectNode().put("message", anilistProperties.getErrorMessage());
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./tags.txt"))) {
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
