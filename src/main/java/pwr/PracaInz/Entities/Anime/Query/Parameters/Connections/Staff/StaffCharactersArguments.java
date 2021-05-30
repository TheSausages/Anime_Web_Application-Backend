package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.CharacterSort;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class StaffCharactersArguments {
    private final String characterArgumentsString;

    private StaffCharactersArguments(String characterArgumentsString) {
        this.characterArgumentsString = characterArgumentsString;
    }

    public static StaffCharactersArgumentsBuilder getStaffCharactersArgumentsBuilder() {
        return new StaffCharactersArgumentsBuilder();
    }

    @Override
    public String toString() {
        return characterArgumentsString;
    }

    public static final class StaffCharactersArgumentsBuilder {
        private final Set<ParameterString>  staffCharactersArguments = new LinkedHashSet<>();

        public StaffCharactersArgumentsBuilder sort(CharacterSort[] sorts) {
            staffCharactersArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
            return this;
        }

        public StaffCharactersArgumentsBuilder page(int page) {
            staffCharactersArguments.add(new ParameterString("page: " + page + ", "));
            return this;
        }

        public StaffCharactersArgumentsBuilder perPage(int perPage) {
            staffCharactersArguments.add(new ParameterString("perPage: " + perPage + ", "));
            return this;
        }

        public StaffCharactersArguments buildCharacterMediaArguments() {
            if (staffCharactersArguments.isEmpty()) { throw new IllegalStateException("Staff Character Arguments should posses at least 1 parameter!"); }

            StringBuilder staffCharacterMediaArguments = new StringBuilder("(");

            this.staffCharactersArguments.forEach(staffCharacterMediaArguments::append);

            staffCharacterMediaArguments.delete(staffCharacterMediaArguments.length() - 2, staffCharacterMediaArguments.length()).append(")");

            return new StaffCharactersArguments(staffCharacterMediaArguments.toString());
        }
    }
}
