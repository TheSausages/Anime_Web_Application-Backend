package pwr.pracainz.configuration.customMappers.tagImportanceMapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

import java.io.IOException;

/**
 * Custom serializer for the {@link TagImportance} class.
 * It serializes to the {@link AnimeUserStatus#formattedStatus}.
 */
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
