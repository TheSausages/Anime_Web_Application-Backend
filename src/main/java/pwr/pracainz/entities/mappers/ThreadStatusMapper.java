package pwr.pracainz.entities.mappers;

import com.google.gson.*;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import java.lang.reflect.Type;
import java.util.Locale;

public class ThreadStatusMapper implements JsonSerializer<ThreadStatus>, JsonDeserializer<ThreadStatus> {
    @Override
    public JsonElement serialize(ThreadStatus src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getFormattedStatus());
    }

    @Override
    public ThreadStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return ThreadStatus.valueOf(json.getAsString().toUpperCase(Locale.ROOT).replaceAll(" ", "_"));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("a");
        }
    }
}
