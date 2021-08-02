package pwr.pracainz.entities.mappers;

import com.google.gson.*;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserStatus;

import java.lang.reflect.Type;
import java.util.Locale;

public class AnimeUserStatusMapper implements JsonSerializer<AnimeUserStatus>, JsonDeserializer<AnimeUserStatus> {
    @Override
    public JsonElement serialize(AnimeUserStatus src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getFormattedStatus());
    }

    @Override
    public AnimeUserStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return AnimeUserStatus.valueOf(json.getAsString().toUpperCase(Locale.ROOT).replaceAll(" ", "_"));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("a");
        }
    }
}
