package pwr.pracainz.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AchievementDTO {
    private int achievementId;
    private String name;
    private String description;
    private String iconPath;
    private int achievementPoints;
    private int numberOfUsersThatPosses;
}
