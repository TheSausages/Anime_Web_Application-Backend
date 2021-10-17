package pwr.pracainz.entities.anime.query.parameters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

	@Test
	void UserBuilder_NoParams_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalStateException.class, () -> User.getUserBuilder().buildUser());

		//then
		assertEquals(exception.getMessage(), "User should posses at least 1 parameter!");
	}

	@Test
	void UserBuilder_Id_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.id()
				.buildUser();

		//then
		assertEquals(user.getUserString(), "user {\nid\n}");
	}

	@Test
	void UserBuilder_ManyId_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.id()
				.id()
				.buildUser();

		//then
		assertEquals(user.getUserString(), "user {\nid\n}");
	}

	@Test
	void UserBuilder_IdWithoutFieldName_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.id()
				.buildUser();

		//then
		assertEquals(user.getUserWithoutFieldName(), "{\nid\n}");
	}

	@Test
	void UserBuilder_ManyIdWithoutFieldName_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.id()
				.id()
				.id()
				.buildUser();

		//then
		assertEquals(user.getUserWithoutFieldName(), "{\nid\n}");
	}

	@Test
	void UserBuilder_Name_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.name()
				.buildUser();

		//then
		assertEquals(user.getUserString(), "user {\nname\n}");
	}

	@Test
	void UserBuilder_ManyName_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.name()
				.name()
				.buildUser();

		//then
		assertEquals(user.getUserString(), "user {\nname\n}");
	}

	@Test
	void UserBuilder_Avatar_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.avatar()
				.buildUser();

		//then
		assertEquals(user.getUserString(), "user {\navatar {\nlarge\nmedium\n}\n}");
	}

	@Test
	void UserBuilder_ManyAvatar_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.avatar()
				.avatar()
				.avatar()
				.buildUser();

		//then
		assertEquals(user.getUserString(), "user {\navatar {\nlarge\nmedium\n}\n}");
	}

	@Test
	void UserBuilder_AllWithManyName_NoException() {
		//given

		//when
		User user = User.getUserBuilder()
				.name()
				.avatar()
				.name()
				.id()
				.buildUser();

		//then
		assertEquals(user.getUserString(), "user {\nname\navatar {\nlarge\nmedium\n}\nid\n}");
	}
}