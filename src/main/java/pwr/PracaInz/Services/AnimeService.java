package pwr.PracaInz.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.*;
import pwr.PracaInz.Entities.Anime.Query.Query;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Field;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Page.Page;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.QueryElements;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;

@Log4j2
@Service
public class AnimeService {
    private final WebClient client;
    private final Gson gson;

    @Autowired
    AnimeService() {
        client = WebClient.create("https://graphql.anilist.co");
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //this.populateTagFile();
    }

    public String getCurrentSeasonInformation() {
        log.info("Get current Season Information");

        JSONObject seasonInformation = new JSONObject();
        seasonInformation.put("year", LocalDateTime.now().getYear());
        seasonInformation.put("season", MediaSeason.getCurrentSeason());

        return seasonInformation.toJSONString();
    }

    public ResponseEntity<String> getCurrentSeasonAnime() {
        log.info("Get current Season Anime");

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
                        .seasonYear(LocalDateTime.now().getYear())
                        .season(MediaSeason.getCurrentSeason())
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
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
                .bodyToMono(String.class)
                .map(res -> evaluateClientResponseWithAdditionalBody(QueryElements.Page, Mono.just(res), "Successfully got Current Season Information and Anime", "\"seasonInformation\": " + getCurrentSeasonInformation()))
                .block()
                .block()
                ;
    }

    public ResponseEntity<String> getSeasonAnime(MediaSeason season, int year) {
        log.info("Get Anime from season:" + season + " from the year:" + year);

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

        return client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .retrieve()
                .bodyToMono(String.class)
                .map(res -> evaluateClientResponse(QueryElements.Media, Mono.just(res), "Successfully got Anime from " + season + " of " + year))
                .block()
                .block();
    }

    public ResponseEntity<String> getTopAnimeMovies(int pageNumber) {
        log.info("Get the " + pageNumber + " page of the top ranking Movies based on Score");

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
                        .buildPageInfo())
                .media(Media.getMediaBuilder(field)
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
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
                .bodyToMono(String.class)
                .map(res -> evaluateClientResponse(QueryElements.Page, Mono.just(res), "Successfully got " + pageNumber + " Page of Top Movies"))
                .block()
                .block();
    }

    public ResponseEntity<String> getTopAnimeAiring(int pageNumber) {
        log.info("Get the " + pageNumber + " page of the top ranking airing Anime based on Score");

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
                        .buildPageInfo())
                .media(Media.getMediaBuilder(field)
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
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
                .bodyToMono(String.class)
                .map(res -> evaluateClientResponse(QueryElements.Page, Mono.just(res), "Successfully got " + pageNumber + " Page of Top Airing Anime"))
                .block()
                .block();
    }

    public ResponseEntity<String> getTopAnimeAllTime(int pageNumber) {
        log.info("Get the" + pageNumber + " page of the top ranking Anime of all Time");

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
                        .buildPageInfo())
                .media(Media.getMediaBuilder(field)
                        .type(pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType.ANIME)
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
                .bodyToMono(String.class)
                .map(res -> evaluateClientResponse(QueryElements.Page, Mono.just(res), "Successfully got " + pageNumber + " Page of Top Anime of All Time"))
                .block()
                .block();
    }

    public ResponseEntity<String> getAnimeById(int id) {
        log.info("Get Anime with id:" + id);

        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .title(MediaTitle.getMediaTitleBuilder()
                        .englishLanguage()
                        .romajiLanguage()
                        .buildMediaTitle())
                .parameter(FieldParameters.format)
                .parameter(FieldParameters.averageScore)
                .coverImage()
                .description()
                .buildField();
        Media media = Media.getMediaBuilder(field)
                .id(id)
                .buildMedia();


        return client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(media))
                .retrieve()
                .bodyToMono(String.class)
                .mapNotNull(res -> evaluateClientResponse(QueryElements.Media, Mono.just(res), "Successfully got Anime with id:" + id))
                .block()
                .block();
    }

    private Mono<ResponseEntity<String>> evaluateClientResponseWithAdditionalBody(QueryElements element, Mono<String> response, String positiveResponse, String additionalBody) {
        return response
                .mapNotNull(res -> removeDataAndQueryElementFromJson(res, element))
                .doOnSuccess(s -> log.info(positiveResponse))
                .mapNotNull(res -> ResponseEntity.status(HttpStatus.OK).body(StringUtils.chop(res) + ",\n" + additionalBody + "}"))
                .doOnError(e -> log.info("Anilist Server did not Respond!"))
                .onErrorReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Anilist Server did not Respond!"));
    }

    private Mono<ResponseEntity<String>> evaluateClientResponse(QueryElements element, Mono<String> response, String positiveResponse) {
        return response
                .mapNotNull(res -> removeDataAndQueryElementFromJson(res, element))
                .doOnSuccess(s -> log.info(positiveResponse))
                .mapNotNull(res -> ResponseEntity.status(HttpStatus.OK).body(res))
                .doOnError(e -> log.info("Anilist Server did not Respond!"))
                .onErrorReturn(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Anilist Server did not Respond!"));
    }

    private String removeDataAndQueryElementFromJson(String json, QueryElements element) {
        return gson.toJson(gson.fromJson(json, JsonObject.class).getAsJsonObject("data").getAsJsonObject(element.name()));
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
