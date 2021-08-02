package pwr.pracainz.entities.anime.query.parameters.connections.airingSchedule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AiringScheduleTest {

    @Test
    void AiringScheduleBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> AiringSchedule.getAiringScheduleBuilder().buildAiringSchedule());

        //then
        assertEquals(exception.getMessage(), "Airing Schedule should posses at least 1 parameter!");
    }

    @Test
    void AiringScheduleBuilder_Id_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .id()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nid\n}");
    }

    @Test
    void AiringScheduleBuilder_ManyId_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .id()
                .id()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nid\n}");
    }

    @Test
    void AiringScheduleBuilder_IdWithoutFieldName_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .id()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleStringStringWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void AiringScheduleBuilder_ManyIdWithoutFieldName_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .id()
                .id()
                .id()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleStringStringWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void AiringScheduleBuilder_AiringAt_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .airingAt()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nairingAt\n}");
    }

    @Test
    void AiringScheduleBuilder_ManyAiringAt_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .airingAt()
                .airingAt()
                .airingAt()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nairingAt\n}");
    }

    @Test
    void AiringScheduleBuilder_TimeUntilAiring_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .timeUntilAiring()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\ntimeUntilAiring\n}");
    }

    @Test
    void AiringScheduleBuilder_ManyTimeUntilAiring_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .timeUntilAiring()
                .timeUntilAiring()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\ntimeUntilAiring\n}");
    }

    @Test
    void AiringScheduleBuilder_Episode_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .episode()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nepisode\n}");
    }

    @Test
    void AiringScheduleBuilder_ManyEpisode_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .episode()
                .episode()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nepisode\n}");
    }

    @Test
    void AiringScheduleBuilder_MediaId_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .mediaId()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nmediaId\n}");
    }

    @Test
    void AiringScheduleBuilder_ManyMediaId_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .mediaId()
                .mediaId()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nmediaId\n}");
    }

    @Test
    void AiringScheduleBuilder_IdAndMediaId_NoException() {
        //given

        //when
        AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
                .id()
                .mediaId()
                .buildAiringSchedule();

        //then
        assertEquals(airingSchedule.getAiringScheduleString(), "airingSchedule {\nid\nmediaId\n}");
    }
}