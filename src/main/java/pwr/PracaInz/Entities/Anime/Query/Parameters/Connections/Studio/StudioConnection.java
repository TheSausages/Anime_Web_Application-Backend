package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Studio;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.Staff;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffEdge;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class StudioConnection {
    private final String studioConnectionString;

    private StudioConnection(String studioConnectionString) {
        this.studioConnectionString = studioConnectionString;
    }

    public String getStudioConnectionWithoutFieldName() {
        return this.studioConnectionString.substring(17);
    }

    public static StudioConnectionBuilder getStudioConnectionBuilder() {
        return new StudioConnectionBuilder();
    }

    public static final class StudioConnectionBuilder {
        private final Set<ParameterString> studioConnection = new LinkedHashSet<>();

        public StudioConnectionBuilder edges(StudioEdge studioEdge) {
            studioConnection.add(new ParameterString("edges " + studioEdge.getStudioEdgeWithoutFieldName() + "\n"));
            return this;
        }

        public StudioConnectionBuilder nodes(Studio studio) {
            studioConnection.add(new ParameterString("nodes " + studio.getStudioWithoutFieldName() + "\n"));
            return this;
        }

        public StudioConnectionBuilder pageInfo(PageInfo pageInfo) {
            studioConnection.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
            return this;
        }

        public StudioConnection buildStudioConnection() {
            if (studioConnection.isEmpty()) { throw new IllegalStateException("Studio Connection should posses at least 1 parameter!"); }

            StringBuilder characterConnectionBuilder = new StringBuilder("studioConnection {\n");

            studioConnection.forEach(characterConnectionBuilder::append);

            characterConnectionBuilder.append("}");

            return new StudioConnection(characterConnectionBuilder.toString());
        }
    }
}
