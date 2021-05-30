package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Studio;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;

import static org.junit.jupiter.api.Assertions.*;

class StudioConnectionTest {

    @Test
    void StudioConnectionBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> StudioConnection.getStudioConnectionBuilder().buildStudioConnection());

        //then
        assertEquals(exception.getMessage(), "Studio Connection should posses at least 1 parameter!");
    }

    @Test
    void StudioConnectionBuilder_Edges_NoException() {
        //given
        StudioEdge edge = StudioEdge.getStudioEdgedBuilder()
                .id()
                .buildStudioEdge();

        //when
        StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
                .edges(edge)
                .buildStudioConnection();

        //then
        assertEquals(studioConnection.getStudioConnectionString(), "studioConnection {\nedges {\nid\n}\n}");
    }

    @Test
    void StudioConnectionBuilder_ManyEdges_NoException() {
        //given
        StudioEdge edge = StudioEdge.getStudioEdgedBuilder()
                .favouriteOrder()
                .buildStudioEdge();
        StudioEdge edge1 = StudioEdge.getStudioEdgedBuilder()
                .id()
                .buildStudioEdge();

        //when
        StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
                .edges(edge)
                .edges(edge1)
                .buildStudioConnection();

        //then
        assertEquals(studioConnection.getStudioConnectionString(), "studioConnection {\nedges {\nfavouriteOrder\n}\n}");
    }

    @Test
    void StudioConnectionBuilder_EdgesWithoutFieldName_NoException() {
        //given
        StudioEdge edge = StudioEdge.getStudioEdgedBuilder()
                .id()
                .buildStudioEdge();

        //when
        StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
                .edges(edge)
                .buildStudioConnection();

        //then
        assertEquals(studioConnection.getStudioConnectionWithoutFieldName(), "{\nedges {\nid\n}\n}");
    }

    @Test
    void StudioConnectionBuilder_ManyEdgesWithoutFieldName_NoException() {
        //given
        StudioEdge edge = StudioEdge.getStudioEdgedBuilder()
                .favouriteOrder()
                .buildStudioEdge();
        StudioEdge edge1 = StudioEdge.getStudioEdgedBuilder()
                .id()
                .buildStudioEdge();

        //when
        StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
                .edges(edge)
                .edges(edge1)
                .buildStudioConnection();

        //then
        assertEquals(studioConnection.getStudioConnectionWithoutFieldName(), "{\nedges {\nfavouriteOrder\n}\n}");
    }

    @Test
    void StudioConnectionBuilder_Nodes_NoException() {
        //given
        Studio studio = Studio.getStudioBuilder()
                .name()
                .buildStudio();

        //when
        StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
                .nodes(studio)
                .buildStudioConnection();

        //then
        assertEquals(studioConnection.getStudioConnectionString(), "studioConnection {\nnodes {\nname\n}\n}");
    }

    @Test
    void StudioConnectionBuilder_ManyNodes_NoException() {
        //given
        Studio studio = Studio.getStudioBuilder()
                .siteUrl()
                .buildStudio();
        Studio studio1 = Studio.getStudioBuilder()
                .name()
                .buildStudio();

        //when
        StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
                .nodes(studio)
                .nodes(studio1)
                .buildStudioConnection();

        //then
        assertEquals(studioConnection.getStudioConnectionString(), "studioConnection {\nnodes {\nsiteUrl\n}\n}");
    }

    @Test
    void StudioConnectionBuilder_PageInfo_NoException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .total()
                .buildPageInfo();

        //when
        StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
                .pageInfo(pageInfo)
                .buildStudioConnection();

        //then
        assertEquals(studioConnection.getStudioConnectionString(), "studioConnection {\npageInfo {\ntotal\n}\n}");
    }

    @Test
    void StudioConnectionBuilder_ManyPageInfo_NoException() {
        //given
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .lastPage()
                .buildPageInfo();
        PageInfo pageInfo1 = PageInfo.getPageInfoBuilder()
                .total()
                .buildPageInfo();

        //when
        StudioConnection studioConnection = StudioConnection.getStudioConnectionBuilder()
                .pageInfo(pageInfo)
                .pageInfo(pageInfo1)
                .buildStudioConnection();

        //then
        assertEquals(studioConnection.getStudioConnectionString(), "studioConnection {\npageInfo {\nlastPage\n}\n}");
    }
}