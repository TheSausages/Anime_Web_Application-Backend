package pwr.pracainz.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SimpleUserDTO {
    private String userId;
    private int nrOfPosts;
    private int watchTime;
    private long achievementPoints;
}
