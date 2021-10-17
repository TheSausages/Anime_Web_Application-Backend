package pwr.pracainz.entities.anime.query.parameters.connections.recommendation;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecommendationTest {

	@Test
	void RecommendationBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> Recommendation.getRecommendationBuilder().buildRecommendation());

		//then
		assertEquals(exception.getMessage(), "Recommendation should posses at least 1 parameter!");
	}

	@Test
	void RecommendationBuilder_Id_NoException() {
		//given

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.id()
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationString(), "recommendation {\nid\n}");
	}

	@Test
	void RecommendationBuilder_ManyId_NoException() {
		//given

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.id()
				.id()
				.id()
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationString(), "recommendation {\nid\n}");
	}

	@Test
	void RecommendationBuilder_IdWithoutFieldName_NoException() {
		//given

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.id()
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationStringWithoutFieldName(), "{\nid\n}");
	}

	@Test
	void RecommendationBuilder_ManyIdWithoutFieldName_NoException() {
		//given

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.id()
				.id()
				.id()
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationStringWithoutFieldName(), "{\nid\n}");
	}

	@Test
	void RecommendationBuilder_Rating_NoException() {
		//given

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.rating()
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationString(), "recommendation {\nrating\n}");
	}

	@Test
	void RecommendationBuilder_ManyRating_NoException() {
		//given

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.rating()
				.rating()
				.rating()
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationString(), "recommendation {\nrating\n}");
	}

	@Test
	void RecommendationBuilder_Media_NoException() {
		//given

		//when

		//then
	}

	@Test
	void RecommendationBuilder_MediaRecommendation_NoException() {
		//given

		//when

		//then
	}

	@Test
	void RecommendationBuilder_User_NoException() {
		//given
		User user = User.getUserBuilder()
				.id()
				.buildUser();

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.user(user)
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationString(), "recommendation {\nuser {\nid\n}\n}");
	}

	@Test
	void RecommendationBuilder_ManyUser_NoException() {
		//given
		User user = User.getUserBuilder()
				.avatar()
				.buildUser();
		User user1 = User.getUserBuilder()
				.id()
				.buildUser();

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.user(user)
				.user(user1)
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationString(), "recommendation {\nuser {\navatar {\nlarge\nmedium\n}\n}\n}");
	}

	@Test
	void RecommendationBuilder_IdAndUser_NoException() {
		//given
		User user = User.getUserBuilder()
				.avatar()
				.buildUser();

		//when
		Recommendation recommendation = Recommendation.getRecommendationBuilder()
				.id()
				.user(user)
				.buildRecommendation();

		//then
		assertEquals(recommendation.getRecommendationString(), "recommendation {\nid\nuser {\navatar {\nlarge\nmedium\n}\n}\n}");
	}
}