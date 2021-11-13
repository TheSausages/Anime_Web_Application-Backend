package pwr.pracainz.entities.databaseerntities.animeInfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import pwr.pracainz.configuration.customMappers.animeUserStatus.AnimeUserStatusDeserializer;
import pwr.pracainz.configuration.customMappers.animeUserStatus.AnimeUserStatusSerializer;

/**
 * Enum representing the status that a {@link pwr.pracainz.entities.databaseerntities.user.User} can have to an {@link Anime}.
 * Used in {@link AnimeUserStatus}. The {@link #formattedStatus} form is used in Frontend.
 * Uses {@link AnimeUserStatusSerializer} to serialize and {@link AnimeUserStatusDeserializer} to deserialize.
 */
@Getter
@JsonDeserialize(using = AnimeUserStatusDeserializer.class)
@JsonSerialize(using = AnimeUserStatusSerializer.class)
public enum AnimeUserStatus {
	NO_STATUS("No Status"),
	WATCHING("Watching"),
	COMPLETED("Completed"),
	DROPPED("Dropped"),
	PLAN_TO_WATCH("Plan to Watch");

	private final String formattedStatus;

	AnimeUserStatus(String formattedStatus) {
		this.formattedStatus = formattedStatus;
	}
}
