package pwr.PracaInz.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.*;
import pwr.PracaInz.Entities.Anime.Query.Query;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Field;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Page.Page;
import pwr.PracaInz.Entities.Anime.TagFile.Tag;
import pwr.PracaInz.Entities.Anime.TagFile.TagList;
import reactor.core.publisher.Mono;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Log4j2
@Service
public class AnimeService {
    private final WebClient client;

    @Autowired
    AnimeService() {
        client = WebClient.create("https://graphql.anilist.co");

        //this.populateTagFile();
    }

    public String getCurrentSeasonInformation() {
        log.info("Get current Season Information");

        JSONObject seasonInformation = new JSONObject();
        seasonInformation.put("year", LocalDateTime.now().getYear());
        seasonInformation.put("season", MediaSeason.getCurrentSeason());

        return seasonInformation.toJSONString();
    }

    public String getCurrentSeasonAnime() {
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

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    public String getSeasonAnime(MediaSeason season, int year) {
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

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    public String getTopAnimeMovies(int pageNumber) {
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

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    public String getTopAnimeAiring(int pageNumber) {
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

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    public String getTopAnimeAllTime(int pageNumber) {
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

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(page))
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    public String getAnimeById(int id) {
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

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(Query.fromQueryElement(media))
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    private Mono<String> evaluateClientResponse(ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(String.class);
        } else {
            System.out.println(response.bodyToMono(String.class));
            return response.createException()
                    .flatMap(Mono::error);
        }
    }

    private void populateTagFile() {
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

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(BodyInserters.fromValue(requestBody))
                .exchangeToMono(this::evaluateClientResponse);

        Object data = gson.fromJson(response.block(), JSONObject.class).get("data");
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
    }
}
