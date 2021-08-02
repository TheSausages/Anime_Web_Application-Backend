package pwr.pracainz.entities.anime.query.parameters.connections.trends;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.queryElements.Media.Media;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaTrend {
    private final String mediaTrendString;

    private MediaTrend(String mediaTrendString) {
        this.mediaTrendString = mediaTrendString;
    }

    public String getStudioEdgeWithoutFieldName() {
        return this.mediaTrendString.substring(11);
    }

    public static MediaTrendBuilder getMediaTrendBuilder() {
        return new MediaTrendBuilder();
    }

    @Override
    public String toString() {
        return mediaTrendString;
    }

    public static final class MediaTrendBuilder {
        private final Set<ParameterString> mediaTrend = new LinkedHashSet<>();

        public MediaTrendBuilder mediaId() {
            mediaTrend.add(new ParameterString("mediaId\n"));
            return this;
        }

        public MediaTrendBuilder date() {
            mediaTrend.add(new ParameterString("date\n"));
            return this;
        }

        public MediaTrendBuilder trending() {
            mediaTrend.add(new ParameterString("trending\n"));
            return this;
        }

        public MediaTrendBuilder averageScore() {
            mediaTrend.add(new ParameterString("averageScore\n"));
            return this;
        }

        public MediaTrendBuilder popularity() {
            mediaTrend.add(new ParameterString("popularity\n"));
            return this;
        }

        public MediaTrendBuilder inProgress() {
            mediaTrend.add(new ParameterString("inProgress\n"));
            return this;
        }

        public MediaTrendBuilder releasing() {
            mediaTrend.add(new ParameterString("releasing\n"));
            return this;
        }

        public MediaTrendBuilder episode() {
            mediaTrend.add(new ParameterString("episode\n"));
            return this;
        }

        public MediaTrendBuilder media(Media media) {
            mediaTrend.add(new ParameterString("media " + media.getFieldOfMedia() + "\n"));
            return this;
        }

        public MediaTrend buildMediaTrendEdge() {
            if (mediaTrend.isEmpty()) { throw new IllegalStateException("Media Trend should posses at least 1 parameter!"); }

            StringBuilder MediaTrendBuilder = new StringBuilder("mediaTrend {\n");

            mediaTrend.forEach(MediaTrendBuilder::append);

            MediaTrendBuilder.append("}");

            return new MediaTrend(MediaTrendBuilder.toString());
        }
    }
}
