package pwr.pracainz.entities.anime.query.parameters.connections.reviews;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class ReviewArguments {
	private final String reviewArgumentsString;

	private ReviewArguments(String reviewArgumentsString) {
		this.reviewArgumentsString = reviewArgumentsString;
	}

	public static ReviewArgumentsBuilder getReviewArgumentsBuilder() {
		return new ReviewArgumentsBuilder();
	}

	@Override
	public String toString() {
		return reviewArgumentsString;
	}

	public static final class ReviewArgumentsBuilder {
		private final Set<ParameterString> reviewArguments = new LinkedHashSet<>();

		public ReviewArgumentsBuilder sort(ReviewSort[] sorts) {
			reviewArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
			return this;
		}

		public ReviewArgumentsBuilder limit(int limit) {
			reviewArguments.add(new ParameterString("limit: " + limit + ", "));
			return this;
		}

		public ReviewArgumentsBuilder page(int page) {
			reviewArguments.add(new ParameterString("page: " + page + ", "));
			return this;
		}

		public ReviewArgumentsBuilder perPage(int perPage) {
			reviewArguments.add(new ParameterString("perPage: " + perPage + ", "));
			return this;
		}

		public ReviewArguments buildCharacterMediaArguments() {
			if (reviewArguments.isEmpty()) {
				throw new IllegalStateException("Review Arguments should posses at least 1 parameter!");
			}

			StringBuilder reviewArgumentsBuilder = new StringBuilder("(");

			this.reviewArguments.forEach(reviewArgumentsBuilder::append);

			reviewArgumentsBuilder.delete(reviewArgumentsBuilder.length() - 2, reviewArgumentsBuilder.length()).append(")");

			return new ReviewArguments(reviewArgumentsBuilder.toString());
		}
	}
}
