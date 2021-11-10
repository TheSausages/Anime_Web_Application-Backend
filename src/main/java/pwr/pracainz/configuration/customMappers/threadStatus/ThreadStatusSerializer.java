package pwr.pracainz.configuration.customMappers.threadStatus;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import java.io.IOException;

/**
 * Custom serializer for the {@link ThreadStatus} class.
 * It serializes to the {@link ThreadStatus#formattedStatus}.
 */
public class ThreadStatusSerializer extends StdSerializer<ThreadStatus> {
	public ThreadStatusSerializer() {
		this(null);
	}

	public ThreadStatusSerializer(Class<ThreadStatus> t) {
		super(t);
	}

	@Override
	public void serialize(ThreadStatus value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(value.getFormattedStatus());
	}
}
