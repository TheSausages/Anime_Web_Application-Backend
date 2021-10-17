package pwr.pracainz.configuration.customMappers.animeUserStatus;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;
import pwr.pracainz.exceptions.exceptions.CustomDeserializationException;

import java.io.IOException;
import java.util.Locale;

public class AnimeUserStatusDeserializer extends StdDeserializer<AnimeUserStatus> {
	public AnimeUserStatusDeserializer() {
		this(null);
	}

	public AnimeUserStatusDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public AnimeUserStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String status = "No value read";
		try {
			JsonNode statusNode = p.getCodec().readTree(p);
			status = statusNode instanceof TextNode ? statusNode.asText() : statusNode.path("status").asText();

			return AnimeUserStatus.valueOf(status.replace(' ', '_').toUpperCase(Locale.ROOT));
		} catch (Exception e) {
			throw new CustomDeserializationException(String.format("Could not read anime user status: %s", status));
		}
	}
}
