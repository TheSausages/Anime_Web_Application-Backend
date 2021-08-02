package pwr.pracainz.entities.anime.query.parameters.media;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class MediaExternalLinks {
    private final String mediaExternalLinkString;

    private MediaExternalLinks(String mediaExternalLinkString) {
        this.mediaExternalLinkString = mediaExternalLinkString;
    }

    public static MediaExternalLinkStringBuilder getMediaExternalLinkStringBuilder() {
        return new MediaExternalLinkStringBuilder();
    }

    @Override
    public String toString() {
        return mediaExternalLinkString;
    }

    public static final class MediaExternalLinkStringBuilder {
        private final Set<ParameterString> mediaExternalLink = new LinkedHashSet<>();

        public MediaExternalLinkStringBuilder addId() {
            mediaExternalLink.add(new ParameterString("id\n"));
            return this;
        }

        public MediaExternalLinkStringBuilder addUrl() {
            mediaExternalLink.add(new ParameterString("url\n"));
            return this;
        }

        public MediaExternalLinkStringBuilder addSite() {
            mediaExternalLink.add(new ParameterString("site\n"));
            return this;
        }

        public MediaExternalLinks buildMediaExternalLinks() {
            if (mediaExternalLink.isEmpty()) { throw new IllegalStateException("External Links should posses at least 1 parameter!"); }

            StringBuilder externalLinksBuilder = new StringBuilder("externalLinks {\n");

            mediaExternalLink.forEach(externalLinksBuilder::append);

            externalLinksBuilder.append("}");

            return new MediaExternalLinks(externalLinksBuilder.toString());
        }
    }
}
