package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import lombok.Getter;

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
        private final Set<String> mediaRank= new LinkedHashSet<>();

        public MediaRankBuilder id() {
            mediaRank.add("id\n");
            return this;
        }

        public MediaRankBuilder rankNumber() {
            mediaRank.add("rank\n");
            return this;
        }

        public MediaRankBuilder type() {
            mediaRank.add("type\n");
            return this;
        }

        public MediaRankBuilder format() {
            mediaRank.add("format\n");
            return this;
        }

        public MediaRankBuilder year() {
            mediaRank.add("year\n");
            return this;
        }

        public MediaRankBuilder season() {
            mediaRank.add("season\n");
            return this;
        }

        public MediaRankBuilder allTime() {
            mediaRank.add("allTime\n");
            return this;
        }

        public MediaRankBuilder context() {
            mediaRank.add("context\n");
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
