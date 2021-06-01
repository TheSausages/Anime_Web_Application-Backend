package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.Character;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.Staff;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffLanguage;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffRoleType;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffSort;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Media;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaEdge {
    private final String mediaEdgeString;

    private MediaEdge(String mediaEdgeString) {
        this.mediaEdgeString = mediaEdgeString;
    }

    public String getMediaEdgeWithoutFieldName() {
        return this.mediaEdgeString.substring(10);
    }

    public static MediaEdgeBuilder getMediaConnectionBuilder() {
        return new MediaEdgeBuilder();
    }

    public static final class MediaEdgeBuilder {
        private final Set<ParameterString> mediaEdge = new LinkedHashSet<>();

        public MediaEdgeBuilder node(Media media) {
            mediaEdge.add(new ParameterString("node" + media.getFieldOfMedia() + "\n"));
            return this;
        }

        public MediaEdgeBuilder id() {
            mediaEdge.add(new ParameterString("id\n"));
            return this;
        }

        public MediaEdgeBuilder relationType() {
            mediaEdge.add(new ParameterString("relationType\n"));
            return this;
        }

        public MediaEdgeBuilder relationType(int version) {
            mediaEdge.add(new ParameterString("relationType(version: " + version + ")\n"));
            return this;
        }

        public MediaEdgeBuilder isMainStudio() {
            mediaEdge.add(new ParameterString("isMainStudio\n"));
            return this;
        }

        public MediaEdgeBuilder characters(Character character) {
            mediaEdge.add(new ParameterString("characters " + character.getCharacterStringWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdgeBuilder characterRole() {
            mediaEdge.add(new ParameterString("characterRole\n"));
            return this;
        }

        public MediaEdgeBuilder characterName() {
            mediaEdge.add(new ParameterString("characterName\n"));
            return this;
        }

        public MediaEdgeBuilder roleNotes() {
            mediaEdge.add(new ParameterString("roleNotes\n"));
            return this;
        }

        public MediaEdgeBuilder dubGroup() {
            mediaEdge.add(new ParameterString("dubGroup\n"));
            return this;
        }

        public MediaEdgeBuilder staffRole() {
            mediaEdge.add(new ParameterString("staffRole\n"));
            return this;
        }

        public MediaEdgeBuilder voiceActors(Staff staff) {
            mediaEdge.add(new ParameterString("voiceActors " + staff.getStaffWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdgeBuilder voiceActors(StaffLanguage language, Staff staff) {
            mediaEdge.add(new ParameterString("voiceActors(language: " + language.name() + ") " + staff.getStaffWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdgeBuilder voiceActors(StaffSort[] staffSort, Staff staff) {
            mediaEdge.add(new ParameterString("voiceActors(sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdgeBuilder voiceActors(StaffLanguage language, StaffSort[] staffSort, Staff staff) {
            mediaEdge.add(new ParameterString("voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType) {
            mediaEdge.add(new ParameterString("voiceActorRoles " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdgeBuilder voiceActorsRoles(StaffLanguage language, StaffRoleType roleType) {
            mediaEdge.add(new ParameterString("voiceActorRoles(language: " + language.name() + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdgeBuilder voiceActorsRoles(StaffSort[] staffSort, StaffRoleType roleType) {
            mediaEdge.add(new ParameterString("voiceActorRoles(sort: " + Arrays.toString(staffSort) + ") " +roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdgeBuilder voiceActorsRoles(StaffLanguage language, StaffSort[] staffSort, StaffRoleType roleType) {
            mediaEdge.add(new ParameterString("voiceActorRoles(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
            return this;
        }

        public MediaEdge buildMediaEdge() {
            if (mediaEdge.isEmpty()) { throw new IllegalStateException("Media Edge should posses at least 1 parameter!"); }

            StringBuilder mediaEdgeBuilder = new StringBuilder("mediaEdge {\n");

            mediaEdge.forEach(mediaEdgeBuilder::append);

            mediaEdgeBuilder.append("}");

            return new MediaEdge(mediaEdgeBuilder.toString());
        }
    }
}
