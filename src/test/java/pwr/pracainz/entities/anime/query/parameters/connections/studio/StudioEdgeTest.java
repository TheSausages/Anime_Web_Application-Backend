package pwr.pracainz.entities.anime.query.parameters.connections.studio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudioEdgeTest {

    @Test
    void StudioEdgedBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> StudioEdge.getStudioEdgedBuilder().buildStudioEdge());

        //then
        assertEquals(exception.getMessage(), "Studio Edge should posses at least 1 parameter!");
    }

    @Test
    void StudioEdgedBuilder_Node_NoException() {
        //given
        Studio studio = Studio.getStudioBuilder()
                .id()
                .buildStudio();

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .node(studio)
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeString(), "studioEdge {\nnode {\nid\n}\n}");
    }

    @Test
    void StudioEdgedBuilder_ManyNode_NoException() {
        //given
        Studio studio1 = Studio.getStudioBuilder()
                .id()
                .buildStudio();
        Studio studio = Studio.getStudioBuilder()
                .name()
                .buildStudio();

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .node(studio)
                .node(studio1)
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeString(), "studioEdge {\nnode {\nname\n}\n}");
    }

    @Test
    void StudioEdgedBuilder_NodeWithoutFieldName_NoException() {
        //given
        Studio studio = Studio.getStudioBuilder()
                .id()
                .buildStudio();

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .node(studio)
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeWithoutFieldName(), "{\nnode {\nid\n}\n}");
    }

    @Test
    void StudioEdgedBuilder_ManyNodeWithoutFieldName_NoException() {
        //given
        Studio studio1 = Studio.getStudioBuilder()
                .id()
                .buildStudio();
        Studio studio = Studio.getStudioBuilder()
                .name()
                .buildStudio();

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .node(studio)
                .node(studio1)
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeWithoutFieldName(), "{\nnode {\nname\n}\n}");
    }

    @Test
    void StudioEdgedBuilder_Id_NoException() {
        //given

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .id()
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeString(), "studioEdge {\nid\n}");
    }

    @Test
    void StudioEdgedBuilder_ManyId_NoException() {
        //given

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .id()
                .id()
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeString(), "studioEdge {\nid\n}");
    }

    @Test
    void StudioEdgedBuilder_IsMain_NoException() {
        //given

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .isMain()
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeString(), "studioEdge {\nisMain\n}");
    }

    @Test
    void StudioEdgedBuilder_ManyIsMain_NoException() {
        //given

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .isMain()
                .isMain()
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeString(), "studioEdge {\nisMain\n}");
    }

    @Test
    void StudioEdgedBuilder_FavouriteOrder_NoException() {
        //given

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .favouriteOrder()
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeString(), "studioEdge {\nfavouriteOrder\n}");
    }

    @Test
    void StudioEdgedBuilder_ManyFavouriteOrder_NoException() {
        //given

        //when
        StudioEdge studioEdge = StudioEdge.getStudioEdgedBuilder()
                .favouriteOrder()
                .favouriteOrder()
                .buildStudioEdge();

        //then
        assertEquals(studioEdge.getStudioEdgeString(), "studioEdge {\nfavouriteOrder\n}");
    }
}