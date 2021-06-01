package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.AiringSchedule;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class AiringSchedule {
    private final String airingScheduleString;

    private AiringSchedule(String airingScheduleString) {
        this.airingScheduleString = airingScheduleString;
    }

    public String getAiringScheduleStringStringWithoutFieldName() {
        return this.airingScheduleString.substring(15);
    }

    public static AiringScheduleBuilder getAiringScheduleBuilder() {
        return new AiringScheduleBuilder();
    }

    public static final class AiringScheduleBuilder {
        private final Set<ParameterString> airingSchedule = new LinkedHashSet<>();

        public AiringScheduleBuilder id() {
            airingSchedule.add(new ParameterString("id\n"));
            return this;
        }

        public AiringScheduleBuilder airingAt() {
            airingSchedule.add(new ParameterString("airingAt\n"));
            return this;
        }

        public AiringScheduleBuilder timeUntilAiring() {
            airingSchedule.add(new ParameterString("timeUntilAiring\n"));
            return this;
        }

        public AiringScheduleBuilder episode() {
            airingSchedule.add(new ParameterString("episode\n"));
            return this;
        }

        public AiringScheduleBuilder mediaId() {
            airingSchedule.add(new ParameterString("mediaId\n"));
            return this;
        }

        public AiringScheduleBuilder media(Media media) {
            airingSchedule.add(new ParameterString("media " + media.getFieldOfMedia() + "\n"));
            return this;
        }

        public AiringSchedule buildAiringSchedule() {
            if (airingSchedule.isEmpty()) { throw new IllegalStateException("Airing Schedule should posses at least 1 parameter!"); }

            StringBuilder StaffBuilder = new StringBuilder("airingSchedule {\n");

            airingSchedule.forEach(StaffBuilder::append);

            StaffBuilder.append("}");

            return new AiringSchedule(StaffBuilder.toString());
        }
    }
}
