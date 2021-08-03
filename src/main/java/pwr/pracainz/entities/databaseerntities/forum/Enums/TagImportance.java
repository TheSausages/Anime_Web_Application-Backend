package pwr.pracainz.entities.databaseerntities.forum.Enums;

import lombok.Getter;

@Getter
public enum TagImportance {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    ADMIN("Admin");

    private final String formattedImportance;

    TagImportance(String formattedImportance) {
        this.formattedImportance = formattedImportance;
    }
}
