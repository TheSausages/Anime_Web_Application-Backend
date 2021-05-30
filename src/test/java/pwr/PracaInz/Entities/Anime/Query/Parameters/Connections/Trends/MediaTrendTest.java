package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Trends;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediaTrendTest {

    @Test
    void MediaTrendBuilder_NoParams_throwException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> MediaTrend.getMediaTrendBuilder().buildMediaTrendEdge());

        //then
        assertEquals(exception.getMessage(), "Media Trend should posses at least 1 parameter!");
    }

    @Test
    void MediaTrendBuilder_MediaId_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .mediaId()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\nmediaId\n}");
    }

    @Test
    void MediaTrendBuilder_ManyMediaId_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .mediaId()
                .mediaId()
                .mediaId()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\nmediaId\n}");
    }

    @Test
    void MediaTrendBuilder_MediaIdWithoutFieldName_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .mediaId()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getStudioEdgeWithoutFieldName(), "{\nmediaId\n}");
    }

    @Test
    void MediaTrendBuilder_ManyMediaIdWithoutFieldName_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .mediaId()
                .mediaId()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getStudioEdgeWithoutFieldName(), "{\nmediaId\n}");
    }

    @Test
    void MediaTrendBuilder_Date_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .date()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\ndate\n}");
    }

    @Test
    void MediaTrendBuilder_ManyDate_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .date()
                .date()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\ndate\n}");
    }

    @Test
    void MediaTrendBuilder_Trending_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .trending()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\ntrending\n}");
    }

    @Test
    void MediaTrendBuilder_ManyTrending_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .trending()
                .trending()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\ntrending\n}");
    }

    @Test
    void MediaTrendBuilder_AverageScore_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .averageScore()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\naverageScore\n}");
    }

    @Test
    void MediaTrendBuilder_ManyAverageScore_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .averageScore()
                .averageScore()
                .averageScore()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\naverageScore\n}");
    }

    @Test
    void MediaTrendBuilder_Popularity_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .popularity()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\npopularity\n}");
    }

    @Test
    void MediaTrendBuilder_ManyPopularity_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .popularity()
                .popularity()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\npopularity\n}");
    }

    @Test
    void MediaTrendBuilder_InProgress_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .inProgress()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\ninProgress\n}");
    }

    @Test
    void MediaTrendBuilder_ManyInProgress_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .inProgress()
                .inProgress()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\ninProgress\n}");
    }

    @Test
    void MediaTrendBuilder_Releasing_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .releasing()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\nreleasing\n}");
    }

    @Test
    void MediaTrendBuilder_ManyReleasing_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .releasing()
                .releasing()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\nreleasing\n}");
    }

    @Test
    void MediaTrendBuilder_Episode_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .episode()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\nepisode\n}");
    }

    @Test
    void MediaTrendBuilder_ManyEpisode_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .episode()
                .episode()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\nepisode\n}");
    }

    @Test
    void MediaTrendBuilder_Media_NoException() {
        //given

        //when

        //then

    }

    @Test
    void MediaTrendBuilder_MediaIdAndReleasing_NoException() {
        //given

        //when
        MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
                .mediaId()
                .releasing()
                .buildMediaTrendEdge();

        //then
        assertEquals(mediaTrend.getMediaTrendString(), "mediaTrend {\nmediaId\nreleasing\n}");
    }
}