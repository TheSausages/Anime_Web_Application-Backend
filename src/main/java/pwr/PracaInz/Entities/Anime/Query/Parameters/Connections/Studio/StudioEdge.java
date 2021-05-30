package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Studio;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaEdge;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Field;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class StudioEdge {
    private final String studioEdgeString;

    private StudioEdge(String studioEdgeString) {
        this.studioEdgeString = studioEdgeString;
    }

    public String getStudioEdgeWithoutFieldName() {
        return this.studioEdgeString.substring(11);
    }

    public static StudioEdgeBuilder getStudioEdgedBuilder() {
        return new StudioEdgeBuilder();
    }

    @Override
    public String toString() {
        return studioEdgeString;
    }

    public static final class StudioEdgeBuilder {
        private final Set<ParameterString> studioEdge = new LinkedHashSet<>();

        public StudioEdgeBuilder node(Studio studio) {
            studioEdge.add(new ParameterString("node " + studio.getStudioWithoutFieldName() + "\n"));
            return this;
        }

        public StudioEdgeBuilder id() {
            studioEdge.add(new ParameterString("id\n"));
            return this;
        }

        public StudioEdgeBuilder isMain() {
            studioEdge.add(new ParameterString("isMain\n"));
            return this;
        }

        public StudioEdgeBuilder favouriteOrder() {
            studioEdge.add(new ParameterString("favouriteOrder\n"));
            return this;
        }

        public StudioEdge buildStudioEdge() {
            if (studioEdge.isEmpty()) { throw new IllegalStateException("Studio Edge should posses at least 1 parameter!"); }

            StringBuilder studioEdgeBuilder = new StringBuilder("studioEdge {\n");

            studioEdge.forEach(studioEdgeBuilder::append);

            studioEdgeBuilder.append("}");

            return new StudioEdge(studioEdgeBuilder.toString());
        }
    }
}
