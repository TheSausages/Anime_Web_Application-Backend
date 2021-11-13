package pwr.pracainz.entities.databaseerntities.forum.Enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import pwr.pracainz.configuration.customMappers.threadStatus.ThreadStatusDeserializer;
import pwr.pracainz.configuration.customMappers.threadStatus.ThreadStatusSerializer;

/**
 * Enum representing the state of a {@link pwr.pracainz.entities.databaseerntities.forum.Thread}.
 * The {@link #formattedStatus} is used in Frontend.
 * Uses {@link ThreadStatusSerializer} to serialize and {@link ThreadStatusDeserializer} to deserialize.
 */
@Getter
@JsonSerialize(using = ThreadStatusSerializer.class)
@JsonDeserialize(using = ThreadStatusDeserializer.class)
public enum ThreadStatus {
	OPEN("Open"),
	CLOSED("Closed");

	private final String formattedStatus;

	ThreadStatus(String formattedStatus) {
		this.formattedStatus = formattedStatus;
	}
}

