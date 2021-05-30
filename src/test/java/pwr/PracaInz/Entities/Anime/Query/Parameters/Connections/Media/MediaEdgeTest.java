package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media;

import org.junit.jupiter.api.Test;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.Character;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.Staff;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffLanguage;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffRoleType;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffSort;

import static org.junit.jupiter.api.Assertions.*;

class MediaEdgeTest {

    @Test
    void MediaEdgeBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> MediaEdge.getMediaConnectionBuilder().buildMediaEdge());

        //then
        assertEquals(exception.getMessage(), "Media Edge should posses at least 1 parameter!");
    }

    //dorobić
    @Test
    void MediaEdgeBuilder_Node_NoException() {
        //given

        //when

        //then
    }

    @Test
    void MediaEdgeBuilder_Id_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .id()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nid\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyId_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .id()
                .id()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nid\n}");
    }

    @Test
    void MediaEdgeBuilder_IdWithoutFieldName_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .id()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyIdWithoutFieldName_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .id()
                .id()
                .id()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void MediaEdgeBuilder_RelationType_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .relationType()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nrelationType\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyRelationType_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .relationType()
                .relationType()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nrelationType\n}");
    }

    @Test
    void MediaEdgeBuilder_RelationTypeWithVersion_NoException() {
        //given
        int version = 2;

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .relationType(version)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nrelationType(version: 2)\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyRelationTypeWithVersion_NoException() {
        //given
        int version = 9;
        int version1 = 2;

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .relationType(version)
                .relationType(version1)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nrelationType(version: 9)\n}");
    }

    @Test
    void MediaEdgeBuilder_IsMainStudio_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .isMainStudio()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nisMainStudio\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyIsMainStudio_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .isMainStudio()
                .isMainStudio()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nisMainStudio\n}");
    }

    @Test
    void MediaEdgeBuilder_Characters_NoException() {
        //given
        Character character = Character.getCharacterBuilder()
                .image()
                .buildCharacter();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .characters(character)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\ncharacters {\nimage {\nlarge\nmedium\n}\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyCharacters_NoException() {
        //given
        Character character = Character.getCharacterBuilder()
                .id()
                .buildCharacter();
        Character character1 = Character.getCharacterBuilder()
                .image()
                .buildCharacter();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .characters(character)
                .characters(character1)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\ncharacters {\nid\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_CharacterRole_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .characterRole()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\ncharacterRole\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyCharacterRole_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .characterRole()
                .characterRole()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\ncharacterRole\n}");
    }

    @Test
    void MediaEdgeBuilder_CharacterName_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .characterName()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\ncharacterName\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyCharacterName_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .characterName()
                .characterName()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\ncharacterName\n}");
    }

    @Test
    void MediaEdgeBuilder_RoleNotes_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .roleNotes()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nroleNotes\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyRoleNotes_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .roleNotes()
                .roleNotes()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nroleNotes\n}");
    }

    @Test
    void MediaEdgeBuilder_DubGroup_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .dubGroup()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\ndubGroup\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyDubGroup_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .dubGroup()
                .dubGroup()
                .dubGroup()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\ndubGroup\n}");
    }

    @Test
    void MediaEdgeBuilder_StaffRole_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .staffRole()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nstaffRole\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyStaffRole_NoException() {
        //given

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .staffRole()
                .staffRole()
                .staffRole()
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nstaffRole\n}");
    }

    @Test
    void MediaEdgeBuilder_VoiceActor_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActors(staff)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActors {\nage\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_ManyVoiceActor_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .siteUrl()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .age()
                .buildStaff();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActors(staff)
                .voiceActors(staff1)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActors {\nsiteUrl\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_VoiceActorWithStaffLanguage_NoException() {
        //given
        StaffLanguage staffLanguage = StaffLanguage.ENGLISH;
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActors(staffLanguage, staff)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActors(language: ENGLISH) {\nage\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_VoiceActorWithSort_NoException() {
        //given
        StaffSort[] sorts = new StaffSort[]{StaffSort.ROLE, StaffSort.ID};
        Staff staff = Staff.getStaffBuilder()
                .homeTown()
                .buildStaff();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActors(sorts, staff)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActors(sort: [ROLE, ID]) {\nhomeTown\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_VoiceActorWithSortAndStaffLanguage_NoException() {
        //given
        StaffSort[] sorts = new StaffSort[]{StaffSort.ROLE, StaffSort.ID};
        StaffLanguage staffLanguage = StaffLanguage.GERMAN;
        Staff staff = Staff.getStaffBuilder()
                .homeTown()
                .buildStaff();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActors(staffLanguage, sorts, staff)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActors(language: GERMAN, sort: [ROLE, ID]) {\nhomeTown\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_VoiceActorRoles_NoException() {
        //given
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActorsRoles(roleType)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActorRoles {\ndubGroup\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_VoiceActorRolesWithLanguage_NoException() {
        //given
        StaffLanguage staffLanguage = StaffLanguage.HEBREW;
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActorsRoles(staffLanguage, roleType)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActorRoles(language: HEBREW) {\ndubGroup\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_VoiceActorRolesWithSort_NoException() {
        //given
        StaffSort[] sorts = new StaffSort[]{StaffSort.ROLE_DESC, StaffSort.RELEVANCE};
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActorsRoles(sorts, roleType)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActorRoles(sort: [ROLE_DESC, RELEVANCE]) {\ndubGroup\n}\n}");
    }

    @Test
    void MediaEdgeBuilder_VoiceActorRolesWithSortAndLanguage_NoException() {
        //given
        StaffLanguage staffLanguage = StaffLanguage.KOREAN;
        StaffSort[] sorts = new StaffSort[]{StaffSort.ROLE_DESC, StaffSort.RELEVANCE};
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
                .voiceActorsRoles(staffLanguage, sorts, roleType)
                .buildMediaEdge();

        //then
        assertEquals(edge.getMediaEdgeString(), "mediaEdge {\nvoiceActorRoles(language: KOREAN, sort: [ROLE_DESC, RELEVANCE]) {\ndubGroup\n}\n}");
    }
}