package pwr.pracainz.entities.anime.query.parameters.connections.charackters;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class CharacterArguments {
	private final String characterArgumentsString;

	private CharacterArguments(String characterArgumentsString) {
		this.characterArgumentsString = characterArgumentsString;
	}

	public static CharacterArgumentsBuilder getCharacterArgumentsBuilder() {
		return new CharacterArgumentsBuilder();
	}

	@Override
	public String toString() {
		return characterArgumentsString;
	}

	public static final class CharacterArgumentsBuilder {
		private final Set<ParameterString> characterMediaArguments = new LinkedHashSet<>();

		public CharacterArgumentsBuilder mediaSort(CharacterSort[] sorts) {
			characterMediaArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
			return this;
		}

		public CharacterArgumentsBuilder role(CharacterRole characterRole) {
			characterMediaArguments.add(new ParameterString("role: " + characterRole + ", "));
			return this;
		}

		public CharacterArgumentsBuilder page(int page) {
			characterMediaArguments.add(new ParameterString("page: " + page + ", "));
			return this;
		}

		public CharacterArgumentsBuilder perPage(int perPage) {
			characterMediaArguments.add(new ParameterString("perPage: " + perPage + ", "));
			return this;
		}

		public CharacterArguments buildCharacterMediaArguments() {
			if (characterMediaArguments.isEmpty()) {
				throw new IllegalStateException("Character Arguments should posses at least 1 parameter!");
			}

			StringBuilder characterMediaArguments = new StringBuilder("(");

			this.characterMediaArguments.forEach(characterMediaArguments::append);

			characterMediaArguments.delete(characterMediaArguments.length() - 2, characterMediaArguments.length()).append(")");

			return new CharacterArguments(characterMediaArguments.toString());
		}
	}
}
