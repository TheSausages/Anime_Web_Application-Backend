package pwr.PracaInz.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.PracaInz.Configuration.AnilistProperties;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.*;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.Character;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaEdge;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.*;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateField;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateFieldParameter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.*;
import pwr.PracaInz.Entities.Anime.Query.Query;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Field;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Page.Page;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.QueryElements;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

@Log4j2
@Service
public class AnimeService {
    private final AnilistProperties anilistProperties;
    private final WebClient client;
    private final Gson gson;

    AnimeService(AnilistProperties anilistProperties) {
        this.anilistProperties = anilistProperties;

        client = WebClient.create(anilistProperties.getApiUrl());
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //this.populateTagFile();
    }

    public JsonObject getCurrentSeasonInformation() {
        log.info("Get current Season Information");

        JsonObject seasonInformation = new JsonObject();
        seasonInformation.addProperty("year", LocalDateTime.now().getYear());
        seasonInformation.addProperty("season", MediaSeason.getCurrentSeason().toString());

        return seasonInformation;
    }

    public ResponseEntity<JsonObject> getCurrentSeasonAnime() {
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
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
                        .sort(new MediaSort[]{MediaSort.POPULARITY_DESC})
                        .buildMedia())
                .buildPage();

        return Objects.requireNonNull(client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> gson.fromJson(body, JsonObject.class))
                .map(res -> evaluateClientResponse(QueryElements.Page, res, "Successfully got Current Season Information and Anime", getCurrentSeasonInformation(), "currentSeason"))
                .block())
                .block();
    }

    public ResponseEntity<JsonObject> getSeasonAnime(MediaSeason season, int year) {
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
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
                        .buildMedia())
                .buildPage();

        return Objects.requireNonNull(client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> gson.fromJson(body, JsonObject.class))
                .map(res -> evaluateClientResponse(QueryElements.Media, res, "Successfully got Anime from " + season + " of " + year))
                .block())
                .block();
    }

    public ResponseEntity<JsonObject> getTopAnimeMovies(int pageNumber) {
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
                        .hasNextPage()
                        .buildPageInfo())
                .media(Media.getMediaBuilder(field)
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
                        .format(MediaFormat.MOVIE)
                        .sort(new MediaSort[]{MediaSort.SCORE_DESC}) //score desc gives highest score for some reason
                        .buildMedia())
                .buildPage();

        return Objects.requireNonNull(client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> gson.fromJson(body, JsonObject.class))
                .map(res -> evaluateClientResponse(QueryElements.Page, res, "Successfully got " + pageNumber + " Page of Top Movies"))
                .block())
                .block();
    }

    public ResponseEntity<JsonObject> getTopAnimeAiring(int pageNumber) {
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
                        .hasNextPage()
                        .buildPageInfo())
                .media(Media.getMediaBuilder(field)
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
                        .status(MediaStatus.RELEASING)
                        .sort(new MediaSort[]{MediaSort.SCORE_DESC}) //score desc gives highest score for some reason
                        .buildMedia())
                .buildPage();

        return Objects.requireNonNull(client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> gson.fromJson(body, JsonObject.class))
                .map(res -> evaluateClientResponse(QueryElements.Page, res, "Successfully got " + pageNumber + " Page of Top Airing Anime"))
                .block())
                .block();
    }

    public ResponseEntity<JsonObject> getTopAnimeAllTime(int pageNumber) {
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
                        .hasNextPage()
                        .buildPageInfo())
                .media(Media.getMediaBuilder(field)
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
                        .sort(new MediaSort[]{MediaSort.SCORE_DESC}) //score desc gives highest score for some reason
                        .buildMedia())
                .buildPage();

        return Objects.requireNonNull(client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> gson.fromJson(body, JsonObject.class))
                .map(res -> evaluateClientResponse(QueryElements.Page, res, "Successfully got " + pageNumber + " Page of Top Anime of All Time"))
                .block())
                .block();
    }

    public ResponseEntity<JsonObject> getAnimeById(int id) {
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

        return Objects.requireNonNull(client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(media))
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> gson.fromJson(body, JsonObject.class))
                .mapNotNull(res -> evaluateClientResponse(QueryElements.Media, res, "Successfully got Anime with id:" + id))
                .block())
                .block();
    }

    private Mono<ResponseEntity<JsonObject>> evaluateClientResponse(QueryElements element, JsonObject response, String positiveResponse) {
        return Mono.just(response)
                .mapNotNull(res -> removeDataAndQueryElementFromJson(res, element))
                .mapNotNull(res -> ResponseEntity.status(HttpStatus.OK).body(res))
                .doOnSuccess(s -> log.info(positiveResponse))
                .doOnError(e -> log.info("Anilist Server did not Respond!"))
                .onErrorReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(getErrorMessage()));
    }

    private Mono<ResponseEntity<JsonObject>> evaluateClientResponse(QueryElements element, JsonObject response, String positiveResponse, JsonObject additionalBody, String additionalBodyName) {
        return Mono.just(response)
                .mapNotNull(res -> removeDataAndQueryElementFromJson(res, element))
                .mapNotNull(res -> {
                    res.add(additionalBodyName, additionalBody);

                    return ResponseEntity.status(HttpStatus.OK).body(res);
                })
                .doOnSuccess(s -> log.info(positiveResponse))
                .doOnError(e -> log.info(anilistProperties.getErrorMessage()))
                .onErrorReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(getErrorMessage()));
    }

    private JsonObject removeDataAndQueryElementFromJson(JsonObject json, QueryElements element) {
        return json.getAsJsonObject("data").getAsJsonObject(element.name());
    }

    private JsonObject getErrorMessage() {
        JsonObject error = new JsonObject();
        error.addProperty("message", anilistProperties.getErrorMessage());

        return error;
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
