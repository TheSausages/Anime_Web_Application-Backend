package pwr.pracainz.configuration.customMappers.animeUserStatus;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;

import java.io.IOException;

public class AnimeUserStatusSerializer extends StdSerializer<AnimeUserStatus> {
    public AnimeUserStatusSerializer() {
        this(null);
    }

    public AnimeUserStatusSerializer(Class<AnimeUserStatus> t) {
        super(t);
    }

    @Override
    public void serialize(AnimeUserStatus value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getFormattedStatus());
    }
}
