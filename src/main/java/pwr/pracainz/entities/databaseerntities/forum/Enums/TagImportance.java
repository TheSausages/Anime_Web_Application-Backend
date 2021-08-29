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
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    ADMIN("Admin");

    private final String formattedImportance;

    TagImportance(String formattedImportance) {
        this.formattedImportance = formattedImportance;
    }
}
