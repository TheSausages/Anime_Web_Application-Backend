package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.connections.charackters.Character;
import pwr.pracainz.entities.anime.query.parameters.connections.charackters.CharacterConnection;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaArguments;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaConnection;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaEdge;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateField;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaffTest {

	@Test
	void StaffBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> Staff.getStaffBuilder().buildStaff());

		//then
		assertEquals(exception.getMessage(), "Staff should posses at least 1 parameter!");
	}

	@Test
	void StaffBuilder_Id_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.id()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nid\n}");
	}

	@Test
	void StaffBuilder_IdWithoutFieldName_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.id()
				.buildStaff();

		//then
		assertEquals(staff.getStaffWithoutFieldName(), "{\nid\n}");
	}

	@Test
	void StaffBuilder_ManyId_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.id()
				.id()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nid\n}");
	}

	@Test
	void StaffBuilder_Name_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.name()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nname {\nfirst\nmiddle\nlast\nfull\nnative\n}\n}");
	}

	@Test
	void StaffBuilder_ManyName_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.name()
				.name()
				.name()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nname {\nfirst\nmiddle\nlast\nfull\nnative\n}\n}");
	}

	@Test
	void StaffBuilder_LanguageV2_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.languageV2()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nlanguageV2\n}");
	}

	@Test
	void StaffBuilder_ManyLanguageV2_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.languageV2()
				.languageV2()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nlanguageV2\n}");
	}

	@Test
	void StaffBuilder_Image_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.image()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nimage {\nlarge\nmedium\n}\n}");
	}

	@Test
	void StaffBuilder_ManyImage_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.image()
				.image()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nimage {\nlarge\nmedium\n}\n}");
	}

	@Test
	void StaffBuilder_Description_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.description()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ndescription\n}");
	}

	@Test
	void StaffBuilder_ManyDescription_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.description()
				.description()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ndescription\n}");
	}

	@Test
	void StaffBuilder_DescriptionAsHtml_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.descriptionAsHtml()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ndescription(asHtml: true)\n}");
	}

	@Test
	void StaffBuilder_ManyDescriptionAsHtml_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.descriptionAsHtml()
				.descriptionAsHtml()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ndescription(asHtml: true)\n}");
	}

	@Test
	void StaffBuilder_PrimaryOccupations_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.primaryOccupations()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nprimaryOccupations\n}");
	}

	@Test
	void StaffBuilder_ManyPrimaryOccupations_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.primaryOccupations()
				.primaryOccupations()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nprimaryOccupations\n}");
	}

	@Test
	void StaffBuilder_Gender_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.gender()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ngender\n}");
	}

	@Test
	void StaffBuilder_ManyGender_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.gender()
				.gender()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ngender\n}");
	}

	@Test
	void StaffBuilder_DateOfBirth_NoException() {
		//given
		FuzzyDateField fuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.dateOfBirth)
				.allAndBuild();

		//when
		Staff staff = Staff.getStaffBuilder()
				.dateOfBirth(fuzzyDateField)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ndateOfBirth {\nyear\nmonth\nday\n}\n}");
	}

	@Test
	void StaffBuilder_ManyDateOfBirth_NoException() {
		//given
		FuzzyDateField fuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.dateOfBirth)
				.allAndBuild();

		//when
		Staff staff = Staff.getStaffBuilder()
				.dateOfBirth(fuzzyDateField)
				.dateOfBirth(fuzzyDateField)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ndateOfBirth {\nyear\nmonth\nday\n}\n}");
	}

	@Test
	void StaffBuilder_DateOfDeath_NoException() {
		//given
		FuzzyDateField fuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.dateOfBirth)
				.allAndBuild();

		//when
		Staff staff = Staff.getStaffBuilder()
				.dateOfDeath(fuzzyDateField)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ndateOfDeath {\nyear\nmonth\nday\n}\n}");
	}

	@Test
	void StaffBuilder_ManyDateOfDeath_NoException() {
		//given
		FuzzyDateField fuzzyDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.dateOfBirth)
				.allAndBuild();

		//when
		Staff staff = Staff.getStaffBuilder()
				.dateOfDeath(fuzzyDateField)
				.dateOfDeath(fuzzyDateField)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ndateOfDeath {\nyear\nmonth\nday\n}\n}");
	}

	@Test
	void StaffBuilder_Age_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.age()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nage\n}");
	}

	@Test
	void StaffBuilder_ManyAge_NoException() {
		//given

		//when
		Staff staff = Staff.getStaffBuilder()
				.age()
				.age()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nage\n}");
	}

	@Test
	void StaffBuilder_YearsActive_NoException() {
		//given;

		//when
		Staff staff = Staff.getStaffBuilder()
				.yearsActive()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nyearsActive\n}");
	}

	@Test
	void StaffBuilder_ManyYearsActive_NoException() {
		//given;

		//when
		Staff staff = Staff.getStaffBuilder()
				.yearsActive()
				.yearsActive()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nyearsActive\n}");
	}

	@Test
	void StaffBuilder_HomeTown_NoException() {
		//given;

		//when
		Staff staff = Staff.getStaffBuilder()
				.homeTown()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nhomeTown\n}");
	}

	@Test
	void StaffBuilder_ManyHomeTown_NoException() {
		//given;

		//when
		Staff staff = Staff.getStaffBuilder()
				.homeTown()
				.homeTown()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nhomeTown\n}");
	}

	@Test
	void StaffBuilder_SiteUrl_NoException() {
		//given;

		//when
		Staff staff = Staff.getStaffBuilder()
				.siteUrl()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nsiteUrl\n}");
	}

	@Test
	void StaffBuilder_ManySiteUrl_NoException() {
		//given;

		//when
		Staff staff = Staff.getStaffBuilder()
				.siteUrl()
				.siteUrl()
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nsiteUrl\n}");
	}

	@Test
	void StaffBuilder_StaffMedia_NoException() {
		//given;
		MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
				.id()
				.buildMediaEdge();
		MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
				.edge(edge)
				.buildMediaConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.staffMedia(connection)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nstaffMedia {\nedges {\nid\n}\n}\n}");
	}

	@Test
	void StaffBuilder_ManyStaffMedia_NoException() {
		//given;
		MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
				.staffRole()
				.buildMediaEdge();
		MediaEdge edge1 = MediaEdge.getMediaConnectionBuilder()
				.id()
				.buildMediaEdge();
		MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
				.edge(edge)
				.buildMediaConnection();
		MediaConnection connection1 = MediaConnection.getMediaConnectionBuilder()
				.edge(edge1)
				.buildMediaConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.staffMedia(connection)
				.staffMedia(connection1)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nstaffMedia {\nedges {\nstaffRole\n}\n}\n}");
	}

	@Test
	void StaffBuilder_StaffMediaWithArguments_NoException() {
		//given;
		MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
				.sort(new MediaSort[]{MediaSort.ID})
				.buildCharacterMediaArguments();
		MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
				.id()
				.buildMediaEdge();
		MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
				.edge(edge)
				.buildMediaConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.staffMedia(arguments, connection)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nstaffMedia(sort: [ID]) {\nedges {\nid\n}\n}\n}");
	}

	@Test
	void StaffBuilder_ManyStaffMediaWithArguments_NoException() {
		//given;
		MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder()
				.sort(new MediaSort[]{MediaSort.EPISODES})
				.buildCharacterMediaArguments();
		MediaArguments arguments1 = MediaArguments.getMediaArgumentsBuilder()
				.sort(new MediaSort[]{MediaSort.ID})
				.buildCharacterMediaArguments();
		MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
				.staffRole()
				.buildMediaEdge();
		MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
				.edge(edge)
				.buildMediaConnection();
		MediaEdge edge1 = MediaEdge.getMediaConnectionBuilder()
				.id()
				.buildMediaEdge();
		MediaConnection connection1 = MediaConnection.getMediaConnectionBuilder()
				.edge(edge1)
				.buildMediaConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.staffMedia(arguments, connection)
				.staffMedia(arguments1, connection1)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\nstaffMedia(sort: [EPISODES]) {\nedges {\nstaffRole\n}\n}\n}");
	}

	@Test
	void StaffBuilder_Characters_NoException() {
		//given;
		Character character = Character.getCharacterBuilder()
				.id()
				.buildCharacter();
		CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
				.nodes(character)
				.buildCharacterConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.characters(characterConnection)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ncharacters {\nnodes {\nid\n}\n}\n}");
	}

	@Test
	void StaffBuilder_ManyCharacters_NoException() {
		//given;
		Character character = Character.getCharacterBuilder()
				.age()
				.buildCharacter();
		CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
				.nodes(character)
				.buildCharacterConnection();
		Character character1 = Character.getCharacterBuilder()
				.id()
				.buildCharacter();
		CharacterConnection characterConnection1 = CharacterConnection.getCharacterConnectionBuilder()
				.nodes(character1)
				.buildCharacterConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.characters(characterConnection)
				.characters(characterConnection1)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ncharacters {\nnodes {\nage\n}\n}\n}");
	}

	@Test
	void StaffBuilder_CharactersWithStaffCharactersArguments_NoException() {
		//given;
		StaffCharactersArguments staffCharactersArguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.page(5)
				.perPage(10)
				.buildCharacterMediaArguments();
		Character character = Character.getCharacterBuilder()
				.id()
				.buildCharacter();
		CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
				.nodes(character)
				.buildCharacterConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.characters(staffCharactersArguments, characterConnection)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ncharacters(page: 5, perPage: 10) {\nnodes {\nid\n}\n}\n}");
	}

	@Test
	void StaffBuilder_ManyCharactersWithStaffCharactersArguments_NoException() {
		//given;
		StaffCharactersArguments staffCharactersArguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.page(5)
				.perPage(10)
				.buildCharacterMediaArguments();
		StaffCharactersArguments staffCharactersArguments1 = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.page(10)
				.perPage(15)
				.buildCharacterMediaArguments();
		Character character = Character.getCharacterBuilder()
				.id()
				.buildCharacter();
		CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
				.nodes(character)
				.buildCharacterConnection();
		Character character1 = Character.getCharacterBuilder()
				.modNotes()
				.buildCharacter();
		CharacterConnection characterConnection1 = CharacterConnection.getCharacterConnectionBuilder()
				.nodes(character1)
				.buildCharacterConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.characters(staffCharactersArguments, characterConnection)
				.characters(staffCharactersArguments1, characterConnection1)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ncharacters(page: 5, perPage: 10) {\nnodes {\nid\n}\n}\n}");
	}

	@Test
	void StaffBuilder_CharactersMedia_NoException() {
		//given;
		MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
				.characterName()
				.buildMediaEdge();
		MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
				.edge(edge)
				.buildMediaConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.characterMedia(connection)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ncharacterMedia {\nedges {\ncharacterName\n}\n}\n}");
	}

	@Test
	void StaffBuilder_ManyCharactersMedia_NoException() {
		//given;
		MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
				.characterName()
				.buildMediaEdge();
		MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
				.edge(edge)
				.buildMediaConnection();
		MediaEdge edge1 = MediaEdge.getMediaConnectionBuilder()
				.id()
				.buildMediaEdge();
		MediaConnection connection1 = MediaConnection.getMediaConnectionBuilder()
				.edge(edge1)
				.buildMediaConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.characterMedia(connection)
				.characterMedia(connection1)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ncharacterMedia {\nedges {\ncharacterName\n}\n}\n}");
	}

	@Test
	void StaffBuilder_CharactersMediaWithStaffCharacterMediaArguments_NoException() {
		//given;
		int page = 7;
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.page(page)
				.buildStaffCharacterMediaArguments();
		MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
				.characterName()
				.buildMediaEdge();
		MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
				.edge(edge)
				.buildMediaConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.characterMedia(arguments, connection)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ncharacterMedia(page: 7) {\nedges {\ncharacterName\n}\n}\n}");
	}

	@Test
	void StaffBuilder_ManyCharactersMediaWithStaffCharacterMediaArguments_NoException() {
		//given;
		int page = 7;
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.onList()
				.buildStaffCharacterMediaArguments();
		StaffCharacterMediaArguments arguments1 = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.page(page)
				.buildStaffCharacterMediaArguments();
		MediaEdge edge = MediaEdge.getMediaConnectionBuilder()
				.characterName()
				.buildMediaEdge();
		MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
				.edge(edge)
				.buildMediaConnection();
		MediaEdge edge1 = MediaEdge.getMediaConnectionBuilder()
				.id()
				.buildMediaEdge();
		MediaConnection connection1 = MediaConnection.getMediaConnectionBuilder()
				.edge(edge1)
				.buildMediaConnection();

		//when
		Staff staff = Staff.getStaffBuilder()
				.characterMedia(arguments, connection)
				.characterMedia(arguments1, connection1)
				.buildStaff();

		//then
		assertEquals(staff.getStaffString(), "staff {\ncharacterMedia(onList: true) {\nedges {\ncharacterName\n}\n}\n}");
	}
}