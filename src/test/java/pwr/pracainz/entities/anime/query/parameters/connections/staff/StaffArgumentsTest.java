package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaffArgumentsTest {

    @Test
    void StaffArgumentsBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> StaffArguments.getStaffArgumentsBuilder().buildStaffArguments());

        //then
        assertEquals(exception.getMessage(), "Staff Arguments should posses at least 1 parameter!");
    }

    @Test
    void StaffArgumentBuilder_StaffSortArray1Element_NoException() {
        //given
        StaffSort[] sorts = new StaffSort[]{StaffSort.ROLE};

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .sort(sorts)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(sort: [ROLE])");
    }

    @Test
    void StaffArgumentBuilder_ManyStaffSortArray1Element_NoException() {
        //given
        StaffSort[] sorts = new StaffSort[]{StaffSort.ROLE};
        StaffSort[] sorts1 = new StaffSort[]{StaffSort.ID};

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .sort(sorts)
                .sort(sorts1)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(sort: [ROLE])");
    }

    @Test
    void StaffArgumentBuilder_StaffSortArrayManyElement_NoException() {
        //given
        StaffSort[] sorts = new StaffSort[]{StaffSort.ROLE, StaffSort.RELEVANCE};

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .sort(sorts)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(sort: [ROLE, RELEVANCE])");
    }

    @Test
    void StaffArgumentBuilder_ManyStaffSortArrayManyElement_NoException() {
        //given
        StaffSort[] sorts = new StaffSort[]{StaffSort.FAVOURITES_DESC, StaffSort.SEARCH_MATCH};
        StaffSort[] sorts1 = new StaffSort[]{StaffSort.ROLE, StaffSort.RELEVANCE};

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .sort(sorts)
                .sort(sorts1)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(sort: [FAVOURITES_DESC, SEARCH_MATCH])");
    }

    @Test
    void StaffArgumentBuilder_ManyPage_NoException() {
        //given
        int page1 = 5;
        int page = 9;

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .page(page)
                .page(page1)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(page: 9)");
    }

    @Test
    void StaffArgumentBuilder_PerPage_NoException() {
        //given
        int perPage = 5;

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .perPage(perPage)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(perPage: 5)");
    }

    @Test
    void StaffArgumentBuilder_ManyPerPage_NoException() {
        //given
        int perPage = 10;
        int perPage1 = 5;

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .perPage(perPage)
                .perPage(perPage1)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(perPage: 10)");
    }

    @Test
    void StaffArgumentBuilder_PageAndManyPerPage_NoException() {
        //given
        int page = 2;
        int perPage = 13;
        int perPage1 = 25;

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .page(page)
                .perPage(perPage)
                .perPage(perPage1)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(page: 2, perPage: 13)");
    }

    @Test
    void StaffArgumentBuilder_AllAndManyPage_NoException() {
        //given
        StaffSort[] sorts = new StaffSort[]{StaffSort.RELEVANCE, StaffSort.ID};
        int page = 3;
        int page1 = 13;
        int perPage = 25;

        //then
        StaffArguments arguments = StaffArguments.getStaffArgumentsBuilder()
                .page(page)
                .perPage(perPage)
                .sort(sorts)
                .page(page1)
                .buildStaffArguments();

        //
        assertEquals(arguments.getStaffArgumentsString(), "(page: 3, perPage: 25, sort: [RELEVANCE, ID])");
    }
}