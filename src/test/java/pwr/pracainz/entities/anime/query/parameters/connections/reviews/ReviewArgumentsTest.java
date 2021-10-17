package pwr.pracainz.entities.anime.query.parameters.connections.reviews;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewArgumentsTest {

	@Test
	void ReviewArgumentsBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> ReviewArguments.getReviewArgumentsBuilder().buildCharacterMediaArguments());

		//then
		assertEquals(exception.getMessage(), "Review Arguments should posses at least 1 parameter!");
	}

	@Test
	void ReviewArgumentsBuilder_Sort1Element_NoException() {
		//given
		ReviewSort[] sorts = new ReviewSort[]{ReviewSort.SCORE};

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.sort(sorts)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(sort: [SCORE])");
	}

	@Test
	void ReviewArgumentsBuilder_ManySort1Element_NoException() {
		//given
		ReviewSort[] sorts1 = new ReviewSort[]{ReviewSort.SCORE};
		ReviewSort[] sorts = new ReviewSort[]{ReviewSort.CREATED_AT};

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(sort: [CREATED_AT])");
	}

	@Test
	void ReviewArgumentsBuilder_SortManyElement_NoException() {
		//given
		ReviewSort[] sorts = new ReviewSort[]{ReviewSort.SCORE, ReviewSort.RATING};

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.sort(sorts)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(sort: [SCORE, RATING])");
	}

	@Test
	void ReviewArgumentsBuilder_ManySortManyElement_NoException() {
		//given
		ReviewSort[] sorts1 = new ReviewSort[]{ReviewSort.SCORE, ReviewSort.RATING};
		ReviewSort[] sorts = new ReviewSort[]{ReviewSort.ID, ReviewSort.ID_DESC};

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(sort: [ID, ID_DESC])");
	}

	@Test
	void ReviewArgumentsBuilder_Limit_NoException() {
		//given
		int limit = 3;

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.limit(limit)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(limit: 3)");
	}

	@Test
	void ReviewArgumentsBuilder_ManyLimit_NoException() {
		//given
		int limit = 5;
		int limit1 = 3;

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.limit(limit)
				.limit(limit1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(limit: 5)");
	}

	@Test
	void ReviewArgumentsBuilder_Page_NoException() {
		//given
		int page = 6;

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.page(page)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(page: 6)");
	}

	@Test
	void ReviewArgumentsBuilder_ManyPage_NoException() {
		//given
		int page = 10;
		int page1 = 6;

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.page(page)
				.page(page1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(page: 10)");
	}

	@Test
	void ReviewArgumentsBuilder_PerPage_NoException() {
		//given
		int perPage = 15;

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.perPage(perPage)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(perPage: 15)");
	}

	@Test
	void ReviewArgumentsBuilder_ManyPerPage_NoException() {
		//given
		int perPage = 9;
		int perPage1 = 15;

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.perPage(perPage)
				.perPage(perPage1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(perPage: 9)");
	}

	@Test
	void ReviewArgumentsBuilder_PageAndPerPage_NoException() {
		//given
		int page = 2;
		int perPage = 15;

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.page(page)
				.perPage(perPage)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(page: 2, perPage: 15)");
	}

	@Test
	void ReviewArgumentsBuilder_AllWithManyPage_NoException() {
		//given
		ReviewSort[] sorts = new ReviewSort[]{ReviewSort.CREATED_AT_DESC, ReviewSort.RATING};
		int limit = 15;
		int page = 3;
		int page1 = 5;
		int perPage = 15;

		//when
		ReviewArguments arguments = ReviewArguments.getReviewArgumentsBuilder()
				.limit(limit)
				.sort(sorts)
				.page(page)
				.perPage(perPage)
				.page(page1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getReviewArgumentsString(), "(limit: 15, sort: [CREATED_AT_DESC, RATING], page: 3, perPage: 15)");
	}
}