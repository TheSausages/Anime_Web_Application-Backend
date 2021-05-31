package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Recommendation;

import org.junit.jupiter.api.Test;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationConnectionTest {

    @Test
    void RecommendationConnectionBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> RecommendationConnection.getRecommendationConnectionBuilder().buildRecommendationConnection());

        //then
        assertEquals(exception.getMessage(), "Recommendation Connection should posses at least 1 parameter!");
    }

    @Test
    void RecommendationConnectionBuilder_Edges_NoException() {
        //given
        Recommendation recommendation = Recommendation.getRecommendationBuilder()
                .id()
                .buildRecommendation();
        RecommendationEdge recommendationEdge = new RecommendationEdge(recommendation);

        //when
        RecommendationConnection recommendationConnection = RecommendationConnection.getRecommendationConnectionBuilder()
                .edges(recommendationEdge)
                .buildRecommendationConnection();

        //then
        assertEquals(recommendationConnection.getRecommendationConnectionString(), "recommendationConnection {\nedges {\nnode {\nid\n}\n}\n}");
    }

    @Test
    void RecommendationConnectionBuilder_ManyEdges_NoException() {
        //given
        Recommendation recommendation = Recommendation.getRecommendationBuilder()
                .rating()
                .buildRecommendation();
        RecommendationEdge recommendationEdge = new RecommendationEdge(recommendation);
        Recommendation recommendation1 = Recommendation.getRecommendationBuilder()
                .id()
                .buildRecommendation();
        RecommendationEdge recommendationEdge1 = new RecommendationEdge(recommendation1);

        //when
        RecommendationConnection recommendationConnection = RecommendationConnection.getRecommendationConnectionBuilder()
                .edges(recommendationEdge)
                .edges(recommendationEdge1)
                .buildRecommendationConnection();

        //then
        assertEquals(recommendationConnection.getRecommendationConnectionString(), "recommendationConnection {\nedges {\nnode {\nrating\n}\n}\n}");
    }

    @Test
    void RecommendationConnectionBuilder_Nodes_NoException() {
        //given
        Recommendation recommendation = Recommendation.getRecommendationBuilder()
                .id()
                .buildRecommendation();

        //when
        RecommendationConnection recommendationConnection = RecommendationConnection.getRecommendationConnectionBuilder()
                .nodes(recommendation)
                .buildRecommendationConnection();

        //then
        assertEquals(recommendationConnection.getRecommendationConnectionString(), "recommendationConnection {\nnodes {\nid\n}\n}");
    }

    @Test
    void RecommendationConnectionBuilder_ManyNodes_NoException() {
        //given
        Recommendation recommendation = Recommendation.getRecommendationBuilder()
                .rating()
                .buildRecommendation();
        Recommendation recommendation1 = Recommendation.getRecommendationBuilder()
                .id()
                .buildRecommendation();

        //when
        RecommendationConnection recommendationConnection = RecommendationConnection.getRecommendationConnectionBuilder()
                .nodes(recommendation)
                .nodes(recommendation1)
                .buildRecommendationConnection();

        //then
        assertEquals(recommendationConnection.getRecommendationConnectionString(), "recommendationConnection {\nnodes {\nrating\n}\n}");
    }

    @Test
    void RecommendationConnectionBuilder_PageInfo_NoException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .currentPage()
                .buildPageInfo();

        //when
        RecommendationConnection recommendationConnection = RecommendationConnection.getRecommendationConnectionBuilder()
                .pageInfo(pageInfo)
                .buildRecommendationConnection();

        //then
        assertEquals(recommendationConnection.getRecommendationConnectionString(), "recommendationConnection {\npageInfo {\ncurrentPage\n}\n}");
    }

    @Test
    void RecommendationConnectionBuilder_ManyPageInfo_NoException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .lastPage()
                .buildPageInfo();
        PageInfo pageInfo1 = PageInfo.getPageInfoBuilder()
                .currentPage()
                .buildPageInfo();

        //when
        RecommendationConnection recommendationConnection = RecommendationConnection.getRecommendationConnectionBuilder()
                .pageInfo(pageInfo)
                .pageInfo(pageInfo1)
                .buildRecommendationConnection();

        //then
        assertEquals(recommendationConnection.getRecommendationConnectionString(), "recommendationConnection {\npageInfo {\nlastPage\n}\n}");
    }

    @Test
    void RecommendationConnectionBuilder_NodesAndPageInfo_NoException() {
        //given
        Recommendation recommendation = Recommendation.getRecommendationBuilder()
                .id()
                .rating()
                .buildRecommendation();
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .perPage()
                .total()
                .lastPage()
                .buildPageInfo();


        //when
        RecommendationConnection recommendationConnection = RecommendationConnection.getRecommendationConnectionBuilder()
                .nodes(recommendation)
                .pageInfo(pageInfo)
                .buildRecommendationConnection();

        //then
        assertEquals(recommendationConnection.getRecommendationConnectionString(), "recommendationConnection {\nnodes {\nid\nrating\n}\npageInfo {\nperPage\ntotal\nlastPage\n}\n}");
    }
}