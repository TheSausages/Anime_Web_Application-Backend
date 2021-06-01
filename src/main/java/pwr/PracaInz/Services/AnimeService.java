package pwr.PracaInz.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaSeason;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaTitle;
import pwr.PracaInz.Entities.Anime.Query.Query;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Field;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;
import pwr.PracaInz.Entities.Anime.TagFile.Tag;
import pwr.PracaInz.Entities.Anime.TagFile.TagList;
import reactor.core.publisher.Mono;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Service
public class AnimeService {
    private final WebClient client;

    @Autowired
    AnimeService() {
        client = WebClient.create("https://graphql.anilist.co");

        //this.populateTagFile();
    }

    public String getCurrentSeasonInformation() {
        JSONObject seasonInformation = new JSONObject();
        seasonInformation.put("year", LocalDateTime.now().getYear());
        seasonInformation.put("season", MediaSeason.getCurrentSeason());

        return seasonInformation.toJSONString();
    }

    public String getCurrentSeasonAnime() {
        String query =
                "query ($page: Int, $pageSize: Int, $seasonYear: Int, $season: MediaSeason) {" +
                        " Page (page: $page, perPage: $pageSize) {" +
                        "    pageInfo {" +
                        "      total" +
                        "      currentPage" +
                        "      lastPage" +
                        "      hasNextPage" +
                        "      perPage" +
                        "    }" +
                        "  media (seasonYear: $seasonYear, season: $season, type: ANIME) {" +
                        "    id" +
                        "    title {" +
                        "      english" +
                        "      romaji" +
                        "    }" +
                        "    coverImage {" +
                        "      extraLarge" +
                        "      large" +
                        "      medium" +
                        "      color" +
                        "    }" +
                        "  }" +
                        "  }" +
                        "}";

        JSONObject queryParameters = new JSONObject();
        queryParameters.put("page", "1");
        queryParameters.put("pageSize", "40");
        queryParameters.put("seasonYear", LocalDateTime.now().getYear());
        queryParameters.put("season", MediaSeason.getCurrentSeason());

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(prepareJSONObject(query, queryParameters))
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    public String getSeasonAnime(String season, String year) {
        String query =
                "query ($page: Int, $pageSize: Int, $seasonYear: Int, $season: MediaSeason) {" +
                        " Page (page: $page, perPage: $pageSize) {" +
                        "    pageInfo {" +
                        "      total" +
                        "      currentPage" +
                        "      lastPage" +
                        "      hasNextPage" +
                        "      perPage" +
                        "    }" +
                        "  media (seasonYear: $seasonYear, season: $season, type: ANIME) {" +
                        "    id" +
                        "    title {" +
                        "      english" +
                        "      romaji" +
                        "    }" +
                        "    coverImage {" +
                        "      extraLarge" +
                        "      large" +
                        "      medium" +
                        "      color" +
                        "    }" +
                        "  }" +
                        "  }" +
                        "}";

        JSONObject queryParameters = new JSONObject();
        queryParameters.put("page", "1");
        queryParameters.put("pageSize", "40");
        queryParameters.put("seasonYear", "2021");
        queryParameters.put("season", "SPRING");

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(prepareJSONObject(query, queryParameters))
                .exchangeToMono(this::evaluateClientResponse);

        return response.block();
    }

    public String getAnimeById(int id) {
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .title(MediaTitle.getMediaTitleBuilder()
                        .englishLanguage()
                        .romajiLanguage()
                        .buildMediaTitle())
                .parameter(FieldParameters.format)
                .parameter(FieldParameters.averageScore)
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
                .body(Query.fromMedia(media))
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

        Mono<String> response = client
                .post()
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                })
                .body(prepareJSONObject(query, queryParameters))
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

    private BodyInserter<JSONObject, ReactiveHttpOutputMessage> prepareJSONObject(String query, JSONObject queryParameters) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);
        requestBody.put("variables", queryParameters.toJSONString());

        return BodyInserters.fromValue(requestBody);
    }
}
