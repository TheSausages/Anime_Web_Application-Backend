package pwr.pracainz.entities.anime.query.parameters.connections.reviews;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewConnectionTest {

	@Test
	void ReviewConnectionBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> ReviewConnection.getReviewConnectionBuilder().buildReviewConnection());

		//then
		assertEquals(exception.getMessage(), "Review Connection should posses at least 1 parameter!");
	}

	@Test
	void ReviewConnectionBuilder_Edge_NoException() {
		//given
		Review review = Review.getReviewBuilder()
				.isPrivate()
				.buildReview();
		ReviewEdge edge = new ReviewEdge(review);

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.edge(edge)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionString(), "reviewConnection {\nedges {\nnode {\nprivate\n}\n}\n}");
	}

	@Test
	void ReviewConnectionBuilder_ManyEdge_NoException() {
		//given
		Review review = Review.getReviewBuilder()
				.id()
				.buildReview();
		ReviewEdge edge = new ReviewEdge(review);
		Review review1 = Review.getReviewBuilder()
				.isPrivate()
				.buildReview();
		ReviewEdge edge1 = new ReviewEdge(review1);

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.edge(edge)
				.edge(edge1)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionString(), "reviewConnection {\nedges {\nnode {\nid\n}\n}\n}");
	}

	@Test
	void ReviewConnectionBuilder_EdgeWithoutFieldName_NoException() {
		//given
		Review review = Review.getReviewBuilder()
				.isPrivate()
				.buildReview();
		ReviewEdge edge = new ReviewEdge(review);

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.edge(edge)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionWithoutFieldName(), "{\nedges {\nnode {\nprivate\n}\n}\n}");
	}

	@Test
	void ReviewConnectionBuilder_ManyEdgeWithoutFieldName_NoException() {
		//given
		Review review = Review.getReviewBuilder()
				.id()
				.buildReview();
		ReviewEdge edge = new ReviewEdge(review);
		Review review1 = Review.getReviewBuilder()
				.isPrivate()
				.buildReview();
		ReviewEdge edge1 = new ReviewEdge(review1);

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.edge(edge)
				.edge(edge1)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionWithoutFieldName(), "{\nedges {\nnode {\nid\n}\n}\n}");
	}

	@Test
	void ReviewConnectionBuilder_Nodes_NoException() {
		//given
		Review review = Review.getReviewBuilder()
				.bodyAsHtml()
				.buildReview();

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.nodes(review)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionString(), "reviewConnection {\nnodes {\nbody(asHtml: true)\n}\n}");
	}

	@Test
	void ReviewConnectionBuilder_ManyNodes_NoException() {
		//given
		Review review = Review.getReviewBuilder()
				.updatedAt()
				.buildReview();
		Review review1 = Review.getReviewBuilder()
				.bodyAsHtml()
				.buildReview();

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.nodes(review)
				.nodes(review1)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionString(), "reviewConnection {\nnodes {\nupdatedAt\n}\n}");
	}

	@Test
	void ReviewConnectionBuilder_PageInfo_NoException() {
		//given
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.currentPage()
				.buildPageInfo();

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.pageInfo(pageInfo)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionString(), "reviewConnection {\npageInfo {\ncurrentPage\n}\n}");
	}

	@Test
	void ReviewConnectionBuilder_ManyPageInfo_NoException() {
		//given
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.lastPage()
				.buildPageInfo();
		PageInfo pageInfo1 = PageInfo.getPageInfoBuilder()
				.currentPage()
				.buildPageInfo();

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.pageInfo(pageInfo)
				.pageInfo(pageInfo1)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionString(), "reviewConnection {\npageInfo {\nlastPage\n}\n}");
	}

	@Test
	void ReviewConnectionBuilder_All3WithManyPageInfo_NoException() {
		//given
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.lastPage()
				.buildPageInfo();
		PageInfo pageInfo1 = PageInfo.getPageInfoBuilder()
				.currentPage()
				.buildPageInfo();
		Review review1 = Review.getReviewBuilder()
				.isPrivate()
				.buildReview();
		ReviewEdge edge = new ReviewEdge(review1);
		Review review = Review.getReviewBuilder()
				.bodyAsHtml()
				.buildReview();

		//when
		ReviewConnection reviewConnection = ReviewConnection.getReviewConnectionBuilder()
				.pageInfo(pageInfo)
				.edge(edge)
				.pageInfo(pageInfo1)
				.nodes(review)
				.buildReviewConnection();

		//then
		assertEquals(reviewConnection.getReviewConnectionString(), "reviewConnection {\npageInfo {\nlastPage\n}\nedges {\nnode {\nprivate\n}\n}\nnodes {\nbody(asHtml: true)\n}\n}");
	}
}