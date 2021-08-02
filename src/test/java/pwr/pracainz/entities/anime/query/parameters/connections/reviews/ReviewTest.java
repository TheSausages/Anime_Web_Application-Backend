package pwr.pracainz.entities.anime.query.parameters.connections.reviews;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewTest {

    @Test
    void ReviewBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> Review.getReviewBuilder().buildReview());

        //then
        assertEquals(exception.getMessage(), "Review should posses at least 1 parameter!");
    }

    @Test
    void ReviewBuilder_Id_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .id()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nid\n}");
    }

    @Test
    void ReviewBuilder_ManyId_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .id()
                .id()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nid\n}");
    }

    @Test
    void ReviewBuilder_IdWithoutFieldName_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .id()
                .buildReview();

        //then
        assertEquals(review.getReviewWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void ReviewBuilder_ManyIdWithoutFieldName_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .id()
                .id()
                .id()
                .buildReview();

        //then
        assertEquals(review.getReviewWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void ReviewBuilder_UserId_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .userId()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nuserId\n}");
    }

    @Test
    void ReviewBuilder_ManyUserId_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .userId()
                .userId()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nuserId\n}");
    }

    @Test
    void ReviewBuilder_MediaId_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .mediaId()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nmediaId\n}");
    }

    @Test
    void ReviewBuilder_ManyMediaId_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .mediaId()
                .mediaId()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nmediaId\n}");
    }

    @Test
    void ReviewBuilder_MediaType_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .mediaType()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nmediaType\n}");
    }

    @Test
    void ReviewBuilder_ManyMediaType_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .mediaType()
                .mediaType()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nmediaType\n}");
    }

    @Test
    void ReviewBuilder_Summary_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .summary()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nsummary\n}");
    }

    @Test
    void ReviewBuilder_ManySummary_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .summary()
                .summary()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nsummary\n}");
    }

    @Test
    void ReviewBuilder_Body_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .body()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nbody\n}");
    }

    @Test
    void ReviewBuilder_ManyBody_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .body()
                .body()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nbody\n}");
    }

    @Test
    void ReviewBuilder_BodyAsHtml_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .bodyAsHtml()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nbody(asHtml: true)\n}");
    }

    @Test
    void ReviewBuilder_ManyBodyAsHtml_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .bodyAsHtml()
                .bodyAsHtml()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nbody(asHtml: true)\n}");
    }

    @Test
    void ReviewBuilder_Rating_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .rating()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nrating\n}");
    }

    @Test
    void ReviewBuilder_ManyRating_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .rating()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nrating\n}");
    }

    @Test
    void ReviewBuilder_RatingAmount_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .ratingAmount()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nratingAmount\n}");
    }

    @Test
    void ReviewBuilder_ManyRatingAmount_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .ratingAmount()
                .ratingAmount()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nratingAmount\n}");
    }

    @Test
    void ReviewBuilder_Score_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .score()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nscore\n}");
    }

    @Test
    void ReviewBuilder_ManyScore_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .score()
                .score()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nscore\n}");
    }

    @Test
    void ReviewBuilder_IsPrivate_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .isPrivate()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nprivate\n}");
    }

    @Test
    void ReviewBuilder_ManyIsPrivate_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .isPrivate()
                .isPrivate()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nprivate\n}");
    }

    @Test
    void ReviewBuilder_CreatedAt_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .createdAt()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\ncreatedAt\n}");
    }

    @Test
    void ReviewBuilder_ManyCreatedAt_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .createdAt()
                .createdAt()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\ncreatedAt\n}");
    }

    @Test
    void ReviewBuilder_UpdatedAt_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .updatedAt()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nupdatedAt\n}");
    }

    @Test
    void ReviewBuilder_ManyUpdatedAt_NoException() {
        //given

        //when
        Review review = Review.getReviewBuilder()
                .updatedAt()
                .updatedAt()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nupdatedAt\n}");
    }

    @Test
    void ReviewBuilder_User_NoException() {
        //given
        User user = User.getUserBuilder()
                .name()
                .buildUser();

        //when
        Review review = Review.getReviewBuilder()
                .user(user)
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nuser {\nname\n}\n}");
    }

    @Test
    void ReviewBuilder_ManyUser_NoException() {
        //given
        User user = User.getUserBuilder()
                .avatar()
                .buildUser();
        User user1 = User.getUserBuilder()
                .name()
                .buildUser();

        //when
        Review review = Review.getReviewBuilder()
                .user(user)
                .user(user1)
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nuser {\navatar {\nlarge\nmedium\n}\n}\n}");
    }

    @Test
    void ReviewBuilder_Media_NoException() {
        //given


        //when


        //then

    }

    @Test
    void ReviewBuilder_IdAndMediaIdAndMediaType_NoException() {
        //given


        //when
        Review review = Review.getReviewBuilder()
                .id()
                .mediaId()
                .mediaType()
                .buildReview();

        //then
        assertEquals(review.getReviewString(), "review {\nid\nmediaId\nmediaType\n}");
    }
}