package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Recommendation;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.Parameters.User;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class Recommendation {
    private final String recommendationString;

    private Recommendation(String recommendationString) {
        this.recommendationString = recommendationString;
    }

    public String getRecommendationStringWithoutFieldName() {
        return this.recommendationString.substring(15);
    }

    public static RecommendationBuilder getRecommendationBuilder() {
        return new RecommendationBuilder();
    }

    public static final class RecommendationBuilder {
        private final Set<ParameterString> recommendation = new LinkedHashSet<>();

        public RecommendationBuilder id() {
            recommendation.add(new ParameterString("id\n"));
            return this;
        }

        public RecommendationBuilder rating() {
            recommendation.add(new ParameterString("rating\n"));
            return this;
        }

        public RecommendationBuilder media(Media media) {
            recommendation.add(new ParameterString("media " + media.getFieldOfMedia() + "\n"));
            return this;
        }

        public RecommendationBuilder mediaRecommendation(Media media) {
            recommendation.add(new ParameterString("mediaRecommendation " + media.getFieldOfMedia() + "\n"));
            return this;
        }

        public RecommendationBuilder user(User user) {
            recommendation.add(new ParameterString("user " + user.getUserWithoutFieldName() + "\n"));
            return this;
        }

        public Recommendation buildRecommendation() {
            if (recommendation.isEmpty()) { throw new IllegalStateException("Recommendation should posses at least 1 parameter!"); }

            StringBuilder recommendationBuilder = new StringBuilder("recommendation {\n");

            recommendation.forEach(recommendationBuilder::append);

            recommendationBuilder.append("}");

            return new Recommendation(recommendationBuilder.toString());
        }
    }
}
