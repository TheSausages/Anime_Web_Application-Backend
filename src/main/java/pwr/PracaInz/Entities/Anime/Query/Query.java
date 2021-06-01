package pwr.PracaInz.Entities.Anime.Query;

import lombok.Getter;
import net.minidev.json.JSONObject;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.QueryElement;

@Getter
public class Query {
    private Query() {
        throw new UnsupportedOperationException();
    }

    public static BodyInserter<JSONObject, ReactiveHttpOutputMessage> fromQueryElement(QueryElement queryElement) {
        StringBuilder query = new StringBuilder("query(");
        StringBuilder queryElements = new StringBuilder();
        JSONObject variables = new JSONObject();

        variables.putAll(queryElement.getVariables());
        queryElement.getQueryParameters().forEach(query::append);
        queryElements.append(queryElement.getElementString()).append("\n");

        query.delete(query.length() - 2, query.length()).append(") {\n").append(queryElements).append("}");

        JSONObject queryJson = new JSONObject();
        queryJson.put("query", query);
        queryJson.put("variables", variables.toJSONString());

        return BodyInserters.fromValue(queryJson);
    }
}
