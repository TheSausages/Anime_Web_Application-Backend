package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaSort;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class CharacterMediaArguments {
    private final String characterMediaArgumentsString;

    private CharacterMediaArguments(String characterMediaArgumentsString) {
        this.characterMediaArgumentsString = characterMediaArgumentsString;
    }

    public static CharacterMediaArgumentsBuilder getCharacterMediaArgumentsBuilder() {
        return new CharacterMediaArgumentsBuilder();
    }

    @Override
    public String toString() {
        return characterMediaArgumentsString;
    }

    public static final class CharacterMediaArgumentsBuilder {
        private final Set<ParameterString> characterMediaArguments = new LinkedHashSet<>();

        public CharacterMediaArgumentsBuilder mediaSort(MediaSort[] sorts) {
            characterMediaArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
            return this;
        }

        public CharacterMediaArgumentsBuilder type(MediaType type) {
            characterMediaArguments.add(new ParameterString("type: " + type.name() + ", "));
            return this;
        }

        public CharacterMediaArgumentsBuilder onList() {
            characterMediaArguments.add(new ParameterString("onList: true"  + ", "));
            return this;
        }

        public CharacterMediaArgumentsBuilder page(int page) {
            characterMediaArguments.add(new ParameterString("page: " + page + ", "));
            return this;
        }

        public CharacterMediaArgumentsBuilder perPage(int perPage) {
            characterMediaArguments.add(new ParameterString("perPage: " + perPage + ", "));
            return this;
        }

        public CharacterMediaArguments buildCharacterMediaArguments() {
            if (characterMediaArguments.isEmpty()) { throw new IllegalStateException("Character should posses at least 1 parameter!"); }

            StringBuilder characterMediaArguments = new StringBuilder("(");

            this.characterMediaArguments.forEach(characterMediaArguments::append);

            characterMediaArguments.delete(characterMediaArguments.length() - 2, characterMediaArguments.length()).append(")");

            return new CharacterMediaArguments(characterMediaArguments.toString());
        }
    }
}
