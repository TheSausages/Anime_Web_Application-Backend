package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters;

import org.junit.jupiter.api.Test;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaSort;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType;

import static org.junit.jupiter.api.Assertions.*;

class CharacterArgumentsTest {
    @Test
    void CharacterArgumentsBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> CharacterArguments.getCharacterArgumentsBuilder().buildCharacterMediaArguments());

        //then
        assertEquals(exception.getMessage(), "Character Arguments should posses at least 1 parameter!");
    }

    @Test
    void CharacterArgumentsBuilder_MediaSort1Element_NoException() {
        //given
        CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ROLE};

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .mediaSort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(sort: [ROLE])");
    }

    @Test
    void CharacterArgumentsBuilder_ManyMediaSort1Element_NoException() {
        //given
        CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ROLE};
        CharacterSort[] sorts1 = new CharacterSort[]{CharacterSort.ID};

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .mediaSort(sorts)
                .mediaSort(sorts1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(sort: [ROLE])");
    }

    @Test
    void CharacterArgumentsBuilder_MediaSortManyElement_NoException() {
        //given
        CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ID, CharacterSort.RELEVANCE};

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .mediaSort(sorts)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(sort: [ID, RELEVANCE])");
    }

    @Test
    void CharacterArgumentsBuilder_ManyMediaSortManyElement_NoException() {
        //given
        CharacterSort[] sorts1 = new CharacterSort[]{CharacterSort.ID, CharacterSort.RELEVANCE};
        CharacterSort[] sorts = new CharacterSort[]{CharacterSort.FAVOURITES, CharacterSort.ROLE_DESC};

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .mediaSort(sorts)
                .mediaSort(sorts1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(sort: [FAVOURITES, ROLE_DESC])");
    }

    @Test
    void CharacterArgumentsBuilder_CharacterRole_NoException() {
        //given
        CharacterRole role = CharacterRole.MAIN;

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .role(role)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(role: MAIN)");
    }

    @Test
    void CharacterArgumentsBuilder_ManyCharacterRole_NoException() {
        //given
        CharacterRole role = CharacterRole.MAIN;
        CharacterRole role1 = CharacterRole.SUPPORTING;

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .role(role)
                .role(role1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(role: MAIN)");
    }

    @Test
    void CharacterArgumentsBuilder_Page_NoException() {
        //given
        int page = 1;

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .page(page)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(page: 1)");
    }

    @Test
    void CharacterArgumentsBuilder_ManyPage_NoException() {
        //given
        int page = 3;
        int page1 = 9;

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .page(page)
                .page(page1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(page: 3)");
    }

    @Test
    void CharacterArgumentsBuilder_PerPage_NoException() {
        //given
        int perPage = 2;

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .perPage(perPage)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(perPage: 2)");
    }

    @Test
    void CharacterArgumentsBuilder_ManyPerPage_NoException() {
        //given
        int perPage = 10;
        int perPage1 = 34;

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .perPage(perPage)
                .perPage(perPage1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(perPage: 10)");
    }

    @Test
    void CharacterArgumentsBuilder_PageAndManyPerPage_NoException() {
        //given
        int page = 5;
        int perPage = 10;
        int perPage1 = 34;

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .perPage(perPage)
                .page(page)
                .perPage(perPage1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(perPage: 10, page: 5)");
    }

    @Test
    void CharacterArgumentsBuilder_AllAndManyRoleAndManyPage_NoException() {
        //given
        int page = 5;
        int page1 = 10;
        int perPage = 14;
        CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ID, CharacterSort.ROLE_DESC};
        CharacterRole role = CharacterRole.BACKGROUND;
        CharacterRole role1 = CharacterRole.SUPPORTING;

        //when
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .perPage(perPage)
                .page(page)
                .role(role)
                .page(page1)
                .mediaSort(sorts)
                .role(role1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(arguments.getCharacterArgumentsString(), "(perPage: 14, page: 5, role: BACKGROUND, sort: [ID, ROLE_DESC])");
    }
}