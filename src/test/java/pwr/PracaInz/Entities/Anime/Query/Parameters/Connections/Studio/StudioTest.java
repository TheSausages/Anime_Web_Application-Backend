package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Studio;

import org.junit.jupiter.api.Test;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.Character;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaArguments;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaEdge;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType;

import static org.junit.jupiter.api.Assertions.*;

class StudioTest {

    @Test
    void StudioBuilder_NoParams_throwException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> Studio.getStudioBuilder().buildStudio());

        //then
        assertEquals(exception.getMessage(), "Studio should posses at least 1 parameter!");
    }

    @Test
    void StudioBuilder_Id_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .id()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nid\n}");
    }

    @Test
    void StudioBuilder_ManyId_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .id()
                .id()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nid\n}");
    }

    @Test
    void StudioBuilder_IdWithoutFieldName_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .id()
                .buildStudio();

        //then
        assertEquals(studio.getStudioWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void StudioBuilder_ManyIdWithoutFieldName_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .id()
                .id()
                .buildStudio();

        //then
        assertEquals(studio.getStudioWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void StudioBuilder_Name_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .name()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nname\n}");
    }

    @Test
    void StudioBuilder_ManyName_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .name()
                .name()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nname\n}");
    }

    @Test
    void StudioBuilder_IsAnimationStudio_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .isAnimationStudio()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nisAnimationStudio\n}");
    }

    @Test
    void StudioBuilder_ManyIsAnimationStudio_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .isAnimationStudio()
                .isAnimationStudio()
                .isAnimationStudio()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nisAnimationStudio\n}");
    }

    @Test
    void StudioBuilder_SiteUrl_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .siteUrl()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nsiteUrl\n}");
    }

    @Test
    void StudioBuilder_ManySiteUrl_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .siteUrl()
                .siteUrl()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nsiteUrl\n}");
    }

    @Test
    void StudioBuilder_Favourites_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .favourites()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nfavourites\n}");
    }

    @Test
    void StudioBuilder_ManyFavourites_NoException() {
        //given

        //when
        Studio studio = Studio.getStudioBuilder()
                .favourites()
                .favourites()
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nfavourites\n}");
    }

    @Test
    void StudioBuilder_Media_NoException() {
        //given
        Character character = Character.getCharacterBuilder()
                .id()
                .buildCharacter();
        MediaEdge mediaEdge = MediaEdge.getMediaConnectionBuilder()
                .characters(character)
                .buildMediaEdge();
        MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
                .edge(mediaEdge)
                .buildMediaConnection();

        //when
        Studio studio = Studio.getStudioBuilder()
                .media(connection)
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nmedia {\nedges {\ncharacters {\nid\n}\n}\n}\n}");
    }

    @Test
    void StudioBuilder_ManyMedia_NoException() {
        //given
        Character character1 = Character.getCharacterBuilder()
                .id()
                .buildCharacter();
        MediaEdge mediaEdge1 = MediaEdge.getMediaConnectionBuilder()
                .characters(character1)
                .buildMediaEdge();
        MediaConnection connection1 = MediaConnection.getMediaConnectionBuilder()
                .edge(mediaEdge1)
                .buildMediaConnection();
        MediaEdge mediaEdge = MediaEdge.getMediaConnectionBuilder()
                .characterRole()
                .buildMediaEdge();
        MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
                .edge(mediaEdge)
                .buildMediaConnection();

        //when
        Studio studio = Studio.getStudioBuilder()
                .media(connection)
                .media(connection1)
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nmedia {\nedges {\ncharacterRole\n}\n}\n}");
    }

    @Test
    void StudioBuilder_MediaWithMediaArguments_NoException() {
        //given
        MediaArguments mediaArguments = MediaArguments.getMediaArgumentsBuilder()
                .onList()
                .buildCharacterMediaArguments();
        Character character = Character.getCharacterBuilder()
                .name()
                .buildCharacter();
        MediaEdge mediaEdge = MediaEdge.getMediaConnectionBuilder()
                .characters(character)
                .buildMediaEdge();
        MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
                .edge(mediaEdge)
                .buildMediaConnection();

        //when
        Studio studio = Studio.getStudioBuilder()
                .media(mediaArguments, connection)
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nmedia(onList: true) {\nedges {\ncharacters {\nname {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}\n}\n}\n}\n}");
    }

    @Test
    void StudioBuilder_ManyMediaWithMediaArguments_NoException() {
        //given
        MediaArguments mediaArguments = MediaArguments.getMediaArgumentsBuilder()
                .type(MediaType.ANIME)
                .buildCharacterMediaArguments();
        MediaEdge mediaEdge = MediaEdge.getMediaConnectionBuilder()
                .isMainStudio()
                .buildMediaEdge();
        MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
                .edge(mediaEdge)
                .buildMediaConnection();

        //when
        Studio studio = Studio.getStudioBuilder()
                .media(mediaArguments, connection)
                .buildStudio();

        //then
        assertEquals(studio.getStudioString(), "studio {\nmedia(type: ANIME) {\nedges {\nisMainStudio\n}\n}\n}");
    }
}