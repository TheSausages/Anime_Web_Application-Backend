package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediaRankTest {
    @Test
    void MediaRankBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            MediaRank.getMediaRankBuilder()
                    .buildMediaRank();
        });

        //then
        assertEquals(exception.getMessage(), "Ranking should posses at least 1 parameter!");
    }

    @Test
    void MediaRankBuilder_Id_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nid\n}");
    }

    @Test
    void MediaRankBuilder_Rank_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .rankNumber()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nrank\n}");
    }

    @Test
    void MediaRankBuilder_Type_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .type()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\ntype\n}");
    }

    @Test
    void MediaRankBuilder_Format_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .format()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nformat\n}");
    }

    @Test
    void MediaRankBuilder_Year_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .year()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nyear\n}");
    }

    @Test
    void MediaRankBuilder_Season_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .season()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nseason\n}");
    }

    @Test
    void MediaRankBuilder_Alltime_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .allTime()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nallTime\n}");
    }

    @Test
    void MediaRankBuilder_Context_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .context()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\ncontext\n}");
    }

    @Test
    void MediaRankBuilder_IdAndRank_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .rankNumber()
                .buildMediaRank();

        //then
        assertEquals(rank.getMediaRankString(), "ranking {\nid\nrank\n}");
    }

    @Test
    void MediaRankBuilder_IdAndTypeAndFormat_ThrowException() {
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
    void MediaRankBuilder_YearAndSeasonAndAllTimeAndContext_ThrowException() {
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
    void MediaRankBuilder_All_ThrowException() {
        //given

        //when
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .id()
                .rankNumber()
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
}