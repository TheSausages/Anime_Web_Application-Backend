package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.connections.charackters.CharacterSort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaffCharacterMediaArgumentsTest {

	@Test
	void StaffCharacterMediaArgumentsBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder().buildStaffCharacterMediaArguments());

		//then
		assertEquals(exception.getMessage(), "Staff Media Character Arguments should posses at least 1 parameter!");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_MediaSortArray1Element_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ID};

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.sort(sorts)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [ID])");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_ManyMediaSortArray1Element_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ID};
		CharacterSort[] sorts1 = new CharacterSort[]{CharacterSort.ROLE_DESC};

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [ID])");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_MediaSortArrayManyElement_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ID, CharacterSort.ROLE};

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.sort(sorts)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [ID, ROLE])");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_ManyMediaSortArrayManyElement_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.RELEVANCE, CharacterSort.ROLE};
		CharacterSort[] sorts1 = new CharacterSort[]{CharacterSort.FAVOURITES, CharacterSort.ROLE_DESC};

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [RELEVANCE, ROLE])");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_OnList_NoException() {
		//given

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.onList()
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(onList: true)");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_ManyOnList_NoException() {
		//given

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.onList()
				.onList()
				.onList()
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(onList: true)");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_Page_NoException() {
		//given
		int page = 5;

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.page(page)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(page: 5)");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_ManyPage_NoException() {
		//given
		int page = 12;
		int page1 = 8;

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.page(page)
				.page(page1)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(page: 12)");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_PerPage_NoException() {
		//given
		int perPage = 20;

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.perPage(perPage)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(perPage: 20)");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_ManyPerPage_NoException() {
		//given
		int perPage = 9;
		int perPage1 = 20;

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.perPage(perPage)
				.perPage(perPage1)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(perPage: 9)");
	}

	@Test
	void StaffCharacterMediaArgumentsBuilder_All_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.RELEVANCE, CharacterSort.FAVOURITES};
		int page = 2;
		int perPage = 10;

		//when
		StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
				.sort(sorts)
				.perPage(perPage)
				.onList()
				.page(page)
				.buildStaffCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterMediaArgumentsString(), "(sort: [RELEVANCE, FAVOURITES], perPage: 10, onList: true, page: 2)");
	}
}