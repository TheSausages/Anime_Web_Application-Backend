package pwr.pracainz.entities.anime.query.parameters.connections.recommendation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecommendationArgumentsTest {

	@Test
	void RecommendationArgumentBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> RecommendationArguments.getRecommendationArgumentBuilder().buildCharacterMediaArguments());

		//then
		assertEquals(exception.getMessage(), "Recommendation Arguments should posses at least 1 parameter!");
	}

	@Test
	void RecommendationArgumentBuilder_Sort1Element_NoException() {
		//given
		RecommendationSort[] sorts = new RecommendationSort[]{RecommendationSort.RATING};

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.sort(sorts)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(sort: [RATING])");
	}

	@Test
	void RecommendationArgumentBuilder_ManySort1Element_NoException() {
		//given
		RecommendationSort[] sorts = new RecommendationSort[]{RecommendationSort.ID_DESC};
		RecommendationSort[] sorts1 = new RecommendationSort[]{RecommendationSort.RATING};

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(sort: [ID_DESC])");
	}

	@Test
	void RecommendationArgumentBuilder_SortManyElement_NoException() {
		//given
		RecommendationSort[] sorts = new RecommendationSort[]{RecommendationSort.ID_DESC, RecommendationSort.RATING};

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.sort(sorts)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(sort: [ID_DESC, RATING])");
	}

	@Test
	void RecommendationArgumentBuilder_ManySortManyElement_NoException() {
		//given
		RecommendationSort[] sorts1 = new RecommendationSort[]{RecommendationSort.ID_DESC, RecommendationSort.RATING};
		RecommendationSort[] sorts = new RecommendationSort[]{RecommendationSort.RATING_DESC, RecommendationSort.ID};

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(sort: [RATING_DESC, ID])");
	}

	@Test
	void RecommendationArgumentBuilder_Page_NoException() {
		//given
		int page = 6;

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.page(page)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(page: 6)");
	}

	@Test
	void RecommendationArgumentBuilder_ManyPage_NoException() {
		//given
		int page = 11;
		int page1 = 6;

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.page(page)
				.page(page1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(page: 11)");
	}

	@Test
	void RecommendationArgumentBuilder_PerPage_NoException() {
		//given
		int perPage = 3;

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.perPage(perPage)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(perPage: 3)");
	}

	@Test
	void RecommendationArgumentBuilder_ManyPerPage_NoException() {
		//given
		int perPage = 13;
		int perPage1 = 3;

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.perPage(perPage)
				.perPage(perPage1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(perPage: 13)");
	}

	@Test
	void RecommendationArgumentBuilder_PageAndPerPage_NoException() {
		//given
		int page = 6;
		int perPage = 3;

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.page(page)
				.perPage(perPage)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(page: 6, perPage: 3)");
	}

	@Test
	void RecommendationArgumentBuilder_All3WithManyPerPage_NoException() {
		//given
		int page = 6;
		int perPage = 9;
		int perPage1 = 13;
		RecommendationSort[] sorts = new RecommendationSort[]{RecommendationSort.ID, RecommendationSort.ID_DESC};

		//when
		RecommendationArguments arguments = RecommendationArguments.getRecommendationArgumentBuilder()
				.perPage(perPage)
				.page(page)
				.sort(sorts)
				.perPage(perPage1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getRecommendationArgumentsString(), "(perPage: 9, page: 6, sort: [ID, ID_DESC])");
	}
}