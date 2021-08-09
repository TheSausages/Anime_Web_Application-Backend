package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.user.SimpleUserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimeUserInfoIdDTO {
    private SimpleUserDTO user;
    private int animeId;
}
