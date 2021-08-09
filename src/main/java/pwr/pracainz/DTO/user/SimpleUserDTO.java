package pwr.pracainz.DTO.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleUserDTO {
    private String userId;
    private int nrOfPosts;
    private int watchTime;
    private long achievementPoints;
}
