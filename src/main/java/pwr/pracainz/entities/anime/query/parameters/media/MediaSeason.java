package pwr.pracainz.entities.anime.query.parameters.media;

import java.time.LocalDateTime;

public enum MediaSeason {
    WINTER,
    SPRING,
    SUMMER,
    FALL;

    public static MediaSeason getCurrentSeason() {
        switch (LocalDateTime.now().getMonth()) {
            case APRIL, MAY, JUNE -> {
                return SPRING;
            }

            case JULY, AUGUST, SEPTEMBER -> {
                return SUMMER;
            }

            case OCTOBER, NOVEMBER, DECEMBER -> {
                return FALL;
            }

            //for January, February, March and default
            default -> {
                return WINTER;
            }
        }
    }
}
