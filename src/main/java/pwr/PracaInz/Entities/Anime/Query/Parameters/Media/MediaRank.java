package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaRank {
    private final String mediaRankString;

    private MediaRank(String mediaRankString) {
        this.mediaRankString = mediaRankString;
    }

    public static MediaRankBuilder getMediaRankBuilder() {
        return new MediaRankBuilder();
    }

    @Override
    public String toString() {
        return mediaRankString;
    }

    public static final class MediaRankBuilder {
        private final Set<ParameterString> mediaRank= new LinkedHashSet<>();

        public MediaRankBuilder id() {
            mediaRank.add(new ParameterString("id\n"));
            return this;
        }

        public MediaRankBuilder rankNumber() {
            mediaRank.add(new ParameterString("rank\n"));
            return this;
        }

        public MediaRankBuilder type() {
            mediaRank.add(new ParameterString("type\n"));
            return this;
        }

        public MediaRankBuilder format() {
            mediaRank.add(new ParameterString("format\n"));
            return this;
        }

        public MediaRankBuilder year() {
            mediaRank.add(new ParameterString("year\n"));
            return this;
        }

        public MediaRankBuilder season() {
            mediaRank.add(new ParameterString("season\n"));
            return this;
        }

        public MediaRankBuilder allTime() {
            mediaRank.add(new ParameterString("allTime\n"));
            return this;
        }

        public MediaRankBuilder context() {
            mediaRank.add(new ParameterString("context\n"));
            return this;
        }

        public MediaRank buildMediaRank() {
            if (mediaRank.isEmpty()) { throw new IllegalStateException("Ranking should posses at least 1 parameter!"); }

            StringBuilder mediaRankBuilder = new StringBuilder("ranking {\n");
            mediaRank.forEach(mediaRankBuilder::append);
            mediaRankBuilder.append("}");

            return new MediaRank(mediaRankBuilder.toString());
        }
    }
}
