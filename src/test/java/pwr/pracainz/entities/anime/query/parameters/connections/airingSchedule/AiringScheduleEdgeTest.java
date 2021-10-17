package pwr.pracainz.entities.anime.query.parameters.connections.airingSchedule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AiringScheduleEdgeTest {

	@Test
	void AiringScheduleEdge_OnlyId_NoException() {
		//given

		//when
		AiringScheduleEdge edge = new AiringScheduleEdge();

		//then
		assertEquals(edge.getAiringScheduleEdgeString(), "airingScheduleEdge {\nid\n}");
	}

	@Test
	void AiringScheduleEdge_OnlyIdWithoutFieldName_NoException() {
		//given

		//when
		AiringScheduleEdge edge = new AiringScheduleEdge();

		//then
		assertEquals(edge.getAiringScheduleEdgeWithoutFieldName(), "{\nid\n}");
	}

	@Test
	void AiringScheduleEdge_OnlyIdWithMedia_NoException() {
		//given
		AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
				.airingAt()
				.buildAiringSchedule();

		//when
		AiringScheduleEdge edge = new AiringScheduleEdge(airingSchedule);

		//then
		assertEquals(edge.getAiringScheduleEdgeString(), "airingScheduleEdge {\nid\nnode {\nairingAt\n}\n}");
	}

	@Test
	void AiringScheduleEdge_OnlyIdWithMediaWithoutFieldName_NoException() {
		//given
		AiringSchedule airingSchedule = AiringSchedule.getAiringScheduleBuilder()
				.airingAt()
				.buildAiringSchedule();

		//when
		AiringScheduleEdge edge = new AiringScheduleEdge(airingSchedule);

		//then
		assertEquals(edge.getAiringScheduleEdgeWithoutFieldName(), "{\nid\nnode {\nairingAt\n}\n}");
	}
}