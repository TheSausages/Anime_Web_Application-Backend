package pwr.pracainz.DTO.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.user.SimpleUserDTO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnimeUserInfoIdDTO {
    private SimpleUserDTO user;
    private int animeId;
}
