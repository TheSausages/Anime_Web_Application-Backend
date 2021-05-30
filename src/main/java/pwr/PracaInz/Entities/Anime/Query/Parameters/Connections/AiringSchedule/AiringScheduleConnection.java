package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class AiringScheduleConnection {
    private final String airingScheduleConnectionString;

    private AiringScheduleConnection(String airingScheduleConnectionString) {
        this.airingScheduleConnectionString = airingScheduleConnectionString;
    }

    public String getAiringScheduleConnectionWithoutFieldName() {
        return this.airingScheduleConnectionString.substring(25);
    }

    public static AiringScheduleConnectionBuilder getAiringScheduleConnectionBuilder() {
        return new AiringScheduleConnectionBuilder();
    }

    public static final class AiringScheduleConnectionBuilder {
        private final Set<ParameterString> airingScheduleConnection = new LinkedHashSet<>();

        public AiringScheduleConnectionBuilder edges(AiringScheduleEdge edge) {
            airingScheduleConnection.add(new ParameterString("edges " + edge.getAiringScheduleEdgeWithoutFieldName() + "\n"));
            return this;
        }

        public AiringScheduleConnectionBuilder nodes(AiringSchedule schedule) {
            airingScheduleConnection.add(new ParameterString("nodes " + schedule.getAiringScheduleStringStringWithoutFieldName() + "\n"));
            return this;
        }

        public AiringScheduleConnectionBuilder pageInfo(PageInfo pageInfo) {
            airingScheduleConnection.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
            return this;
        }

        public AiringScheduleConnection buildAiringScheduleConnection() {
            if (airingScheduleConnection.isEmpty()) { throw new IllegalStateException("Airing Schedule Connection should posses at least 1 parameter!"); }

            StringBuilder characterConnectionBuilder = new StringBuilder("airingScheduleConnection {\n");

            airingScheduleConnection.forEach(characterConnectionBuilder::append);

            characterConnectionBuilder.append("}");

            return new AiringScheduleConnection(characterConnectionBuilder.toString());
        }
    }
}
