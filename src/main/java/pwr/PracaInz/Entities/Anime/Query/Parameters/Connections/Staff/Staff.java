package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.CharacterConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaArguments;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateField;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
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

        public StaffBuilder name() {
            staff.add(new ParameterString("name {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\n}\n"));
            return this;
        }

        public StaffBuilder languageV2() {
            staff.add(new ParameterString("languageV2\n"));
            return this;
        }

        public StaffBuilder image() {
            staff.add(new ParameterString("image {\nlarge\nmedium\n}\n"));
            return this;
        }

        public StaffBuilder description() {
            staff.add(new ParameterString("description\n"));
            return this;
        }

        public StaffBuilder descriptionAsHtml() {
            staff.add(new ParameterString("description(asHtml: true)\n"));
            return this;
        }

        public StaffBuilder primaryOccupations() {
            staff.add(new ParameterString("primaryOccupations\n"));
            return this;
        }

        public StaffBuilder gender() {
            staff.add(new ParameterString("gender\n"));
            return this;
        }

        public StaffBuilder dateOfBirth(FuzzyDateField fuzzyDateField) {
            staff.add(new ParameterString("dateOfBirth" + fuzzyDateField.getFuzzyDateStringWithoutFieldName() + "\n"));
            return this;
        }

        public StaffBuilder dateOfDeath(FuzzyDateField fuzzyDateField) {
            staff.add(new ParameterString("dateOfDeath" + fuzzyDateField.getFuzzyDateStringWithoutFieldName() + "\n"));
            return this;
        }

        public StaffBuilder age() {
            staff.add(new ParameterString("age\n"));
            return this;
        }

        public StaffBuilder yearsActive() {
            staff.add(new ParameterString("yearsActive\n"));
            return this;
        }

        public StaffBuilder homeTown() {
            staff.add(new ParameterString("homeTown\n"));
            return this;
        }

        public StaffBuilder siteUrl() {
            staff.add(new ParameterString("siteUrl\n"));
            return this;
        }

        public StaffBuilder staffMedia(MediaConnection mediaConnection) {
            staff.add(new ParameterString("staffMedia " + mediaConnection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public StaffBuilder staffMedia(MediaArguments mediaArguments, MediaConnection mediaConnection) {
            staff.add(new ParameterString("staffMedia" + mediaArguments.getMediaArgumentsString() + " " + mediaConnection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public StaffBuilder characters(CharacterConnection characterConnection) {
            staff.add(new ParameterString("characters " + characterConnection.getCharacterConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public StaffBuilder characters(StaffCharactersArguments staffCharactersArguments, CharacterConnection characterConnection) {
            staff.add(new ParameterString("characters" + staffCharactersArguments.getCharacterArgumentsString() + " " + characterConnection.getCharacterConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public StaffBuilder characterMedia(MediaConnection connection) {
            staff.add(new ParameterString("characterMedia " + connection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public StaffBuilder characterMedia(StaffCharacterMediaArguments arguments, MediaConnection connection) {
            staff.add(new ParameterString("characterMedia" + arguments.getCharacterMediaArgumentsString() + " " + connection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public StaffBuilder favourites() {
            staff.add(new ParameterString("favourites\n"));
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
