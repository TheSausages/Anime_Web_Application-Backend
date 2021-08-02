package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class StaffEdge {
    private final String staffEdgeString;

    private StaffEdge(String staffEdgeString) {
        this.staffEdgeString = staffEdgeString;
    }

    public static StaffEdgeBuilder getStaffEdgeBuilder() {
        return new StaffEdgeBuilder();
    }

    public String getStaffEdgeWithoutFieldName() {
        return this.staffEdgeString.substring(10);
    }

    @Override
    public String toString() {
        return staffEdgeString;
    }

    public static final class StaffEdgeBuilder {
        private final Set<ParameterString> staffEdge = new LinkedHashSet<>();

        public StaffEdgeBuilder node(Staff staff) {
            staffEdge.add(new ParameterString("node " + staff.getStaffWithoutFieldName() + "\n"));
            return this;
        }

        public StaffEdgeBuilder id() {
            staffEdge.add(new ParameterString("id\n"));
            return this;
        }

        public StaffEdgeBuilder role() {
            staffEdge.add(new ParameterString("role\n"));
            return this;
        }

        public StaffEdgeBuilder favouriteOrder() {
            staffEdge.add(new ParameterString("favouriteOrder\n"));
            return this;
        }

        public StaffEdge buildStaffEdge() {
            if (staffEdge.isEmpty()) { throw new IllegalStateException("Staff Edge should posses at least 1 parameter!"); }

            StringBuilder staffEdgeBuilder = new StringBuilder("staffEdge {\n");

            staffEdge.forEach(staffEdgeBuilder::append);

            staffEdgeBuilder.append("}");

            return new StaffEdge(staffEdgeBuilder.toString());
        }
    }
}
