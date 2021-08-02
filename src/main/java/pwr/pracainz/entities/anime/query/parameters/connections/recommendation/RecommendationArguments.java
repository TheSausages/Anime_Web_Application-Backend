package pwr.pracainz.entities.anime.query.parameters.connections.recommendation;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class RecommendationArguments {
    private final String recommendationArgumentsString;

    private RecommendationArguments(String recommendationArgumentsString) {
        this.recommendationArgumentsString = recommendationArgumentsString;
    }

    public static RecommendationArgumentsBuilder getRecommendationArgumentBuilder() {
        return new RecommendationArgumentsBuilder();
    }

    @Override
    public String toString() {
        return recommendationArgumentsString;
    }

    public static final class RecommendationArgumentsBuilder {
        private final Set<ParameterString> recommendationArgumentsArguments = new LinkedHashSet<>();

        public RecommendationArgumentsBuilder sort(RecommendationSort[] sorts) {
            recommendationArgumentsArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
            return this;
        }

        public RecommendationArgumentsBuilder page(int page) {
            recommendationArgumentsArguments.add(new ParameterString("page: " + page + ", "));
            return this;
        }

        public RecommendationArgumentsBuilder perPage(int perPage) {
            recommendationArgumentsArguments.add(new ParameterString("perPage: " + perPage + ", "));
            return this;
        }

        public RecommendationArguments buildCharacterMediaArguments() {
            if (recommendationArgumentsArguments.isEmpty()) { throw new IllegalStateException("Recommendation Arguments should posses at least 1 parameter!"); }

            StringBuilder airingScheduleArguments = new StringBuilder("(");

            this.recommendationArgumentsArguments.forEach(airingScheduleArguments::append);

            airingScheduleArguments.delete(airingScheduleArguments.length() - 2, airingScheduleArguments.length()).append(")");

            return new RecommendationArguments(airingScheduleArguments.toString());
        }
    }
}
