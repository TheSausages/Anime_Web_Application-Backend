package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Reviews;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class ReviewConnection {
    private final String reviewConnectionString;

    private ReviewConnection(String reviewConnectionString) {
        this.reviewConnectionString = reviewConnectionString;
    }

    public String getReviewConnectionWithoutFieldName() {
        return this.reviewConnectionString.substring(17);
    }

    public static ReviewConnectionBuilder getReviewConnectionBuilder() {
        return new ReviewConnectionBuilder();
    }

    public static final class ReviewConnectionBuilder {
        private final Set<ParameterString> reviewConnection = new LinkedHashSet<>();

        public ReviewConnectionBuilder edge(ReviewEdge reviewEdge) {
            reviewConnection.add(new ParameterString("edges " + reviewEdge.getReviewEdgeWithoutFieldName() + "\n"));
            return this;
        }

        public ReviewConnectionBuilder nodes(Review review) {
            reviewConnection.add(new ParameterString("nodes " + review.getReviewWithoutFieldName() + "\n"));
            return this;
        }

        public ReviewConnectionBuilder pageInfo(PageInfo pageInfo) {
            reviewConnection.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
            return this;
        }

        public ReviewConnection buildReviewConnection() {
            if (reviewConnection.isEmpty()) { throw new IllegalStateException("Review Connection should posses at least 1 parameter!"); }

            StringBuilder characterConnectionBuilder = new StringBuilder("reviewConnection {\n");

            reviewConnection.forEach(characterConnectionBuilder::append);

            characterConnectionBuilder.append("}");

            return new ReviewConnection(characterConnectionBuilder.toString());
        }
    }
}
