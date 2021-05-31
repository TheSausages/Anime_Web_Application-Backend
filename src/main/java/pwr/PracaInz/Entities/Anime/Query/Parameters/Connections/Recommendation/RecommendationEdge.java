package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Recommendation;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule.AiringSchedule;

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
