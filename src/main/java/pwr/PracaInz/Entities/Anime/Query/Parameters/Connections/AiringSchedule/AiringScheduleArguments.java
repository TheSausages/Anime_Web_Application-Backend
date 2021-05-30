package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class AiringScheduleArguments {
    private final String airingScheduleArgumentsString;

    private AiringScheduleArguments(String airingScheduleArgumentsString) {
        this.airingScheduleArgumentsString = airingScheduleArgumentsString;
    }

    public static AiringScheduleArgumentsBuilder getAiringScheduleArgumentsBuilder() {
        return new AiringScheduleArgumentsBuilder();
    }

    @Override
    public String toString() {
        return airingScheduleArgumentsString;
    }

    public static final class AiringScheduleArgumentsBuilder {
        private final Set<ParameterString> AiringScheduleArguments = new LinkedHashSet<>();

        public AiringScheduleArgumentsBuilder notYetAired() {
            AiringScheduleArguments.add(new ParameterString("notYetAired: true, "));
            return this;
        }

        public AiringScheduleArgumentsBuilder page(int page) {
            AiringScheduleArguments.add(new ParameterString("page: " + page + ", "));
            return this;
        }

        public AiringScheduleArgumentsBuilder perPage(int perPage) {
            AiringScheduleArguments.add(new ParameterString("perPage: " + perPage + ", "));
            return this;
        }

        public AiringScheduleArguments buildCharacterMediaArguments() {
            if (AiringScheduleArguments.isEmpty()) { throw new IllegalStateException("Airing Schedule Arguments should posses at least 1 parameter!"); }

            StringBuilder airingScheduleArguments = new StringBuilder("(");

            this.AiringScheduleArguments.forEach(airingScheduleArguments::append);

            airingScheduleArguments.delete(airingScheduleArguments.length() - 2, airingScheduleArguments.length()).append(")");

            return new AiringScheduleArguments(airingScheduleArguments.toString());
        }
    }
}