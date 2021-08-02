package pwr.pracainz.entities.anime.query.parameters.connections.trends;

import lombok.Getter;

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
