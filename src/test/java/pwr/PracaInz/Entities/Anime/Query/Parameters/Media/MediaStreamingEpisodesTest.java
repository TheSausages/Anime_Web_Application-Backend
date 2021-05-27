package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediaStreamingEpisodesTest {
    @Test
    void MediaStreamingEpisodeBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> {
           MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                   .buildMediaRank();
        });

        assertEquals(exception.getMessage(), "Streaming Episodes should posses at least 1 parameter!");
    }

    @Test
    void MediaStreamingEpisodeBuilder_Title_ThrowException() {
        //given

        //when
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .title()
                .buildMediaRank();

        assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\ntitle\n}");
    }

    @Test
    void MediaStreamingEpisodeBuilder_Url_ThrowException() {
        //given

        //when
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .url()
                .buildMediaRank();

        assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nurl\n}");
    }

    @Test
    void MediaStreamingEpisodeBuilder_Site_ThrowException() {
        //given

        //when
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .site()
                .buildMediaRank();

        assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nsite\n}");
    }

    @Test
    void MediaStreamingEpisodeBuilder_Thumbnail_ThrowException() {
        //given

        //when
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .thumbnail()
                .buildMediaRank();

        assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nthumbnail\n}");
    }

    @Test
    void MediaStreamingEpisodeBuilder_TitleAndSite_ThrowException() {
        //given

        //when
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .title()
                .site()
                .buildMediaRank();

        assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\ntitle\nsite\n}");
    }

    @Test
    void MediaStreamingEpisodeBuilder_ThumbnailAndUrlAndTitle_ThrowException() {
        //given

        //when
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .thumbnail()
                .url()
                .title()
                .buildMediaRank();

        assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nthumbnail\nurl\ntitle\n}");
    }

    @Test
    void MediaStreamingEpisodeBuilder_All_ThrowException() {
        //given

        //when
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .thumbnail()
                .title()
                .url()
                .site()
                .buildMediaRank();

        assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nthumbnail\ntitle\nurl\nsite\n}");
    }
}