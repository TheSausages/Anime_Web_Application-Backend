package pwr.pracainz.entities.databaseerntities.animeInfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import pwr.pracainz.configuration.customMappers.animeUserStatus.AnimeUserStatusDeserializer;
import pwr.pracainz.configuration.customMappers.animeUserStatus.AnimeUserStatusSerializer;

@Getter
@JsonDeserialize(using = AnimeUserStatusDeserializer.class)
@JsonSerialize(using = AnimeUserStatusSerializer.class)
public enum AnimeUserStatus {
    NO_STATUS("No Status"),
    WATCHING("Watching"),
    COMPLETED("Completed"),
    DROPPED("Dropped"),
    PLAN_TO_WATCH("Plan to Watch")
    ;

    private final String formattedStatus;
    AnimeUserStatus(String formattedStatus) {
        this.formattedStatus = formattedStatus;
    }
}
