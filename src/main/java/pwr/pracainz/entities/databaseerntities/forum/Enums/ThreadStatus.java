package pwr.pracainz.entities.databaseerntities.forum.Enums;

import lombok.Getter;

@Getter
public enum ThreadStatus {
    OPEN("Open"),
    CLOSED("Closed");

    private final String formattedStatus;

    ThreadStatus(String formattedStatus) {
        this.formattedStatus = formattedStatus;
    }
}

