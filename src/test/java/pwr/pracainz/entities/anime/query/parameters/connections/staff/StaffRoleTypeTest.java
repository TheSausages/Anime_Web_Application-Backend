package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		Staff staff = Staff.getStaffBuilder()
				.age()
				.buildStaff();

		//when
		StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
				.voiceActor(staff)
				.buildStaffRoleType();

		assertEquals(type.getStaffRoleTypeString(), "staffRoleType {\nvoiceActor {\nage\n}\n}");
	}

	@Test
	void StaffROleTypeBuilder_ManyVoiceActor_NoException() {
		//given
		Staff staff = Staff.getStaffBuilder()
				.homeTown()
				.buildStaff();
		Staff staff1 = Staff.getStaffBuilder()
				.age()
				.buildStaff();

		//when
		StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
				.voiceActor(staff)
				.voiceActor(staff1)
				.buildStaffRoleType();

		assertEquals(type.getStaffRoleTypeString(), "staffRoleType {\nvoiceActor {\nhomeTown\n}\n}");
	}

	@Test
	void StaffROleTypeBuilder_VoiceActorNoFieldName_NoException() {
		//given
		Staff staff = Staff.getStaffBuilder()
				.homeTown()
				.buildStaff();

		//when
		StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
				.voiceActor(staff)
				.buildStaffRoleType();

		assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor {\nhomeTown\n}\n}");
	}

	@Test
	void StaffROleTypeBuilder_ManyVoiceActorNoFieldName_NoException() {
		//given
		Staff staff = Staff.getStaffBuilder()
				.image()
				.buildStaff();
		Staff staff1 = Staff.getStaffBuilder()
				.homeTown()
				.buildStaff();

		//when
		StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
				.voiceActor(staff)
				.voiceActor(staff1)
				.buildStaffRoleType();

		assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor {\nimage {\nlarge\nmedium\n}\n}\n}");
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
		Staff staff = Staff.getStaffBuilder()
				.image()
				.buildStaff();

		//when
		StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
				.voiceActor(staff)
				.dubGroup()
				.buildStaffRoleType();

		assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor {\nimage {\nlarge\nmedium\n}\n}\ndubGroup\n}");
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
		Staff staff = Staff.getStaffBuilder()
				.image()
				.buildStaff();

		//when
		StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
				.voiceActor(staff)
				.dubGroup()
				.roleNotes()
				.buildStaffRoleType();

		assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor {\nimage {\nlarge\nmedium\n}\n}\ndubGroup\nroleNotes\n}");
	}

	@Test
	void StaffROleTypeBuilder_AllANdManyDubGroups_NoException() {
		//given
		Staff staff = Staff.getStaffBuilder()
				.image()
				.buildStaff();

		//when
		StaffRoleType type = StaffRoleType.getStaffRoleTypeBuilder()
				.voiceActor(staff)
				.dubGroup()
				.roleNotes()
				.dubGroup()
				.buildStaffRoleType();

		assertEquals(type.getStaffRoleTypeStringWithoutFieldName(), "{\nvoiceActor {\nimage {\nlarge\nmedium\n}\n}\ndubGroup\nroleNotes\n}");
	}
}