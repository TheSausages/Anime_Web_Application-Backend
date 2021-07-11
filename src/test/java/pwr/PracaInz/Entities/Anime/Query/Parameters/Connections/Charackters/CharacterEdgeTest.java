package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters;

import org.junit.jupiter.api.Test;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.Staff;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffLanguage;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffRoleType;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Staff.StaffSort;

import static org.junit.jupiter.api.Assertions.*;

class CharacterEdgeTest {
    @Test
    void CharacterEdgeBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> CharacterEdge.getCharacterEdgeBuilder().buildCharacterEdge());

        assertEquals(exception.getMessage(), "Character Edge should posses at least 1 parameter!");
    }

    @Test
    void CharacterEdgeBuilder_Node_NoException() {
        //given
        Character character = Character.getCharacterBuilder()
                .id()
                .buildCharacter();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .node(character)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nnode {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyNode_NoException() {
        //given
        Character character = Character.getCharacterBuilder()
                .age()
                .buildCharacter();
        Character character1 = Character.getCharacterBuilder()
                .id()
                .buildCharacter();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .node(character)
                .node(character1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nnode {\nage\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_NodeWithoutFieldName_NoException() {
        //given
        Character character = Character.getCharacterBuilder()
                .id()
                .buildCharacter();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .node(character)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nnode {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyNodeWithoutFieldName_NoException() {
        //given
        Character character = Character.getCharacterBuilder()
                .age()
                .buildCharacter();
        Character character1 = Character.getCharacterBuilder()
                .id()
                .buildCharacter();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .node(character)
                .node(character1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nnode {\nage\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_Id_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .id()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nid\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyId_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .id()
                .id()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nid\n}");
    }

    @Test
    void CharacterEdgeBuilder_IdWithoutFieldName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .id()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyIdWithoutFieldName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .id()
                .id()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nid\n}");
    }

    @Test
    void CharacterEdgeBuilder_Role_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .role()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nrole\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyRole_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .role()
                .role()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nrole\n}");
    }

    @Test
    void CharacterEdgeBuilder_RoleWithoutFieldName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .role()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nrole\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyRoleWithoutFieldName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .role()
                .role()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nrole\n}");
    }

    @Test
    void CharacterEdgeBuilder_Name_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .name()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nname\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .name()
                .name()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nname\n}");
    }

    @Test
    void CharacterEdgeBuilder_NameWithoutFieldName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .name()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nname\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyNameWithoutFieldName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .name()
                .name()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nname\n}");
    }

    @Test
    void CharacterEdgeBuilder_FavouriteOrder_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .favouriteOrder()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nfavouriteOrder\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyFavouriteOrder_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .favouriteOrder()
                .favouriteOrder()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nfavouriteOrder\n}");
    }

    @Test
    void CharacterEdgeBuilder_FavouriteOrderWithoutFieldName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .favouriteOrder()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nfavouriteOrder\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyFavouriteOrderWithoutFieldName_NoException() {
        //given

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .favouriteOrder()
                .favouriteOrder()
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nfavouriteOrder\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActors_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(staff)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActors {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActors_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(staff)
                .voiceActors(staff1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActors {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(staff)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActors {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(staff)
                .voiceActors(staff1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActors {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsWithLanguage_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(StaffLanguage.ENGLISH, staff)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActors(language: ENGLISH) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsWithLanguage_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(StaffLanguage.ENGLISH, staff)
                .voiceActors(StaffLanguage.GERMAN, staff1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActors(language: ENGLISH) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsWithLanguageWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(StaffLanguage.ENGLISH, staff)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActors(language: ENGLISH) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsWithLanguageWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(StaffLanguage.ENGLISH, staff)
                .voiceActors(StaffLanguage.GERMAN, staff1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActors(language: ENGLISH) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsWithSort_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(new StaffSort[]{StaffSort.LANGUAGE}, staff)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActors(sort: [LANGUAGE]) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsWithSort_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(new StaffSort[]{StaffSort.LANGUAGE}, staff)
                .voiceActors(new StaffSort[]{StaffSort.FAVOURITES}, staff1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActors(sort: [LANGUAGE]) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsWithSortWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(new StaffSort[]{StaffSort.LANGUAGE}, staff)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActors(sort: [LANGUAGE]) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsWithSortWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(new StaffSort[]{StaffSort.LANGUAGE}, staff)
                .voiceActors(new StaffSort[]{StaffSort.FAVOURITES}, staff1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActors(sort: [LANGUAGE]) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsWithLanguageAndSort_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(StaffLanguage.GERMAN, new StaffSort[]{StaffSort.LANGUAGE}, staff)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActors(language: GERMAN, sort: [LANGUAGE]) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsWithLanguageAndSort_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(StaffLanguage.GERMAN, new StaffSort[]{StaffSort.LANGUAGE}, staff)
                .voiceActors(StaffLanguage.HEBREW, new StaffSort[]{StaffSort.FAVOURITES_DESC}, staff1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActors(language: GERMAN, sort: [LANGUAGE]) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsWithLanguageAndSortWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(StaffLanguage.GERMAN, new StaffSort[]{StaffSort.LANGUAGE}, staff)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActors(language: GERMAN, sort: [LANGUAGE]) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsWithLanguageAndSortWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .id()
                .buildStaff();
        Staff staff1 = Staff.getStaffBuilder()
                .id()
                .buildStaff();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActors(StaffLanguage.GERMAN, new StaffSort[]{StaffSort.LANGUAGE}, staff)
                .voiceActors(StaffLanguage.HEBREW, new StaffSort[]{StaffSort.FAVOURITES_DESC}, staff1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActors(language: GERMAN, sort: [LANGUAGE]) {\nid\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsRoles_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(roleType)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActorsRoles {\nvoiceActor {\nage\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsRoles_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();
        StaffRoleType roleType1 = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(roleType)
                .voiceActorsRoles(roleType1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActorsRoles {\nvoiceActor {\nage\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsRolesWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .age()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(roleType)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActorsRoles {\nvoiceActor {\nage\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsRolesWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .favourites()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();
        StaffRoleType roleType1 = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(roleType)
                .voiceActorsRoles(roleType1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActorsRoles {\nvoiceActor {\nfavourites\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsRolesWithLanguage_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .favourites()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(StaffLanguage.ENGLISH, roleType)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActorsRoles(language: ENGLISH) {\nvoiceActor {\nfavourites\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsRolesWithLanguage_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .favourites()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();
        StaffRoleType roleType1 = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(StaffLanguage.ENGLISH, roleType)
                .voiceActorsRoles(StaffLanguage.GERMAN, roleType1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActorsRoles(language: ENGLISH) {\nvoiceActor {\nfavourites\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsRolesWithLanguageWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .siteUrl()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(StaffLanguage.ENGLISH, roleType)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActorsRoles(language: ENGLISH) {\nvoiceActor {\nsiteUrl\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsRolesWithLanguageWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .siteUrl()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();
        StaffRoleType roleType1 = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(StaffLanguage.ENGLISH, roleType)
                .voiceActorsRoles(StaffLanguage.GERMAN, roleType1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActorsRoles(language: ENGLISH) {\nvoiceActor {\nsiteUrl\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsRolesWithSort_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .siteUrl()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(new StaffSort[]{StaffSort.LANGUAGE}, roleType)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActorsRoles(sort: [LANGUAGE]) {\nvoiceActor {\nsiteUrl\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsRolesWithSort_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .yearsActive()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();
        StaffRoleType roleType1 = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(new StaffSort[]{StaffSort.LANGUAGE}, roleType)
                .voiceActorsRoles(new StaffSort[]{StaffSort.FAVOURITES}, roleType1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActorsRoles(sort: [LANGUAGE]) {\nvoiceActor {\nyearsActive\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsRolesWithSortWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .yearsActive()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(new StaffSort[]{StaffSort.LANGUAGE}, roleType)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActorsRoles(sort: [LANGUAGE]) {\nvoiceActor {\nyearsActive\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsRolesWithSortWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .yearsActive()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();
        StaffRoleType roleType1 = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(new StaffSort[]{StaffSort.LANGUAGE}, roleType)
                .voiceActorsRoles(new StaffSort[]{StaffSort.FAVOURITES}, roleType1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActorsRoles(sort: [LANGUAGE]) {\nvoiceActor {\nyearsActive\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsRolesWithLanguageAndSort_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .name()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(StaffLanguage.GERMAN, new StaffSort[]{StaffSort.LANGUAGE}, roleType)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActorsRoles(language: GERMAN, sort: [LANGUAGE]) {\nvoiceActor {\nname {\nfirst\nmiddle\nlast\nfull\nnative\n}\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsRolesWithLanguageAndSort_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .name()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();
        StaffRoleType roleType1 = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(StaffLanguage.GERMAN, new StaffSort[]{StaffSort.LANGUAGE}, roleType)
                .voiceActorsRoles(StaffLanguage.HEBREW, new StaffSort[]{StaffSort.FAVOURITES_DESC}, roleType1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeString(), "characterEdge {\nvoiceActorsRoles(language: GERMAN, sort: [LANGUAGE]) {\nvoiceActor {\nname {\nfirst\nmiddle\nlast\nfull\nnative\n}\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_VoiceActorsRolesWithLanguageAndSortWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .name()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(StaffLanguage.GERMAN, new StaffSort[]{StaffSort.LANGUAGE}, roleType)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActorsRoles(language: GERMAN, sort: [LANGUAGE]) {\nvoiceActor {\nname {\nfirst\nmiddle\nlast\nfull\nnative\n}\n}\n}\n}");
    }

    @Test
    void CharacterEdgeBuilder_ManyVoiceActorsRolesWithLanguageAndSortWithoutFieldName_NoException() {
        //given
        Staff staff = Staff.getStaffBuilder()
                .name()
                .buildStaff();
        StaffRoleType roleType = StaffRoleType.getStaffRoleTypeBuilder()
                .voiceActor(staff)
                .buildStaffRoleType();
        StaffRoleType roleType1 = StaffRoleType.getStaffRoleTypeBuilder()
                .dubGroup()
                .buildStaffRoleType();

        //when
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .voiceActorsRoles(StaffLanguage.GERMAN, new StaffSort[]{StaffSort.LANGUAGE}, roleType)
                .voiceActorsRoles(StaffLanguage.HEBREW, new StaffSort[]{StaffSort.FAVOURITES_DESC}, roleType1)
                .buildCharacterEdge();

        //then
        assertEquals(edge.getCharacterEdgeWithoutFieldName(), "{\nvoiceActorsRoles(language: GERMAN, sort: [LANGUAGE]) {\nvoiceActor {\nname {\nfirst\nmiddle\nlast\nfull\nnative\n}\n}\n}\n}");
    }
}