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
public class AchievementDTO {
    int achievementId;
    String name;
    String description;
    String iconPath;
    int achievementPoints;
    int numberOfUsersThatPosses;
}
