package pwr.pracainz.entities.anime.query.parameters.connections.staff;

import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.connections.charackters.CharacterSort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaffCharactersArgumentsTest {

	@Test
	void StaffCharactersArgumentsBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> StaffCharactersArguments.getStaffCharactersArgumentsBuilder().buildCharacterMediaArguments());

		//then
		assertEquals(exception.getMessage(), "Staff Character Arguments should posses at least 1 parameter!");
	}

	@Test
	void StaffCharactersArgumentsBuilder_MediaSortArray1Element_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ID};

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.sort(sorts)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(sort: [ID])");
	}

	@Test
	void StaffCharactersArgumentsBuilder_ManyMediaSortArray1Element_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ID};
		CharacterSort[] sorts1 = new CharacterSort[]{CharacterSort.ROLE_DESC};

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(sort: [ID])");
	}

	@Test
	void StaffCharactersArgumentsBuilder_MediaSortArrayManyElement_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.ID, CharacterSort.ROLE};

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.sort(sorts)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(sort: [ID, ROLE])");
	}

	@Test
	void StaffCharactersArgumentsBuilder_ManyMediaSortArrayManyElement_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.RELEVANCE, CharacterSort.ROLE};
		CharacterSort[] sorts1 = new CharacterSort[]{CharacterSort.FAVOURITES, CharacterSort.ROLE_DESC};

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.sort(sorts)
				.sort(sorts1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(sort: [RELEVANCE, ROLE])");
	}

	@Test
	void StaffCharactersArgumentsBuilder_Page_NoException() {
		//given
		int page = 5;

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.page(page)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(page: 5)");
	}

	@Test
	void StaffCharactersArgumentsBuilder_ManyPage_NoException() {
		//given
		int page = 12;
		int page1 = 8;

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.page(page)
				.page(page1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(page: 12)");
	}

	@Test
	void StaffCharactersArgumentsBuilder_PerPage_NoException() {
		//given
		int perPage = 20;

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.perPage(perPage)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(perPage: 20)");
	}

	@Test
	void StaffCharactersArgumentsBuilder_ManyPerPage_NoException() {
		//given
		int perPage = 9;
		int perPage1 = 20;

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.perPage(perPage)
				.perPage(perPage1)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(perPage: 9)");
	}

	@Test
	void StaffCharactersArgumentsBuilder_All_NoException() {
		//given
		CharacterSort[] sorts = new CharacterSort[]{CharacterSort.RELEVANCE, CharacterSort.FAVOURITES};
		int page = 2;
		int perPage = 10;

		//when
		StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
				.sort(sorts)
				.perPage(perPage)
				.page(page)
				.buildCharacterMediaArguments();

		//then
		assertEquals(arguments.getCharacterArgumentsString(), "(sort: [RELEVANCE, FAVOURITES], perPage: 10, page: 2)");
	}
}