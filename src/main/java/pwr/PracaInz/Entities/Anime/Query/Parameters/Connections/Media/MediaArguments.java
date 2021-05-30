package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaSort;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.MediaType;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaArguments {
    private final String MediaArgumentsString;

    private MediaArguments(String MediaArgumentsString) {
        this.MediaArgumentsString = MediaArgumentsString;
    }

    public static MediaArgumentsBuilder getMediaArgumentsBuilder() {
        return new MediaArgumentsBuilder();
    }

    @Override
    public String toString() {
        return MediaArgumentsString;
    }

    public static final class MediaArgumentsBuilder {
        private final Set<ParameterString> MediaArguments = new LinkedHashSet<>();

        public MediaArgumentsBuilder sort(MediaSort[] sorts) {
            MediaArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
            return this;
        }

        public MediaArgumentsBuilder type(MediaType type) {
            MediaArguments.add(new ParameterString("type: " + type.name() + ", "));
            return this;
        }

        public MediaArgumentsBuilder onList() {
            MediaArguments.add(new ParameterString("onList: true"  + ", "));
            return this;
        }

        public MediaArgumentsBuilder page(int page) {
            MediaArguments.add(new ParameterString("page: " + page + ", "));
            return this;
        }

        public MediaArgumentsBuilder perPage(int perPage) {
            MediaArguments.add(new ParameterString("perPage: " + perPage + ", "));
            return this;
        }

        public MediaArguments buildCharacterMediaArguments() {
            if (MediaArguments.isEmpty()) { throw new IllegalStateException("Media Arguments should posses at least 1 parameter!"); }

            StringBuilder MediaArguments = new StringBuilder("(");

            this.MediaArguments.forEach(MediaArguments::append);

            MediaArguments.delete(MediaArguments.length() - 2, MediaArguments.length()).append(")");

            return new MediaArguments(MediaArguments.toString());
        }
    }
}
