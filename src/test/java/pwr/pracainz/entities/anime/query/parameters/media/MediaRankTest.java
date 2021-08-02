package pwr.pracainz.entities.anime.query.parameters.media;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MediaRankTest {
    @Test
    void MediaRankBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> MediaRank.getMediaRankBuilder()
                .buildMediaRank());

        //then
        assertEquals(exception.getMessage(), "Ranking should posses at least 1 parameter!");
    }

    @Test
    void MediaRankBuilder_Id_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nid\n}");
    }

    @Test
    void MediaRankBuilder_ManyId_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .id()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nid\n}");
    }

    @Test
    void MediaRankBuilder_Rank_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .rank()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nrank\n}");
    }

    @Test
    void MediaRankBuilder_ManyRank_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .rank()
                .rank()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nrank\n}");
    }

    @Test
    void MediaRankBuilder_Type_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .type()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\ntype\n}");
    }

    @Test
    void MediaRankBuilder_ManyType_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .type()
                .type()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\ntype\n}");
    }

    @Test
    void MediaRankBuilder_Format_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .format()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nformat\n}");
    }

    @Test
    void MediaRankBuilder_ManyFormat_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .format()
                .format()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nformat\n}");
    }

    @Test
    void MediaRankBuilder_Year_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .year()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nyear\n}");
    }

    @Test
    void MediaRankBuilder_ManyYear_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .year()
                .year()
                .year()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nyear\n}");
    }

    @Test
    void MediaRankBuilder_Season_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .season()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nseason\n}");
    }

    @Test
    void MediaRankBuilder_ManySeason_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .season()
                .season()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nseason\n}");
    }

    @Test
    void MediaRankBuilder_AllTime_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .allTime()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nallTime\n}");
    }

    @Test
    void MediaRankBuilder_ManyAllTime_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .allTime()
                .allTime()
                .allTime()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nallTime\n}");
    }

    @Test
    void MediaRankBuilder_Context_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .context()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\ncontext\n}");
    }

    @Test
    void MediaRankBuilder_ManyContext_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .context()
                .context()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\ncontext\n}");
    }

    @Test
    void MediaRankBuilder_IdAndRank_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .rank()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nid\nrank\n}");
    }

    @Test
    void MediaRankBuilder_IdAndTypeAndFormat_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .type()
                .format()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nid\ntype\nformat\n}");
    }

    @Test
    void MediaRankBuilder_ManySeasonAndTypeAndFormat_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .season()
                .type()
                .format()
                .season()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nseason\ntype\nformat\n}");
    }

    @Test
    void MediaRankBuilder_YearAndSeasonAndAllTimeAndContext_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .year()
                .season()
                .allTime()
                .context()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nyear\nseason\nallTime\ncontext\n}");
    }

    @Test
    void MediaRankBuilder_All_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .rank()
                .type()
                .format()
                .year()
                .season()
                .allTime()
                .context()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nid\nrank\ntype\nformat\nyear\nseason\nallTime\ncontext\n}");
    }

    @Test
    void MediaRankBuilder_AllWithManyTypeAndContext_NoException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .rank()
                .type()
                .format()
                .year()
                .season()
                .allTime()
                .context()
                .type()
                .context()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nid\nrank\ntype\nformat\nyear\nseason\nallTime\ncontext\n}");
    }
}