package pwr.pracainz.entities.databaseerntities.forum.Enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import pwr.pracainz.configuration.customMappers.tagImportanceMapper.TagImportanceDeserializer;
import pwr.pracainz.configuration.customMappers.tagImportanceMapper.TagImportanceSerializer;

@Getter
@JsonSerialize(using = TagImportanceSerializer.class)
@JsonDeserialize(using = TagImportanceDeserializer.class)
public enum TagImportance {
    LOW("Low", 1),
    MEDIUM("Medium", 2),
    HIGH("High", 3),
    ADMIN("Admin", 4);

    private final String formattedImportance;
    private final int comparableImportance;

    TagImportance(String formattedImportance, int comparableImportance) {
        this.formattedImportance = formattedImportance;
        this.comparableImportance = comparableImportance;
    }
}
