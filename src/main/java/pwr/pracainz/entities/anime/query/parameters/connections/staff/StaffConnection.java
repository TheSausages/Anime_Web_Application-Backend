package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class StaffConnection {
    private final String staffConnectionString;

    private StaffConnection(String staffConnectionString) {
        this.staffConnectionString = staffConnectionString;
    }

    public String getStaffConnectionWithoutFieldName() {
        return this.staffConnectionString.substring(16);
    }

    public static StaffConnectionBuilder getMediaConnectionBuilder() {
        return new StaffConnectionBuilder();
    }

    public static final class StaffConnectionBuilder {
        private final Set<ParameterString> staffConnection = new LinkedHashSet<>();

        public StaffConnectionBuilder edges(StaffEdge edge) {
            staffConnection.add(new ParameterString("edges " + edge.getStaffEdgeWithoutFieldName() + "\n"));
            return this;
        }

        public StaffConnectionBuilder nodes(Staff staff) {
            staffConnection.add(new ParameterString("nodes " + staff.getStaffWithoutFieldName() + "\n"));
            return this;
        }

        public StaffConnectionBuilder pageInfo(PageInfo pageInfo) {
            staffConnection.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
            return this;
        }

        public StaffConnection buildStaffConnection() {
            if (staffConnection.isEmpty()) { throw new IllegalStateException("Staff Connection should posses at least 1 parameter!"); }

            StringBuilder characterConnectionBuilder = new StringBuilder("staffConnection {\n");

            staffConnection.forEach(characterConnectionBuilder::append);

            characterConnectionBuilder.append("}");

            return new StaffConnection(characterConnectionBuilder.toString());
        }
    }
}
