package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Trends;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Studio.Studio;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Studio.StudioEdge;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaTrendEdge {
    private final String studioEdgeString;

    public MediaTrendEdge(MediaTrend mediaTrend) {
        this.studioEdgeString = "mediaTrendEdge " + mediaTrend.getStudioEdgeWithoutFieldName();
    }

    public String getStudioEdgeWithoutFieldName() {
        return this.studioEdgeString.substring(15);
    }
}
