package pwr.pracainz.entities.anime.query.parameters.media;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MediaStreamingEpisodesTest {
	@Test
	void MediaStreamingEpisodeBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.buildMediaRank());

		assertEquals(exception.getMessage(), "Streaming Episodes should posses at least 1 parameter!");
	}

	@Test
	void MediaStreamingEpisodeBuilder_Title_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.title()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\ntitle\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_ManyTitle_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.title()
				.title()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\ntitle\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_Url_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.url()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nurl\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_ManyUrl_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.url()
				.url()
				.url()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nurl\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_Site_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.site()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nsite\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_ManySite_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.site()
				.site()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nsite\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_Thumbnail_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.thumbnail()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nthumbnail\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_ManyThumbnail_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.thumbnail()
				.thumbnail()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nthumbnail\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_TitleAndSite_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.title()
				.site()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\ntitle\nsite\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_TitleAndSiteAndManyThumbnail_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.title()
				.thumbnail()
				.site()
				.thumbnail()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\ntitle\nthumbnail\nsite\n}");
	}

	@Test
	void MediaStreamingEpisodeBuilder_ThumbnailAndUrlAndTitle_NoException() {
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
	void MediaStreamingEpisodeBuilder_All_NoException() {
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

	@Test
	void MediaStreamingEpisodeBuilder_AllWithManyUrlAndManySite_NoException() {
		//given

		//when
		MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
				.thumbnail()
				.url()
				.site()
				.title()
				.site()
				.url()
				.buildMediaRank();

		assertEquals(episode.getMediaStreamingEpisodeString(), "streamingEpisodes {\nthumbnail\nurl\nsite\ntitle\n}");
	}
}