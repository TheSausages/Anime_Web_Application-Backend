package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Recommendation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationEdgeTest {

    @Test
    void RecommendationEdge_Recommendation_NoException() {
        //given
        Recommendation recommendation = Recommendation.getRecommendationBuilder()
                .rating()
                .buildRecommendation();

        //when
        RecommendationEdge edge = new RecommendationEdge(recommendation);

        //then
        assertEquals(edge.getRecommendationEdgeString(), "recommendationEdge {\nnode {\nrating\n}\n}");
    }

    @Test
    void RecommendationEdge_RecommendationWithoutFieldName_NoException() {
        //given
        Recommendation recommendation = Recommendation.getRecommendationBuilder()
                .rating()
                .buildRecommendation();

        //when
        RecommendationEdge edge = new RecommendationEdge(recommendation);

        //then
        assertEquals(edge.getRecommendationEdgeWithoutFieldName(), "{\nnode {\nrating\n}\n}");
    }
}