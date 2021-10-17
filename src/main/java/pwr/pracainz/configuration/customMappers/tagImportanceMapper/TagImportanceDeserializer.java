package pwr.pracainz.configuration.customMappers.tagImportanceMapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;
import pwr.pracainz.exceptions.exceptions.CustomDeserializationException;

import java.io.IOException;
import java.util.Locale;

public class TagImportanceDeserializer extends StdDeserializer<TagImportance> {
	public TagImportanceDeserializer() {
		this(null);
	}

	public TagImportanceDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public TagImportance deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String tagImportance = "No value read";
		try {
			JsonNode statusNode = p.getCodec().readTree(p);
			tagImportance = statusNode instanceof TextNode ? statusNode.asText() : statusNode.path("tagImportance").asText();

			return TagImportance.valueOf(tagImportance.toUpperCase(Locale.ROOT));
		} catch (Exception e) {
			throw new CustomDeserializationException(String.format("Could not read anime user status: %s", tagImportance));
		}
	}
}
