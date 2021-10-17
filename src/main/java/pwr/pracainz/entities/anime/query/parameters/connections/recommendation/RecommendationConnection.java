package pwr.pracainz.entities.anime.query.parameters.connections.recommendation;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class RecommendationConnection {
	private final String recommendationConnectionString;

	private RecommendationConnection(String recommendationConnectionString) {
		this.recommendationConnectionString = recommendationConnectionString;
	}

	public String getRecommendationConnectionWithoutFieldName() {
		return this.recommendationConnectionString.substring(25);
	}

	public static RecommendationConnectionBuilder getRecommendationConnectionBuilder() {
		return new RecommendationConnectionBuilder();
	}

	public static final class RecommendationConnectionBuilder {
		private final Set<ParameterString> recommendationConnection = new LinkedHashSet<>();

		public RecommendationConnectionBuilder edges(RecommendationEdge edge) {
			recommendationConnection.add(new ParameterString("edges " + edge.getRecommendationEdgeWithoutFieldName() + "\n"));
			return this;
		}

		public RecommendationConnectionBuilder nodes(Recommendation recommendation) {
			recommendationConnection.add(new ParameterString("nodes " + recommendation.getRecommendationStringWithoutFieldName() + "\n"));
			return this;
		}

		public RecommendationConnectionBuilder pageInfo(PageInfo pageInfo) {
			recommendationConnection.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
			return this;
		}

		public RecommendationConnection buildRecommendationConnection() {
			if (recommendationConnection.isEmpty()) {
				throw new IllegalStateException("Recommendation Connection should posses at least 1 parameter!");
			}

			StringBuilder characterConnectionBuilder = new StringBuilder("recommendationConnection {\n");

			recommendationConnection.forEach(characterConnectionBuilder::append);

			characterConnectionBuilder.append("}");

			return new RecommendationConnection(characterConnectionBuilder.toString());
		}
	}
}
