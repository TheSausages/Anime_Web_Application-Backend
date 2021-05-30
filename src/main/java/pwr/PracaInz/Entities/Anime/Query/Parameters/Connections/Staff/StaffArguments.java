package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class StaffArguments {
    private final String staffArgumentsString;

    private StaffArguments(String staffArgumentsString) {
        this.staffArgumentsString = staffArgumentsString;
    }

    public static StaffArgumentsBuilder getStaffArgumentsBuilder() {
        return new StaffArgumentsBuilder();
    }

    @Override
    public String toString() {
        return staffArgumentsString;
    }

    public static final class StaffArgumentsBuilder {
        private final Set<ParameterString> staffMediaArguments = new LinkedHashSet<>();

        public StaffArgumentsBuilder sort(StaffSort[] sorts) {
            staffMediaArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
            return this;
        }

        public StaffArgumentsBuilder page(int page) {
            staffMediaArguments.add(new ParameterString("page: " + page + ", "));
            return this;
        }

        public StaffArgumentsBuilder perPage(int perPage) {
            staffMediaArguments.add(new ParameterString("perPage: " + perPage + ", "));
            return this;
        }

        public StaffArguments buildStaffArguments() {
            if (staffMediaArguments.isEmpty()) { throw new IllegalStateException("Staff Arguments should posses at least 1 parameter!"); }

            StringBuilder characterMediaArguments = new StringBuilder("(");

            this.staffMediaArguments.forEach(characterMediaArguments::append);

            characterMediaArguments.delete(characterMediaArguments.length() - 2, characterMediaArguments.length()).append(")");

            return new StaffArguments(characterMediaArguments.toString());
        }
    }
}
