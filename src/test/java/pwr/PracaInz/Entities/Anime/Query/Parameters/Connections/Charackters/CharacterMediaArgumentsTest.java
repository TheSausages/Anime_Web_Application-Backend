package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters;

import org.junit.jupiter.api.Test;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaSort;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaTitle;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterMediaArgumentsTest {

    @Test
    void CharacterMediaArgumentsBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> CharacterMediaArguments.getCharacterMediaArgumentsBuilder().buildCharacterMediaArguments());

        //then
        assertEquals(exception.getMessage(), "Character should posses at least 1 parameter!");
    }

    @Test
    void CharacterMediaArgumentsBuilder_MediaSort1Element_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.EPISODES};

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .mediaSort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [EPISODES])");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyMediaSort1Element_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.EPISODES};

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .mediaSort(sorts)
                .mediaSort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [EPISODES])");
    }

    @Test
    void CharacterMediaArgumentsBuilder_MediaSortManyElement_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.EPISODES, MediaSort.ID};

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .mediaSort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [EPISODES, ID])");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyMediaSortManyElement_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.EPISODES, MediaSort.ID};

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .mediaSort(sorts)
                .mediaSort(sorts)
                .mediaSort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [EPISODES, ID])");
    }

    @Test
    void CharacterMediaArgumentsBuilder_Type_NoException() {
        //given
        MediaType type = MediaType.ANIME;

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .type(type)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(type: ANIME)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyType_NoException() {
        //given
        MediaType type = MediaType.ANIME;

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .type(type)
                .type(type)
                .type(type)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(type: ANIME)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_OnList_NoException() {
        //given

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .onList()
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(onList: true)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyOnList_NoException() {
        //given

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .onList()
                .onList()
                .onList()
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(onList: true)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_Page_NoException() {
        //given
        int page = 2;

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .page(page)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(page: 2)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyPage_NoException() {
        //given
        int page = 2;
        int page1 = 25;

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .page(page)
                .page(page1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(page: 2)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_PerPage_NoException() {
        //given
        int perPage = 9;


        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .perPage(perPage)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(perPage: 9)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_ManyPerPage_NoException() {
        //given
        int perPage = 8;
        int perPage1 = 12;

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .perPage(perPage)
                .perPage(perPage1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(perPage: 8)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_PerPageAndPage_NoException() {
        //given
        int perPage = 8;
        int page = 3;

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .perPage(perPage)
                .page(page)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(perPage: 8, page: 3)");
    }

    @Test
    void CharacterMediaArgumentsBuilder_MediaSortManyElementsAndTypeAndManyOnList_NoException() {
        //given
        MediaSort[] sorts = new MediaSort[]{MediaSort.ID, MediaSort.TYPE, MediaSort.SCORE};
        MediaType type = MediaType.MANGA;

        //when
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .mediaSort(sorts)
                .type(type)
                .onList()
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [ID, TYPE, SCORE], type: MANGA, onList: true)");
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
        CharacterMediaArguments arguments = CharacterMediaArguments.getCharacterMediaArgumentsBuilder()
                .mediaSort(sorts)
                .type(type)
                .onList()
                .perPage(perPage)
                .type(type1)
                .page(page)
                .perPage(perPage1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [ID, START_DATE_DESC, END_DATE], type: ANIME, onList: true, perPage: 2, page: 6)");
    }
}