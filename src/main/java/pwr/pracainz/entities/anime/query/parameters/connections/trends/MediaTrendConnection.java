package pwr.pracainz.entities.anime.query.parameters.connections.trends;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaTrendConnection {
	private final String mediaConnectionString;

	private MediaTrendConnection(String mediaConnectionString) {
		this.mediaConnectionString = mediaConnectionString;
	}

	public String getMediaConnectionWithoutFieldName() {
		return this.mediaConnectionString.substring(22);
	}

	public static MediaConnectionBuilder getMediaConnectionBuilder() {
		return new MediaConnectionBuilder();
	}

	public static final class MediaConnectionBuilder {
		private final Set<ParameterString> mediaTrendsConnection = new LinkedHashSet<>();

		public MediaConnectionBuilder edges(MediaTrendEdge mediaTrendEdge) {
			mediaTrendsConnection.add(new ParameterString("edges " + mediaTrendEdge.getStudioEdgeWithoutFieldName() + "\n"));
			return this;
		}

		public MediaConnectionBuilder nodes(MediaTrend mediaTrend) {
			mediaTrendsConnection.add(new ParameterString("nodes " + mediaTrend.getStudioEdgeWithoutFieldName() + "\n"));
			return this;
		}

		public MediaConnectionBuilder pageInfo(PageInfo pageInfo) {
			mediaTrendsConnection.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
			return this;
		}

		public MediaTrendConnection buildTrendsConnection() {
			if (mediaTrendsConnection.isEmpty()) {
				throw new IllegalStateException("Trends Connection should posses at least 1 parameter!");
			}

			StringBuilder characterConnectionBuilder = new StringBuilder("mediaTrendsConnection {\n");

			mediaTrendsConnection.forEach(characterConnectionBuilder::append);

			characterConnectionBuilder.append("}");

			return new MediaTrendConnection(characterConnectionBuilder.toString());
		}
	}
}
