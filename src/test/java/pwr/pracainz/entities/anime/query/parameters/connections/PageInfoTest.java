package pwr.pracainz.entities.anime.query.parameters.connections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PageInfoTest {

	@Test
	void PageInfoBuilder_NoParam_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> PageInfo.getPageInfoBuilder().buildPageInfo());

		//then
		assertEquals(exception.getMessage(), "Page Info should posses at least 1 parameter!");
	}

	@Test
	void PageInfoBuilder_Total_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.total()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\ntotal\n}");
	}

	@Test
	void PageInfoBuilder_ManyTotal_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.total()
				.total()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\ntotal\n}");
	}

	@Test
	void PageInfoBuilder_PerPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.perPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\nperPage\n}");
	}

	@Test
	void PageInfoBuilder_ManyPerPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.perPage()
				.perPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\nperPage\n}");
	}

	@Test
	void PageInfoBuilder_currentPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.currentPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\ncurrentPage\n}");
	}

	@Test
	void PageInfoBuilder_ManyCurrentPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.currentPage()
				.currentPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\ncurrentPage\n}");
	}

	@Test
	void PageInfoBuilder_LastPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.lastPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\nlastPage\n}");
	}

	@Test
	void PageInfoBuilder_ManyLastPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.lastPage()
				.lastPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\nlastPage\n}");
	}

	@Test
	void PageInfoBuilder_HasNextPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.hasNextPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\nhasNextPage\n}");
	}

	@Test
	void PageInfoBuilder_ManyHasNextPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.hasNextPage()
				.hasNextPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\nhasNextPage\n}");
	}

	@Test
	void PageInfoBuilder_TotalAndHasNextPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.total()
				.hasNextPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\ntotal\nhasNextPage\n}");
	}

	@Test
	void PageInfoBuilder_CurrentPageAndManyLastPageAndPerPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.currentPage()
				.lastPage()
				.lastPage()
				.perPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\ncurrentPage\nlastPage\nperPage\n}");
	}

	@Test
	void PageInfoBuilder_AllAndManyTotalAndManyLastPage_NoException() {
		//given

		//when
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.total()
				.perPage()
				.total()
				.currentPage()
				.hasNextPage()
				.total()
				.lastPage()
				.total()
				.lastPage()
				.buildPageInfo();

		//then
		assertEquals(pageInfo.getPageInfoString(), "pageInfo {\ntotal\nperPage\ncurrentPage\nhasNextPage\nlastPage\n}");
	}
}