package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class StaffRoleType {
    private final String staffRoleTypeString;

    private StaffRoleType(String staffRoleTypeString) {
        this.staffRoleTypeString = staffRoleTypeString;
    }

    public String getStaffRoleTypeStringWithoutFieldName() {
        return this.staffRoleTypeString.substring(14);
    }

    public static StaffRoleTypeBuilder getStaffRoleTypeBuilder() {
        return new StaffRoleTypeBuilder();
    }

    public static final class StaffRoleTypeBuilder {
        private final Set<ParameterString> staffRoleType = new LinkedHashSet<>();

        public StaffRoleTypeBuilder voiceActor(Staff staff) {
            staffRoleType.add(new ParameterString("voiceActor " + staff.getStaffWithoutFieldName() + "\n"));
            return this;
        }

        public StaffRoleTypeBuilder roleNotes() {
            staffRoleType.add(new ParameterString("roleNotes\n"));
            return this;
        }

        public StaffRoleTypeBuilder dubGroup() {
            staffRoleType.add(new ParameterString("dubGroup\n"));
            return this;
        }

        public StaffRoleType buildStaffRoleType() {
            if (staffRoleType.isEmpty()) { throw new IllegalStateException("Staff Role Type should posses at least 1 parameter!"); }

            StringBuilder StaffBuilder = new StringBuilder("staffRoleType {\n");

            staffRoleType.forEach(StaffBuilder::append);

            StaffBuilder.append("}");

            return new StaffRoleType(StaffBuilder.toString());
        }
    }
}
