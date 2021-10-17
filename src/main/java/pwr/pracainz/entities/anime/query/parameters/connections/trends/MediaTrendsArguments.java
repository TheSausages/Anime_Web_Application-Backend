package pwr.pracainz.entities.anime.query.parameters.connections.trends;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaTrendsArguments {
	private final String mediaTrendsArgumentsString;

	private MediaTrendsArguments(String mediaTrendsArgumentsString) {
		this.mediaTrendsArgumentsString = mediaTrendsArgumentsString;
	}

	public static MediaTrendsArgumentsBuilder getMediaTrendsArgumentsBuilder() {
		return new MediaTrendsArgumentsBuilder();
	}

	@Override
	public String toString() {
		return mediaTrendsArgumentsString;
	}

	public static final class MediaTrendsArgumentsBuilder {
		private final Set<ParameterString> trendsArguments = new LinkedHashSet<>();

		public MediaTrendsArgumentsBuilder sort(MediaTrendSort[] sorts) {
			trendsArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
			return this;
		}

		public MediaTrendsArgumentsBuilder releasing() {
			trendsArguments.add(new ParameterString("releasing: true, "));
			return this;
		}

		public MediaTrendsArgumentsBuilder page(int page) {
			trendsArguments.add(new ParameterString("page: " + page + ", "));
			return this;
		}

		public MediaTrendsArgumentsBuilder perPage(int perPage) {
			trendsArguments.add(new ParameterString("perPage: " + perPage + ", "));
			return this;
		}

		public MediaTrendsArguments buildTrendsArguments() {
			if (trendsArguments.isEmpty()) {
				throw new IllegalStateException("Trends Arguments should posses at least 1 parameter!");
			}

			StringBuilder trendsArgumentsBuilder = new StringBuilder("(");

			this.trendsArguments.forEach(trendsArgumentsBuilder::append);

			trendsArgumentsBuilder.delete(trendsArgumentsBuilder.length() - 2, trendsArgumentsBuilder.length()).append(")");

			return new MediaTrendsArguments(trendsArgumentsBuilder.toString());
		}
	}
}
