package pwr.pracainz.configuration.customMappers.tagImportanceMapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

import java.io.IOException;

public class TagImportanceSerializer extends StdSerializer<TagImportance> {
    public TagImportanceSerializer() {
        this(null);
    }

    public TagImportanceSerializer(Class<TagImportance> t) {
        super(t);
    }

    @Override
    public void serialize(TagImportance value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getFormattedImportance());
    }
}
