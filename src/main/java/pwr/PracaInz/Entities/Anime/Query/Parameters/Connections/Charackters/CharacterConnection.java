package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.PageInfo;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class CharacterConnection {
    private final String characterConnectionString;

    private CharacterConnection(String characterConnectionString) {
        this.characterConnectionString = characterConnectionString;
    }

    public String getCharacterConnectionWithoutFieldName() {
        return this.characterConnectionString.substring(20);
    }

    public static CharacterConnectionBuilder getCharacterConnectionBuilder() {
        return new CharacterConnectionBuilder();
    }

    public static final class CharacterConnectionBuilder {
        private final Set<ParameterString> characterConnection = new LinkedHashSet<>();

        public CharacterConnectionBuilder edges(CharacterEdge characterEdge) {

            characterConnection.add(new ParameterString("edges " + characterEdge.getCharacterEdgeWithoutFieldName() + "\n"));
            return this;
        }

        public CharacterConnectionBuilder nodes(Character character) {
            characterConnection.add(new ParameterString("nodes " + character.getCharacterStringWithoutFieldName() + "\n"));
            return this;
        }

        public CharacterConnectionBuilder pageInfo(PageInfo pageInfo) {
            characterConnection.add(new ParameterString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName() + "\n"));
            return this;
        }

        public CharacterConnection buildCharacterConnection() {
            if (characterConnection.isEmpty()) { throw new IllegalStateException("Character Connection should posses at least 1 parameter!"); }

            StringBuilder characterConnectionBuilder = new StringBuilder("characterConnection {\n");

            characterConnection.forEach(characterConnectionBuilder::append);

            characterConnectionBuilder.append("}");

            return new CharacterConnection(characterConnectionBuilder.toString());
        }
    }
}
