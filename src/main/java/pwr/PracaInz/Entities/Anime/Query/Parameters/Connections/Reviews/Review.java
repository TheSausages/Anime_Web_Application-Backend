package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Reviews;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.Staff;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.Parameters.User;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class Review {
    private final String reviewString;

    private Review(String reviewString) {
        this.reviewString = reviewString;
    }

    public String getReviewWithoutFieldName() {
        return this.reviewString.substring(7);
    }

    public static ReviewBuilder getReviewBuilder() {
        return new ReviewBuilder();
    }

    public static final class ReviewBuilder {
        private final Set<ParameterString> review = new LinkedHashSet<>();

        public ReviewBuilder id() {
            review.add(new ParameterString("id\n"));
            return this;
        }

        public ReviewBuilder userId() {
            review.add(new ParameterString("userId\n"));
            return this;
        }

        public ReviewBuilder mediaId() {
            review.add(new ParameterString("mediaId\n"));
            return this;
        }

        public ReviewBuilder mediaType() {
            review.add(new ParameterString("mediaType\n"));
            return this;
        }

        public ReviewBuilder summary() {
            review.add(new ParameterString("summary\n"));
            return this;
        }

        public ReviewBuilder body() {
            review.add(new ParameterString("body\n"));
            return this;
        }

        public ReviewBuilder bodyAsHtml() {
            review.add(new ParameterString("body(asHtml: true)\n"));
            return this;
        }

        public ReviewBuilder rating() {
            review.add(new ParameterString("rating\n"));
            return this;
        }

        public ReviewBuilder ratingAmount() {
            review.add(new ParameterString("ratingAmount\n"));
            return this;
        }

        public ReviewBuilder score() {
            review.add(new ParameterString("score\n"));
            return this;
        }

        public ReviewBuilder isPrivate() {
            review.add(new ParameterString("private\n"));
            return this;
        }

        public ReviewBuilder siteUrl() {
            review.add(new ParameterString("siteUrl\n"));
            return this;
        }

        public ReviewBuilder createdAt() {
            review.add(new ParameterString("createdAt\n"));
            return this;
        }

        public ReviewBuilder updatedAt() {
            review.add(new ParameterString("updatedAt\n"));
            return this;
        }

        public ReviewBuilder user(User user) {
            review.add(new ParameterString("user " + user.getUserWithoutFieldName() + "\n"));
            return this;
        }

        public ReviewBuilder media(Media media) {
            review.add(new ParameterString("media " + media.getMediaStringWithoutFieldName() + "\n"));
            return this;
        }

        public Review buildReview() {
            if (review.isEmpty()) { throw new IllegalStateException("Review should posses at least 1 parameter!"); }

            StringBuilder reviewBuilder = new StringBuilder("review {\n");

            review.forEach(reviewBuilder::append);

            reviewBuilder.append("}");

            return new Review(reviewBuilder.toString());
        }
    }
}
