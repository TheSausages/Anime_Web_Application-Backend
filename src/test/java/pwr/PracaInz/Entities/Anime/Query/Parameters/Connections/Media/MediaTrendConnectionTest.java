package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;

import static org.junit.jupiter.api.Assertions.*;

class MediaTrendConnectionTest {

    @Test
    void MediaConnectionBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> MediaConnection.getMediaConnectionBuilder().buildMediaConnection());

        //then
        assertEquals(exception.getMessage(), "Media Connection should posses at least 1 parameter!");
    }

    @Test
    void MediaConnectionBuilder_Edges_ThrowException() {
        //given
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .staffRole()
                .buildMediaEdge();

        //when
        MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
                .edge(edge)
                .buildMediaConnection();

        //then
        assertEquals(connection.getMediaConnectionString(), "mediaConnection {\nedges {\nstaffRole\n}\n}");
    }

    @Test
    void MediaConnectionBuilder_EdgesWithoutFieldName_ThrowException() {
        //given
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .staffRole()
                .buildMediaEdge();

        //when
        MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
                .edge(edge)
                .buildMediaConnection();

        //then
        assertEquals(connection.getMediaConnectionWithoutFieldName(), "{\nedges {\nstaffRole\n}\n}");
    }

    @Test
    void MediaConnectionBuilder_Nodes_ThrowException() {
        //given


        //when
        /*MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
                .nodes()
                .buildMediaConnection();

        //then
        assertEquals(connection.getMediaConnectionString(), "mediaConnection {\nedges {\nstaffRole\n}\n}");*/
    }

    @Test
    void MediaConnectionBuilder_PageInfo_ThrowException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .perPage()
                .buildPageInfo();

        //when
        MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
                .pageInfo(pageInfo)
                .buildMediaConnection();

        //then
        assertEquals(connection.getMediaConnectionString(), "mediaConnection {\npageInfo {\nperPage\n}\n}");
    }
}