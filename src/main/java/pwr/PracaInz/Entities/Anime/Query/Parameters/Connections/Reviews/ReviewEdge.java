package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Reviews;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule.AiringSchedule;

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
