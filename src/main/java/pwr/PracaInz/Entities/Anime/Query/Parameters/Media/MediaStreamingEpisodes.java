package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaStreamingEpisodes {
    private final String MediaStreamingEpisodeString;

    private MediaStreamingEpisodes(String MediaStreamingEpisodeString) {
        this.MediaStreamingEpisodeString = MediaStreamingEpisodeString;
    }

    @Override
    public String toString() {
        return MediaStreamingEpisodeString;
    }

    public static MediaStreamingEpisodesBuilder MediaStreamingEpisodesBuilder() {
        return new MediaStreamingEpisodesBuilder();
    }

    public final static class MediaStreamingEpisodesBuilder {
        private final Set<ParameterString> mediaStreamingEpisode = new LinkedHashSet<>();

        public MediaStreamingEpisodesBuilder title() {
            mediaStreamingEpisode.add(new ParameterString("title\n"));
            return this;
        }

        public MediaStreamingEpisodesBuilder url() {
            mediaStreamingEpisode.add(new ParameterString("url\n"));
            return this;
        }

        public MediaStreamingEpisodesBuilder site() {
            mediaStreamingEpisode.add(new ParameterString("site\n"));
            return this;
        }

        public MediaStreamingEpisodesBuilder thumbnail() {
            mediaStreamingEpisode.add(new ParameterString("thumbnail\n"));
            return this;
        }

        public MediaStreamingEpisodes buildMediaRank() {
            if (mediaStreamingEpisode.isEmpty()) { throw new IllegalStateException("Streaming Episodes should posses at least 1 parameter!"); }

            StringBuilder mediaStreamingEpisodeBuilder = new StringBuilder("streamingEpisodes {\n");;
            mediaStreamingEpisode.forEach(mediaStreamingEpisodeBuilder::append);
            mediaStreamingEpisodeBuilder.append("}");

            return new MediaStreamingEpisodes(mediaStreamingEpisodeBuilder.toString());
        }

    }
}
