package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffRoleTypeTest {

    @Test
    void StaffRoleTypeBuilder_NoParameter_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> StaffRoleType.getStaffRoleTypeBuilder().buildStaffRoleType());

        assertEquals(exception.getMessage(), "Staff Role Type should posses at least 1 parameter!");
    }

    @Test
    void StaffROleTypeBuilder_VoiceActor_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeString(), "staffRoleType {\nvoiceActor\n}");
    }

    @Test
    void StaffROleTypeBuilder_ManyVoiceActor_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor()
                .voiceActor()
                .voiceActor()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeString(), "staffRoleType {\nvoiceActor\n}");
    }

    @Test
    void StaffROleTypeBuilder_VoiceActorNoFieldName_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor\n}");
    }

    @Test
    void StaffROleTypeBuilder_ManyVoiceActorNoFieldName_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor()
                .voiceActor()
                .voiceActor()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor\n}");
    }

    @Test
    void StaffROleTypeBuilder_RoleNotes_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .roleNotes()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeString(), "staffRoleType {\nroleNotes\n}");
    }

    @Test
    void StaffROleTypeBuilder_ManyRoleNotes_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .roleNotes()
                .roleNotes()
                .roleNotes()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeString(), "staffRoleType {\nroleNotes\n}");
    }

    @Test
    void StaffROleTypeBuilder_RoleNotesNoFieldName_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .roleNotes()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nroleNotes\n}");
    }

    @Test
    void StaffROleTypeBuilder_ManyRoleNotesNoFieldName_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .roleNotes()
                .roleNotes()
                .roleNotes()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nroleNotes\n}");
    }

    @Test
    void StaffROleTypeBuilder_DubGroup_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeString(), "staffRoleType {\ndubGroup\n}");
    }

    @Test
    void StaffROleTypeBuilder_ManyDubGroup_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .dubGroup()
                .dubGroup()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeString(), "staffRoleType {\ndubGroup\n}");
    }

    @Test
    void StaffROleTypeBuilder_DubGroupNoFieldName_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\ndubGroup\n}");
    }

    @Test
    void StaffROleTypeBuilder_ManyDubGroupNoFieldName_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .dubGroup()
                .dubGroup()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\ndubGroup\n}");
    }

    @Test
    void StaffROleTypeBuilder_VoiceActorAndDubGroupNoFieldName_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor()
                .dubGroup()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor\ndubGroup\n}");
    }

    @Test
    void StaffROleTypeBuilder_ManyRoleNotesAndDubGroupNoFieldName_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .roleNotes()
                .dubGroup()
                .roleNotes()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nroleNotes\ndubGroup\n}");
    }

    @Test
    void StaffROleTypeBuilder_All_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor()
                .dubGroup()
                .roleNotes()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor\ndubGroup\nroleNotes\n}");
    }

    @Test
    void StaffROleTypeBuilder_AllANdManyDubGroups_NoException() {
        //given

        //when
        StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor()
                .dubGroup()
                .roleNotes()
                .dubGroup()
                .buildStaffRoleType();

        assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor\ndubGroup\nroleNotes\n}");
    }
}