package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;

import static org.junit.jupiter.api.Assertions.*;

class AiringScheduleConnectionTest {

    @Test
    void AiringScheduleConnectionBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> AiringScheduleConnection.getAiringScheduleConnectionBuilder().buildAiringScheduleConnection());

        //then
        assertEquals(exception.getMessage(), "Airing Schedule Connection should posses at least 1 parameter!");
    }

    @Test
    void AiringScheduleConnectionBuilder_Edge_NoException() {
        //given
        AiringScheduleEdge airingScheduleEdge = new AiringScheduleEdge();

        //when
        AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
                .edges(airingScheduleEdge)
                .buildAiringScheduleConnection();

        //then
        assertEquals(connection.getAiringScheduleConnectionString(), "airingScheduleConnection {\nedges {\nid\n}\n}");
    }

    @Test
    void AiringScheduleConnectionBuilder_EdgeWithoutFieldname_NoException() {
        //given
        AiringScheduleEdge airingScheduleEdge = new AiringScheduleEdge();

        //when
        AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
                .edges(airingScheduleEdge)
                .buildAiringScheduleConnection();

        //then
        assertEquals(connection.getAiringScheduleConnectionWithoutFieldName(), "{\nedges {\nid\n}\n}");
    }

    @Test
    void AiringScheduleConnectionBuilder_Nodes_NoException() {
        //given
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .mediaId()
                .buildAiringSchedule();

        //when
        AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
                .nodes(airingSchedule)
                .buildAiringScheduleConnection();

        //then
        assertEquals(connection.getAiringScheduleConnectionString(), "airingScheduleConnection {\nnodes {\nmediaId\n}\n}");
    }

    @Test
    void AiringScheduleConnectionBuilder_NodesWithoutFieldName_NoException() {
        //given
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .mediaId()
                .buildAiringSchedule();

        //when
        AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
                .nodes(airingSchedule)
                .buildAiringScheduleConnection();

        //then
        assertEquals(connection.getAiringScheduleConnectionWithoutFieldName(), "{\nnodes {\nmediaId\n}\n}");
    }

    @Test
    void AiringScheduleConnectionBuilder_PageInfo_NoException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .hasNextPage()
                .buildPageInfo();

        //when
        AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
                .pageInfo(pageInfo)
                .buildAiringScheduleConnection();

        //then
        assertEquals(connection.getAiringScheduleConnectionString(), "airingScheduleConnection {\npageInfo {\nhasNextPage\n}\n}");
    }

    @Test
    void AiringScheduleConnectionBuilder_PageInfoWithoutFieldName_NoException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .hasNextPage()
                .buildPageInfo();

        //when
        AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
                .pageInfo(pageInfo)
                .buildAiringScheduleConnection();

        //then
        assertEquals(connection.getAiringScheduleConnectionWithoutFieldName(), "{\npageInfo {\nhasNextPage\n}\n}");
    }

    @Test
    void AiringScheduleConnectionBuilder_EdgesAndPageInfo_NoException() {
        //given
        AiringScheduleEdge airingScheduleEdge = new AiringScheduleEdge();
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .lastPage()
                .buildPageInfo();

        //when
        AiringScheduleConnection connection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
                .edges(airingScheduleEdge)
                .pageInfo(pageInfo)
                .buildAiringScheduleConnection();

        //then
        assertEquals(connection.getAiringScheduleConnectionString(), "airingScheduleConnection {\nedges {\nid\n}\npageInfo {\nlastPage\n}\n}");
    }
}