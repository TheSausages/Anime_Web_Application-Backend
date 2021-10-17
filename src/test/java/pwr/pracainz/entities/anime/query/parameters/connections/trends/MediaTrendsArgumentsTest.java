package pwr.pracainz.entities.anime.query.parameters.connections.trends;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MediaTrendsArgumentsTest {

	@Test
	void TrendsArgumentsBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> MediaTrendsArguments.getMediaTrendsArgumentsBuilder().buildTrendsArguments());

		//then
		assertEquals(exception.getMessage(), "Trends Arguments should posses at least 1 parameter!");
	}

	@Test
	void TrendsArgumentsBuilder_Sort1Element_NoException() {
		//given
		MediaTrendSort[] sorts = new MediaTrendSort[]{MediaTrendSort.SCORE};

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.sort(sorts)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(sort: [SCORE])");
	}

	@Test
	void TrendsArgumentsBuilder_ManySort1Element_NoException() {
		//given
		MediaTrendSort[] sorts = new MediaTrendSort[]{MediaTrendSort.DATE};
		MediaTrendSort[] sorts1 = new MediaTrendSort[]{MediaTrendSort.SCORE};

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(sort: [DATE])");
	}

	@Test
	void TrendsArgumentsBuilder_SortManyElement_NoException() {
		//given
		MediaTrendSort[] sorts = new MediaTrendSort[]{MediaTrendSort.SCORE, MediaTrendSort.TRENDING};

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.sort(sorts)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(sort: [SCORE, TRENDING])");
	}

	@Test
	void TrendsArgumentsBuilder_ManySortManyElement_NoException() {
		//given
		MediaTrendSort[] sorts = new MediaTrendSort[]{MediaTrendSort.DATE, MediaTrendSort.EPISODE};
		MediaTrendSort[] sorts1 = new MediaTrendSort[]{MediaTrendSort.SCORE, MediaTrendSort.MEDIA_ID};

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(sort: [DATE, EPISODE])");
	}

	@Test
	void TrendsArgumentsBuilder_Realising_NoException() {
		//given

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.releasing()
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(releasing: true)");
	}

	@Test
	void TrendsArgumentsBuilder_ManyRealising_NoException() {
		//given

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.releasing()
				.releasing()
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(releasing: true)");
	}

	@Test
	void TrendsArgumentsBuilder_Page_NoException() {
		//given
		int page = 6;

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.page(page)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(page: 6)");
	}

	@Test
	void TrendsArgumentsBuilder_ManyPage_NoException() {
		//given
		int page = 11;
		int page1 = 6;

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.page(page)
				.page(page1)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(page: 11)");
	}

	@Test
	void TrendsArgumentsBuilder_PerPage_NoException() {
		//given
		int perPage = 15;

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.perPage(perPage)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(perPage: 15)");
	}

	@Test
	void TrendsArgumentsBuilder_ManyPerPage_NoException() {
		//given
		int perPage = 2;
		int perPage1 = 15;


		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.perPage(perPage)
				.perPage(perPage1)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(perPage: 2)");
	}

	@Test
	void TrendsArgumentsBuilder_PageAndPerPage_NoException() {
		//given
		int page = 2;
		int perPage = 15;

		//when
		MediaTrendsArguments arguments = MediaTrendsArguments.getMediaTrendsArgumentsBuilder()
				.page(page)
				.perPage(perPage)
				.buildTrendsArguments();

		//then
		assertEquals(arguments.getMediaTrendsArgumentsString(), "(page: 2, perPage: 15)");
	}
}