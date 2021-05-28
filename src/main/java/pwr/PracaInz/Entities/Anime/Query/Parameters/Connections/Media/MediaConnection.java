package pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media;

import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.Character;

public class MediaConnection {
    private final String mediaConnectionString;

    private MediaConnection(String mediaConnectionString) {
        this.mediaConnectionString = mediaConnectionString;
    }

    public String getMediaConnectionWithoutFieldName() {
        return this.mediaConnectionString.substring(16);
    }

    public static MediaConnectionBuilder getMediaConnectionBuilder() {
        return new MediaConnectionBuilder();
    }

    public static final class MediaConnectionBuilder {  }
}
