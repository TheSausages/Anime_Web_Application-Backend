package pwr.pracainz.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.forum.PostDTO;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private String userId;
    private int nrOfPosts;
    private int watchTime;
    private long achievementPoints;
    private Set<AchievementDTO> achievements;
    private Set<PostDTO> posts;
}
