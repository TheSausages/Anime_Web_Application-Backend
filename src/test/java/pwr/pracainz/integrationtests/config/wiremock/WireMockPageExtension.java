package pwr.pracainz.integrationtests.config.wiremock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import pwr.pracainz.configuration.configuration.ObjectMapperConfiguration;
import pwr.pracainz.entities.anime.query.queryElements.QueryElements;
import pwr.pracainz.integrationtests.config.GraphQlUtils;

import java.util.Map;
import java.util.Objects;

/**
 * The request need to be:
 * <pre>
 *     wireMockServer
 *          .stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
 *              .willReturn(ResponseDefinitionBuilder
 *              .okForJson(responseBody)
 *              .withTransformers(WireMockPageExtension.wireMockPageExtensionName)
 *          )
 *     );
 * </pre>
 */
public class WireMockPageExtension extends ResponseTransformer {
	public static final String wireMockPageExtensionName = "wireMockPageExtension";

	private static WireMockPageExtension instance;

	private WireMockPageExtension() { }

	public static WireMockPageExtension getInstance() {
		if (Objects.isNull(instance)) {
			synchronized (WireMockPageExtension.class) {
				if (Objects.isNull(instance)) {
					instance = new WireMockPageExtension();
				}
			}
		}

		return instance;
	}

	@Override
	public boolean applyGlobally() { return false; }

	@Override
	public String getName() {
		return wireMockPageExtensionName;
	}

	@Override
	public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
		return transformationMethod(request, response, files, parameters);
	}

	public static Response transformationMethod(Request request, Response response, FileSource files, Parameters parameters) {
		try {
			ObjectMapper mapper = ObjectMapperConfiguration.ObjectMapperFactory.getNewObjectMapper();

			JsonNode requestBody = mapper.readTree(request.getBody());

			if (!requestBody.get("variables").asText().contains("page")) {
				throw new AssertionError("Request body does not have a 'page' element");
			}

			JsonNode responseBody = mapper.readTree(response.getBody()).get("data").get("Page");

			Map<String, Object> variables = GraphQlUtils.readStringIntoStringKeyMap(
					requestBody.get("variables").asText(),
					mapper);
			((ObjectNode) responseBody).put("page", mapper.writeValueAsString(variables.get("page")));

			return Response.Builder.like(response).but()
					.body(mapper.writeValueAsString(GraphQlUtils.addElementToCreateGraphQlQueryAnwser(
							responseBody,
							QueryElements.Page,
							mapper))).build();
		} catch (Exception e) {
			throw new AssertionError(e.getMessage(), e);
		}
	}
}
