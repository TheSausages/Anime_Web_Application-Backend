package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.CharacterSort;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class StaffCharacterMediaArguments {
    private final String characterMediaArgumentsString;

    private StaffCharacterMediaArguments(String characterMediaArgumentsString) {
        this.characterMediaArgumentsString = characterMediaArgumentsString;
    }

    public static StaffCharacterMediaArgumentsBuilder getStaffCharacterMediaArgumentsBuilder() {
        return new StaffCharacterMediaArgumentsBuilder();
    }

    @Override
    public String toString() {
        return characterMediaArgumentsString;
    }

    public static final class StaffCharacterMediaArgumentsBuilder {
        private final Set<ParameterString> staffCharacterMediaArguments = new LinkedHashSet<>();

        public StaffCharacterMediaArgumentsBuilder sort(CharacterSort[] sorts) {
            staffCharacterMediaArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
            return this;
        }

        public StaffCharacterMediaArgumentsBuilder onList() {
            staffCharacterMediaArguments.add(new ParameterString("onList: true, "));
            return this;
        }

        public StaffCharacterMediaArgumentsBuilder page(int page) {
            staffCharacterMediaArguments.add(new ParameterString("page: " + page + ", "));
            return this;
        }

        public StaffCharacterMediaArgumentsBuilder perPage(int perPage) {
            staffCharacterMediaArguments.add(new ParameterString("perPage: " + perPage + ", "));
            return this;
        }

        public StaffCharacterMediaArguments buildStaffCharacterMediaArguments() {
            if (staffCharacterMediaArguments.isEmpty()) { throw new IllegalStateException("Staff Media Character Arguments should posses at least 1 parameter!"); }

            StringBuilder staffMediaCharacterMediaArguments = new StringBuilder("(");

            this.staffCharacterMediaArguments.forEach(staffMediaCharacterMediaArguments::append);

            staffMediaCharacterMediaArguments.delete(staffMediaCharacterMediaArguments.length() - 2, staffMediaCharacterMediaArguments.length()).append(")");

            return new StaffCharacterMediaArguments(staffMediaCharacterMediaArguments.toString());
        }
    }
}
