package pwr.PracaInz.Entities.Anime.Query.QueryElements.Media;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Media.MediaConnection;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateValue;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.*;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;
import pwr.PracaInz.Entities.Anime.Query.Parameters.QueryParameters;
import pwr.PracaInz.Entities.Anime.Query.Query;

import java.util.*;

@Getter
public class Media {
    private final String mediaString;

    private Media(String mediaString) {
        this.mediaString = mediaString;
    }

    public String getMediaStringWithoutFieldName() {
        return mediaString.substring(12);
    }

    public MediaBuilder getMediaBuilder(Field field) {
        return new MediaBuilder(field);
    }

    public static class MediaBuilder {
        public MediaBuilder(Field field) {
        }
    }
}
