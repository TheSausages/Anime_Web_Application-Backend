package pwr.pracainz.entities.mappers;

import com.google.gson.*;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

import java.lang.reflect.Type;
import java.util.Locale;

public class TagImportanceMapper implements JsonSerializer<TagImportance>, JsonDeserializer<TagImportance> {
    @Override
    public JsonElement serialize(TagImportance src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getFormattedImportance());
    }

    @Override
    public TagImportance deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return TagImportance.valueOf(json.getAsString().toUpperCase(Locale.ROOT).replaceAll(" ", "_"));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("a");
        }
    }
}