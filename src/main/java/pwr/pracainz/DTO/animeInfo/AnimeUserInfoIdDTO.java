package pwr.pracainz.DTO.animeInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.user.SimpleUserDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimeUserInfoIdDTO {

    @NotNull(message = "User id Anime User Information cannot be null")
    @Valid
    private SimpleUserDTO user;

    @Positive(message = "Anime Id must be positive!")
    private int animeId;
}
