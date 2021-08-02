package pwr.pracainz.entities.anime.query.parameters.connections.media;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.media.MediaSort;
import pwr.pracainz.entities.anime.query.parameters.media.MediaType;

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
        private final Set<ParameterString> mediaArguments = new LinkedHashSet<>();

        public MediaArgumentsBuilder sort(MediaSort[] sorts) {
            mediaArguments.add(new ParameterString("sort: " + Arrays.toString(sorts) + ", "));
            return this;
        }

        public MediaArgumentsBuilder type(MediaType type) {
            mediaArguments.add(new ParameterString("type: " + type.name() + ", "));
            return this;
        }

        public MediaArgumentsBuilder onList() {
            mediaArguments.add(new ParameterString("onList: true"  + ", "));
            return this;
        }

        public MediaArgumentsBuilder page(int page) {
            mediaArguments.add(new ParameterString("page: " + page + ", "));
            return this;
        }

        public MediaArgumentsBuilder perPage(int perPage) {
            mediaArguments.add(new ParameterString("perPage: " + perPage + ", "));
            return this;
        }

        public MediaArguments buildCharacterMediaArguments() {
            if (mediaArguments.isEmpty()) { throw new IllegalStateException("Media Arguments should posses at least 1 parameter!"); }

            StringBuilder MediaArguments = new StringBuilder("(");

            this.mediaArguments.forEach(MediaArguments::append);

            MediaArguments.delete(MediaArguments.length() - 2, MediaArguments.length()).append(")");

            return new MediaArguments(MediaArguments.toString());
        }
    }
}
