package pwr.pracainz.integrationtests.config.wiremock;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The request need to be:
 * <pre>
 *      WireMockExtensionSummarizer.getInstance().addToTransformers(
 *          WireMockPageExtension.getInstance(),
 *          WireMockAnimeSearchExtension.getInstance()
 * 	    );
 * </pre>
 * <pre>
 *      wireMockServer
 *          .stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
 *              .willReturn(ResponseDefinitionBuilder
 *              .okForJson(responseBody)
 *              .withTransformers(WireMockExtensionSummarizer.wireMockExtensionSummarizerName)
 *          )
 *     );
 * </pre>
 */
public class WireMockExtensionSummarizer extends ResponseTransformer {
	public static final String wireMockExtensionSummarizerName = "wireMockExtensionSummarizer";

	private static WireMockExtensionSummarizer instance;
	private Set<ResponseTransformer> transformers;

	private WireMockExtensionSummarizer() {
		this.transformers = ConcurrentHashMap.newKeySet();
	}

	public static WireMockExtensionSummarizer getInstance() {
		if (Objects.isNull(instance)) {
			synchronized (WireMockExtensionSummarizer.class) {
				if (Objects.isNull(instance)) {
					instance = new WireMockExtensionSummarizer();
				}
			}
		}

		return instance;
	}

	public void addToTransformers(ResponseTransformer ...transformers) {
		this.transformers.addAll(List.of(transformers));
	}

	@Override
	public boolean applyGlobally() { return false; }

	@Override
	public String getName() {
		return wireMockExtensionSummarizerName;
	}

	@Override
	public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
		synchronized (WireMockExtensionSummarizer.class) {
			//transform th response using each transformer
			for (ResponseTransformer transformer : transformers) {
				response = transformer.transform(request, response, files, parameters);
			}

			//create a new one to be sure no are left
			transformers = ConcurrentHashMap.newKeySet();
		}

		//return the response
		return response;
	}
}
