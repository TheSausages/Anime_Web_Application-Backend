package pwr.pracainz.entities.anime.query.queryElements.Page;

import lombok.Getter;
import net.minidev.json.JSONObject;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;
import pwr.pracainz.entities.anime.query.queryElements.Media.Media;
import pwr.pracainz.entities.anime.query.queryElements.QueryElement;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class Page implements QueryElement {
	private final String elementString;
	private final Set<ParameterString> queryParameters;
	private final JSONObject variables;

	private Page(String elementString, Set<ParameterString> queryParameters, JSONObject variables) {
		this.elementString = elementString;
		this.queryParameters = queryParameters;
		this.variables = variables;
	}

	public String getMediaStringWithoutFieldName() {
		return elementString.substring(elementString.indexOf(')') + 2);
	}

	public static PageBuilder getPageBuilder(int page, int perPage) {
		return new PageBuilder(page, perPage);
	}

	public static class PageBuilder {
		private final Set<ParameterString> pageFieldParameters = new LinkedHashSet<>();
		private final Set<ParameterString> queryParameters = new LinkedHashSet<>();
		private final Map<String, Object> parametersValue = new LinkedHashMap<>();

		public PageBuilder(int page, int perPage) {
			queryParameters.add(new ParameterString("$page: Int, "));
			queryParameters.add(new ParameterString("$perPage: Int, "));

			parametersValue.putIfAbsent("page", page);
			parametersValue.putIfAbsent("perPage", perPage);
		}

		public PageBuilder pageInfo(PageInfo pageInfo) {
			pageFieldParameters.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
			return this;
		}

		public PageBuilder media(Media media) {
			queryParameters.addAll(media.getQueryParameters());
			parametersValue.putAll(media.getVariables());
			pageFieldParameters.add(new ParameterString("media" + media.getMediaWithoutFieldName() + "\n"));
			return this;
		}

		public Page buildPage() {
			if (pageFieldParameters.isEmpty()) {
				throw new IllegalStateException("Page must posses at least 1 parameter!");
			}

			StringBuilder pageBuilder = new StringBuilder("Page(page: $page, perPage: $perPage) {\n");
			pageFieldParameters.forEach(pageBuilder::append);
			pageBuilder.append("}");

			JSONObject jsonObject = new JSONObject();
			jsonObject.putAll(parametersValue);

			return new Page(pageBuilder.toString(), queryParameters, jsonObject);
		}
	}
}
