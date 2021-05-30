package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class AiringScheduleEdge {
    private final String airingScheduleEdgeString;

    public AiringScheduleEdge() {
        this.airingScheduleEdgeString = "airingScheduleEdge {\nid\n}";
    }

    public AiringScheduleEdge(AiringSchedule airingSchedule) {
        this.airingScheduleEdgeString = "airingScheduleEdge {\nid\nnode " + airingSchedule.getAiringScheduleStringStringWithoutFieldName() + "\n}";
    }

    public String getAiringScheduleEdgeWithoutFieldName() {
        return this.airingScheduleEdgeString.substring(19);
    }
}
