package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Reviews;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewEdgeTest {

    @Test
    void ReviewEdge_Review_NoException() {
        //given
        Review review = Review.getReviewBuilder()
                .updatedAt()
                .buildReview();

        //when
        ReviewEdge edge = new ReviewEdge(review);

        //then
        assertEquals(edge.getReviewEdgeString(), "reviewEdge {\nnode {\nupdatedAt\n}\n}");
    }

    @Test
    void ReviewEdge_ReviewWithoutFieldName_NoException() {
        //given
        Review review = Review.getReviewBuilder()
                .body()
                .buildReview();

        //when
        ReviewEdge edge = new ReviewEdge(review);

        //then
        assertEquals(edge.getReviewEdgeString(), "reviewEdge {\nnode {\nbody\n}\n}");
    }
}