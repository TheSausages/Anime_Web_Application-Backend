package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaConnection {
    private final String mediaConnectionString;

    private MediaConnection(String mediaConnectionString) {
        this.mediaConnectionString = mediaConnectionString;
    }

    public String getMediaConnectionWithoutFieldName() {
        return this.mediaConnectionString.substring(16);
    }

    public static MediaConnectionBuilder getMediaConnectionBuilder() {
        return new MediaConnectionBuilder();
    }

    public static final class MediaConnectionBuilder {
        private final Set<ParameterString> mediaConnections = new LinkedHashSet<>();

        public MediaConnectionBuilder edge(MediaEdge mediaEdge) {
            mediaConnections.add(new ParameterString("edges " + mediaEdge.getMediaEdgeWithoutFieldName() + "\n"));
            return this;
        }

        public MediaConnectionBuilder nodes(Media media) {
            mediaConnections.add(new ParameterString("nodes " + media.getFieldOfMedia() + "\n"));
            return this;
        }

        public MediaConnectionBuilder pageInfo(PageInfo pageInfo) {
            mediaConnections.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
            return this;
        }

        public MediaConnection buildMediaConnection() {
            if (mediaConnections.isEmpty()) { throw new IllegalStateException("Media Connection should posses at least 1 parameter!"); }

            StringBuilder characterConnectionBuilder = new StringBuilder("mediaConnection {\n");

            mediaConnections.forEach(characterConnectionBuilder::append);

            characterConnectionBuilder.append("}");

            return new MediaConnection(characterConnectionBuilder.toString());
        }
    }
}
