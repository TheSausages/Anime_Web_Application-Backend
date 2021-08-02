package pwr.pracainz.entities.anime.query.parameters.connections.studio;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaArguments;
import pwr.pracainz.entities.anime.query.parameters.connections.media.MediaConnection;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class Studio {
    private final String studioString;

    private Studio(String studioString) {
        this.studioString = studioString;
    }

    public String getStudioWithoutFieldName() {
        return this.studioString.substring(7);
    }

    public static StudioBuilder getStudioBuilder() {
        return new StudioBuilder();
    }

    public static final class StudioBuilder {
        private final Set<ParameterString> studio = new LinkedHashSet<>();

        public StudioBuilder id() {
            studio.add(new ParameterString("id\n"));
            return this;
        }

        public StudioBuilder name() {
            studio.add(new ParameterString("name\n"));
            return this;
        }

        public StudioBuilder isAnimationStudio() {
            studio.add(new ParameterString("isAnimationStudio\n"));
            return this;
        }

        public StudioBuilder media(MediaConnection mediaConnection) {
            studio.add(new ParameterString("media " + mediaConnection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public StudioBuilder media(MediaArguments mediaArguments, MediaConnection mediaConnection) {
            studio.add(new ParameterString("media" + mediaArguments.getMediaArgumentsString() + " " + mediaConnection.getMediaConnectionWithoutFieldName() + "\n"));
            return this;
        }

        public StudioBuilder siteUrl() {
            studio.add(new ParameterString("siteUrl\n"));
            return this;
        }

        public StudioBuilder favourites() {
            studio.add(new ParameterString("favourites\n"));
            return this;
        }

        public Studio buildStudio() {
            if (studio.isEmpty()) { throw new IllegalStateException("Studio should posses at least 1 parameter!"); }

            StringBuilder characterConnectionBuilder = new StringBuilder("studio {\n");

            studio.forEach(characterConnectionBuilder::append);

            characterConnectionBuilder.append("}");

            return new Studio(characterConnectionBuilder.toString());
        }
    }
}
