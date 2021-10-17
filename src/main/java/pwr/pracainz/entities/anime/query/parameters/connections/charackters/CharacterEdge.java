package pwr.pracainz.entities.anime.query.parameters.connections.charackters;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.connections.staff.Staff;
import pwr.pracainz.entities.anime.query.parameters.connections.staff.StaffLanguage;
import pwr.pracainz.entities.anime.query.parameters.connections.staff.StaffRoleType;
import pwr.pracainz.entities.anime.query.parameters.connections.staff.StaffSort;
import pwr.pracainz.entities.anime.query.queryElements.Media.Media;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class CharacterEdge {
	private final String characterEdgeString;

	private CharacterEdge(String characterEdgeString) {
		this.characterEdgeString = characterEdgeString;
	}

	public static CharacterEdgeBuilder getCharacterEdgeBuilder() {
		return new CharacterEdgeBuilder();
	}

	public String getCharacterEdgeWithoutFieldName() {
		return this.characterEdgeString.substring(14);
	}

	@Override
	public String toString() {
		return characterEdgeString;
	}

	public static final class CharacterEdgeBuilder {
		private final Set<ParameterString> characterEdge = new LinkedHashSet<>();

		public CharacterEdgeBuilder node(Character character) {
			characterEdge.add(new ParameterString("node " + character.getCharacterStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder id() {
			characterEdge.add(new ParameterString("id\n"));
			return this;
		}

		public CharacterEdgeBuilder role() {
			characterEdge.add(new ParameterString("role\n"));
			return this;
		}

		public CharacterEdgeBuilder name() {
			characterEdge.add(new ParameterString("name\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(Staff staff) {
			characterEdge.add(new ParameterString("voiceActors " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(StaffLanguage language, Staff staff) {
			characterEdge.add(new ParameterString("voiceActors(language: " + language.name() + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(StaffSort[] staffSort, Staff staff) {
			characterEdge.add(new ParameterString("voiceActors(sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(StaffLanguage language, StaffSort[] staffSort, Staff staff) {
			characterEdge.add(new ParameterString("voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffRoleType roleType) {
			characterEdge.add(new ParameterString("voiceActorsRoles " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffLanguage language, StaffRoleType roleType) {
			characterEdge.add(new ParameterString("voiceActorsRoles(language: " + language.name() + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffSort[] staffSort, StaffRoleType roleType) {
			characterEdge.add(new ParameterString("voiceActorsRoles(sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffLanguage language, StaffSort[] staffSort, StaffRoleType roleType) {
			characterEdge.add(new ParameterString("voiceActorsRoles(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder media(Media media) {
			characterEdge.add(new ParameterString("media " + media + "\n"));
			return this;
		}

		public CharacterEdgeBuilder favouriteOrder() {
			characterEdge.add(new ParameterString("favouriteOrder\n"));
			return this;
		}

		public CharacterEdge buildCharacterEdge() {
			if (characterEdge.isEmpty()) {
				throw new IllegalStateException("Character Edge should posses at least 1 parameter!");
			}

			StringBuilder characterEdgeBuilder = new StringBuilder("characterEdge {\n");

			characterEdge.forEach(characterEdgeBuilder::append);

			characterEdgeBuilder.append("}");

			return new CharacterEdge(characterEdgeBuilder.toString());
		}
	}
}
