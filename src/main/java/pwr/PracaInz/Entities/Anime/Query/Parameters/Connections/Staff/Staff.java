package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff;

import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.CharacterEdge;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
//Do Dokońćzenia
public class Staff {
    private final String staffString;

    private Staff(String staffString) {
        this.staffString = staffString;
    }

    public String getStaffWithoutFieldName() {
        return this.staffString.substring(6);
    }

    public static StaffBuilder getStaffBuilder() {
        return new StaffBuilder();
    }

    public static final class StaffBuilder {
        private final Set<ParameterString> staff = new LinkedHashSet<>();

        public StaffBuilder id() {
            staff.add(new ParameterString("id\n"));
            return this;
        }

        public Staff buildStaff() {
            if (staff.isEmpty()) { throw new IllegalStateException("Staff should posses at least 1 parameter!"); }

            StringBuilder StaffBuilder = new StringBuilder("staff {\n");

            staff.forEach(StaffBuilder::append);

            StaffBuilder.append("}");

            return new Staff(StaffBuilder.toString());
        }
    }
}
