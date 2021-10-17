package pwr.pracainz.entities.anime.query.parameters.connections.recommendation;

import lombok.Getter;

@Getter
public class RecommendationEdge {
	private final String recommendationEdgeString;

	public RecommendationEdge(Recommendation recommendation) {
		this.recommendationEdgeString = "recommendationEdge {\nnode " + recommendation.getRecommendationStringWithoutFieldName() + "\n}";
	}

	public String getRecommendationEdgeWithoutFieldName() {
		return this.recommendationEdgeString.substring(19);
	}
}
