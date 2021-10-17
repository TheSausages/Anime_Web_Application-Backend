package pwr.pracainz.entities.anime.query.parameters.connections.trends;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MediaTrendConnectionTest {

	@Test
	void MediaTrendConnectionBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> MediaTrendConnection.getMediaConnectionBuilder().buildTrendsConnection());

		//then
		assertEquals(exception.getMessage(), "Trends Connection should posses at least 1 parameter!");
	}

	@Test
	void MediaTrendConnectionBuilder_Edges_NoException() {
		//given
		MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
				.averageScore()
				.buildMediaTrendEdge();
		MediaTrendEdge mediaTrendEdge = new MediaTrendEdge(mediaTrend);

		//when
		MediaTrendConnection connection = MediaTrendConnection.getMediaConnectionBuilder()
				.edges(mediaTrendEdge)
				.buildTrendsConnection();

		//then
		assertEquals(connection.getMediaConnectionString(), "mediaTrendsConnection {\nedges {\naverageScore\n}\n}");
	}

	@Test
	void MediaTrendConnectionBuilder_EdgesWithoutFieldName_NoException() {
		//given
		MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
				.averageScore()
				.buildMediaTrendEdge();
		MediaTrendEdge mediaTrendEdge = new MediaTrendEdge(mediaTrend);

		//when
		MediaTrendConnection connection = MediaTrendConnection.getMediaConnectionBuilder()
				.edges(mediaTrendEdge)
				.buildTrendsConnection();

		//then
		assertEquals(connection.getMediaConnectionWithoutFieldName(), "{\nedges {\naverageScore\n}\n}");
	}

	@Test
	void MediaTrendConnectionBuilder_ManyEdges_NoException() {
		//given
		MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
				.releasing()
				.buildMediaTrendEdge();
		MediaTrendEdge mediaTrendEdge = new MediaTrendEdge(mediaTrend);
		MediaTrend mediaTrend1 = MediaTrend.getMediaTrendBuilder()
				.averageScore()
				.buildMediaTrendEdge();
		MediaTrendEdge mediaTrendEdge1 = new MediaTrendEdge(mediaTrend1);

		//when
		MediaTrendConnection connection = MediaTrendConnection.getMediaConnectionBuilder()
				.edges(mediaTrendEdge)
				.edges(mediaTrendEdge1)
				.buildTrendsConnection();

		//then
		assertEquals(connection.getMediaConnectionString(), "mediaTrendsConnection {\nedges {\nreleasing\n}\n}");
	}

	@Test
	void MediaTrendConnectionBuilder_Nodes_NoException() {
		//given
		MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
				.averageScore()
				.buildMediaTrendEdge();

		//when
		MediaTrendConnection connection = MediaTrendConnection.getMediaConnectionBuilder()
				.nodes(mediaTrend)
				.buildTrendsConnection();

		//then
		assertEquals(connection.getMediaConnectionString(), "mediaTrendsConnection {\nnodes {\naverageScore\n}\n}");
	}

	@Test
	void MediaTrendConnectionBuilder_ManyNodes_NoException() {
		//given
		MediaTrend mediaTrend = MediaTrend.getMediaTrendBuilder()
				.inProgress()
				.buildMediaTrendEdge();
		MediaTrend mediaTrend1 = MediaTrend.getMediaTrendBuilder()
				.averageScore()
				.buildMediaTrendEdge();

		//when
		MediaTrendConnection connection = MediaTrendConnection.getMediaConnectionBuilder()
				.nodes(mediaTrend)
				.nodes(mediaTrend1)
				.buildTrendsConnection();

		//then
		assertEquals(connection.getMediaConnectionString(), "mediaTrendsConnection {\nnodes {\ninProgress\n}\n}");
	}

	@Test
	void MediaTrendConnectionBuilder_PageInfo_NoException() {
		//given
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.currentPage()
				.buildPageInfo();

		//when
		MediaTrendConnection connection = MediaTrendConnection.getMediaConnectionBuilder()
				.pageInfo(pageInfo)
				.buildTrendsConnection();

		//then
		assertEquals(connection.getMediaConnectionString(), "mediaTrendsConnection {\npageInfo {\ncurrentPage\n}\n}");
	}

	@Test
	void MediaTrendConnectionBuilder_ManyPageInfo_NoException() {
		//given
		PageInfo pageInfo = PageInfo.getPageInfoBuilder()
				.lastPage()
				.buildPageInfo();
		PageInfo pageInfo1 = PageInfo.getPageInfoBuilder()
				.currentPage()
				.buildPageInfo();

		//when
		MediaTrendConnection connection = MediaTrendConnection.getMediaConnectionBuilder()
				.pageInfo(pageInfo)
				.pageInfo(pageInfo1)
				.buildTrendsConnection();

		//then
		assertEquals(connection.getMediaConnectionString(), "mediaTrendsConnection {\npageInfo {\nlastPage\n}\n}");
	}
}