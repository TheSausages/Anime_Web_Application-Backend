package pwr.pracainz.entities.anime.query.parameters.connections.charackters;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaArguments;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaConnection;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateField;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class Character {
	private final String characterString;

	private Character(String characterString) {
		this.characterString = characterString;
	}

	public String getCharacterStringWithoutFieldName() {
		return this.characterString.substring(10);
	}

	public static CharacterBuilder getCharacterBuilder() {
		return new CharacterBuilder();
	}

	public static final class CharacterBuilder {
		private final Set<ParameterString> character = new LinkedHashSet<>();

		public CharacterBuilder id() {
			character.add(new ParameterString("id\n"));
			return this;
		}

		public CharacterBuilder name() {
			character.add(new ParameterString("name {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}\n"));
			return this;
		}

		public CharacterBuilder image() {
			character.add(new ParameterString("image {\nlarge\nmedium\n}\n"));
			return this;
		}

		public CharacterBuilder description() {
			character.add(new ParameterString("description\n"));
			return this;
		}

		public CharacterBuilder descriptionAsHtml() {
			character.add(new ParameterString("description(asHtml: true)\n"));
			return this;
		}

		public CharacterBuilder gender() {
			character.add(new ParameterString("gender\n"));
			return this;
		}

		public CharacterBuilder dateOfBirth(FuzzyDateField fuzzyDateField) {
			character.add(new ParameterString("dateOfBirth" + fuzzyDateField.getFuzzyDateStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterBuilder age() {
			character.add(new ParameterString("age\n"));
			return this;
		}

		public CharacterBuilder aniListSiteUrl() {
			character.add(new ParameterString("siteUrl\n"));
			return this;
		}

		public CharacterBuilder favourites() {
			character.add(new ParameterString("favourites\n"));
			return this;
		}

		public CharacterBuilder modNotes() {
			character.add(new ParameterString("modNotes\n"));
			return this;
		}

		public CharacterBuilder media(MediaConnection mediaConnection) {
			character.add(new ParameterString("media " + mediaConnection.getMediaConnectionWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterBuilder media(MediaArguments mediaArguments, MediaConnection mediaConnection) {
			character.add(new ParameterString("media" + mediaArguments.getMediaArgumentsString() + " " + mediaConnection.getMediaConnectionWithoutFieldName() + "\n"));
			return this;
		}

		public Character buildCharacter() {
			if (character.isEmpty()) {
				throw new IllegalStateException("Character should posses at least 1 parameter!");
			}

			StringBuilder StaffBuilder = new StringBuilder("character {\n");

			character.forEach(StaffBuilder::append);

			StaffBuilder.append("}");

			return new Character(StaffBuilder.toString());
		}
	}
}
