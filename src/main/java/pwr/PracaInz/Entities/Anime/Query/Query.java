package pwr.PracaInz.Entities.Anime.Query;

import lombok.Getter;
import net.minidev.json.JSONObject;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.QueryParameters;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Field;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateValue;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.*;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.QueryElements;

import java.util.*;

@Getter
public class Query {
    private Query() {
        throw new UnsupportedOperationException();
    }

    public static BodyInserter<JSONObject, ReactiveHttpOutputMessage> fromMedia(Media media) {
        StringBuilder query = new StringBuilder("query(");
        StringBuilder queryElements = new StringBuilder();
        JSONObject variables = new JSONObject();

        variables.putAll(media.getVariables());
        media.getQueryParameters().forEach(query::append);
        queryElements.append(media.getElementString()).append("\n");

        query.delete(query.length() - 2, query.length()).append(") {\n").append(queryElements).append("}");

        JSONObject queryJson = new JSONObject();
        queryJson.put("query", query);
        queryJson.put("variables", variables.toJSONString());

        return BodyInserters.fromValue(queryJson);
    }
}
