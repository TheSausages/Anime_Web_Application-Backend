package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AiringScheduleArgumentsTest {

    @Test
    void AiringScheduleArgumentsBuilder_NoParams_throwException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> AiringScheduleArguments.getAiringScheduleArgumentsBuilder().buildCharacterMediaArguments());

        //then
        assertEquals(exception.getMessage(), "Airing Schedule Arguments should posses at least 1 parameter!");
    }

    @Test
    void AiringScheduleArgumentsBuilder_NotYetAired_NoException() {
        //given

        //when
        AiringScheduleArguments airingScheduleArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
                .notYetAired()
                .buildCharacterMediaArguments();

        //then
        assertEquals(airingScheduleArguments.getAiringScheduleArgumentsString(), "(notYetAired: true)");
    }

    @Test
    void AiringScheduleArgumentsBuilder_ManyNotYetAired_NoException() {
        //given

        //when
        AiringScheduleArguments airingScheduleArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
                .notYetAired()
                .notYetAired()
                .notYetAired()
                .buildCharacterMediaArguments();

        //then
        assertEquals(airingScheduleArguments.getAiringScheduleArgumentsString(), "(notYetAired: true)");
    }

    @Test
    void AiringScheduleArgumentsBuilder_Page_NoException() {
        //given
        int page = 5;

        //when
        AiringScheduleArguments airingScheduleArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
                .page(page)
                .buildCharacterMediaArguments();

        //then
        assertEquals(airingScheduleArguments.getAiringScheduleArgumentsString(), "(page: 5)");
    }

    @Test
    void AiringScheduleArgumentsBuilder_ManyPage_NoException() {
        //given
        int page1 = 5;
        int page = 15;

        //when
        AiringScheduleArguments airingScheduleArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
                .page(page)
                .page(page1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(airingScheduleArguments.getAiringScheduleArgumentsString(), "(page: 15)");
    }

    @Test
    void AiringScheduleArgumentsBuilder_PerPage_NoException() {
        //given
        int perPage = 9;

        //when
        AiringScheduleArguments airingScheduleArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
                .perPage(perPage)
                .buildCharacterMediaArguments();

        //then
        assertEquals(airingScheduleArguments.getAiringScheduleArgumentsString(), "(perPage: 9)");
    }

    @Test
    void AiringScheduleArgumentsBuilder_ManyPerPage_NoException() {
        //given
        int perPage = 5;
        int perPage1 = 19;

        //when
        AiringScheduleArguments airingScheduleArguments = AiringScheduleArguments.getAiringScheduleArgumentsBuilder()
                .perPage(perPage)
                .perPage(perPage1)
                .buildCharacterMediaArguments();

        //then
        assertEquals(airingScheduleArguments.getAiringScheduleArgumentsString(), "(perPage: 5)");
    }
}