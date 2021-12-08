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
public class WireMockPageExtension extends ResponseDefinitionTransformer {
	public static final String wireMockPageExtensionName = "wireMockPageExtension";

	@Override
	public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
		try {
			ObjectMapper mapper = new ObjectMapper().setDefaultPrettyPrinter(new DefaultPrettyPrinter());

			JsonNode requestBody = mapper.readTree(request.getBody());

			if (!requestBody.get("variables").asText().contains("page")) {
				throw new AssertionError("Request body does not have a 'page' element");
			}

			JsonNode responseBody = mapper.readTree(responseDefinition.getBody()).get("data").get("Page");

			System.out.println(requestBody);

			Map<String, Object> variables = GraphQlUtils.readStringIntoStringKeyMap(
					requestBody.get("variables").asText(),
					mapper);
			((ObjectNode) responseBody).put("page", mapper.writeValueAsString(variables.get("page")));

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
	public boolean applyGlobally() { return false; }

	@Override
	public String getName() {
		return wireMockPageExtensionName;
	}
}
