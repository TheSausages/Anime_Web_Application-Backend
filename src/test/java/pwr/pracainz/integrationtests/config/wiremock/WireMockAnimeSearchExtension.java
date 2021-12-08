package pwr.pracainz.integrationtests.config.wiremock;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import pwr.pracainz.entities.anime.query.queryElements.QueryElements;
import pwr.pracainz.integrationtests.config.GraphQlUtils;

import java.util.Map;

/**
 * The request need to be:
 * <pre>
 *     wireMockServer
 *          .stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
 *          .willReturn(ResponseDefinitionBuilder
 *          .okForJson(responseBody)
 *          .withTransformers(WireMockPageExtension.wireMockPageExtensionName))
 *     );
 * </pre>
 */
public class WireMockAnimeSearchExtension extends ResponseDefinitionTransformer {
	public static final String wireMockPageExtensionName = "wireMockAnimeSearchExtension";

	//We take the variable string from the request and give it to the request
	@Override
	public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
		try {
			ObjectMapper mapper = new ObjectMapper().setDefaultPrettyPrinter(new DefaultPrettyPrinter());

			JsonNode requestBody = mapper.readTree(request.getBody());

			JsonNode responseBody = mapper.readTree(responseDefinition.getBody()).get("data").get("Page");

			Map<String, Object> variables = GraphQlUtils.readStringIntoStringKeyMap(
					requestBody.get("variables").asText(),
					mapper);
			((ObjectNode) responseBody).put("variables", mapper.writeValueAsString(variables));

			return ResponseDefinition
					.okForJson(
							GraphQlUtils.addElementToCreateGraphQlQueryAnwser(
									responseBody,
									QueryElements.Page,
									mapper));
		} catch (Exception e) {
			throw new AssertionError(e.getMessage(), e);
		}
	}

	@Override
	public String getName() {
		return wireMockPageExtensionName;
	}
}
