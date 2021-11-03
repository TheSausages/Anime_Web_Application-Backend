package pwr.pracainz.configuration.customMappers.threadStatus;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;
import pwr.pracainz.exceptions.exceptions.CustomDeserializationException;

import java.io.IOException;
import java.util.Locale;

public class ThreadStatusDeserializer extends StdDeserializer<ThreadStatus> {
	public ThreadStatusDeserializer() {
		this(null);
	}

	public ThreadStatusDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public ThreadStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String status = "No value read";
		try {
			JsonNode statusNode = p.getCodec().readTree(p);
			status = statusNode instanceof TextNode ? statusNode.asText() : statusNode.path("status").asText();

			return ThreadStatus.valueOf(status.toUpperCase(Locale.ROOT));
		} catch (Exception e) {
			throw new CustomDeserializationException("general.an-error-occurred", String.format("Could not read thread status: %s", status));
		}
	}
}
