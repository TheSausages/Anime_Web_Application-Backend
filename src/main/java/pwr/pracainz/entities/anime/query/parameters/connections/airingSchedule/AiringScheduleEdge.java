package pwr.pracainz.entities.anime.query.parameters.connections.airingSchedule;

import lombok.Getter;

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
