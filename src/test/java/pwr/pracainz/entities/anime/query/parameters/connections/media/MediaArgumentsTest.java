package pwr.pracainz.entities.anime.query.parameters.connections.media;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSort;
import pwr.pracainz.entities.anime.query.parameters.media.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MediaArgumentsTest {

    @Test
    void CharacterMediaArgumentsBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> MediaArguments.getMediaArgumentsBuilder().buildCharacterMediaArguments());

        //then
        assertEquals(exception.getMessage(), "Media Arguments should posses at least 1 parameter!");
    }

    @Test
    void CharacterMediaArgumentsBuilder_MediaSort1Element_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.EPISODES};

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .sort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(sort: [EPISODES])");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyMediaSort1Element_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.EPISODES};

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .sort(sorts)
                .sort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(sort: [EPISODES])");
    }

    @Test
    void CharacterMediaArgumentsBuilder_MediaSortManyElement_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.EPISODES, MediaSort.ID};

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .sort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(sort: [EPISODES, ID])");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyMediaSortManyElement_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.EPISODES, MediaSort.ID};

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .sort(sorts)
                .sort(sorts)
                .sort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(sort: [EPISODES, ID])");
    }

    @Test
    void CharacterMediaArgumentsBuilder_Type_NoException() {
        //given
        MediaType type = MediaType.ANIME;

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .type(type)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(type: ANIME)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyType_NoException() {
        //given
        MediaType type = MediaType.ANIME;

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .type(type)
                .type(type)
                .type(type)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(type: ANIME)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_OnList_NoException() {
        //given

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .onList()
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(onList: true)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyOnList_NoException() {
        //given

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .onList()
                .onList()
                .onList()
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(onList: true)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_Page_NoException() {
        //given
        int page = 2;

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .page(page)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(page: 2)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyPage_NoException() {
        //given
        int page = 2;
        int page1 = 25;

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .page(page)
                .page(page1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(page: 2)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_PerPage_NoException() {
        //given
        int perPage = 9;


        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .perPage(perPage)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(perPage: 9)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyPerPage_NoException() {
        //given
        int perPage = 8;
        int perPage1 = 12;

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .perPage(perPage)
                .perPage(perPage1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(perPage: 8)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_PerPageAndPage_NoException() {
        //given
        int perPage = 8;
        int page = 3;

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .perPage(perPage)
                .page(page)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(perPage: 8, page: 3)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_MediaSortManyElementsAndTypeAndManyOnList_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.ID, MediaSort.TYPE, MediaSort.SCORE};
        MediaType type = MediaType.MANGA;

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .sort(sorts)
                .type(type)
                .onList()
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(sort: [ID, TYPE, SCORE], type: MANGA, onList: true)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_AllAndManyPerPageAndManyType_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.ID, MediaSort.START_DATE_DESC, MediaSort.END_DATE};
        MediaType type = MediaType.ANIME;
        MediaType type1 = MediaType.MANGA;
        int page = 6;
        int perPage = 2;
        int perPage1 = 18;

        //when
        MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
                .sort(sorts)
                .type(type)
                .onList()
                .perPage(perPage)
                .type(type1)
                .page(page)
                .perPage(perPage1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getMediaArgumentsString(), "(sort: [ID, START_DATE_DESC, END_DATE], type: ANIME, onList: true, perPage: 2, page: 6)");
    }
}