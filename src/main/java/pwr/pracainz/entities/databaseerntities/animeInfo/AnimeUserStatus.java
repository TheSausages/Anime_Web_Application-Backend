package pwr.pracainz.entities.databaseerntities.animeInfo;

import lombok.Getter;

@Getter
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
