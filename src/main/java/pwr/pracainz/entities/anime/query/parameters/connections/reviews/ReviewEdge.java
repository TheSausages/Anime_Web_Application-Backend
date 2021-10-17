package pwr.pracainz.entities.anime.query.parameters.connections.reviews;

import lombok.Getter;

@Getter
public class ReviewEdge {
	private final String reviewEdgeString;

	public ReviewEdge(Review review) {
		this.reviewEdgeString = "reviewEdge {\nnode " + review.getReviewWithoutFieldName() + "\n}";
	}

	public String getReviewEdgeWithoutFieldName() {
		return this.reviewEdgeString.substring(11);
	}
}
