package pwr.pracainz.DTO.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompleteUserDTO {
    private SimpleUserDTO userInformation;
    private Set<AchievementDTO> achievements;
    private Set<AnimeUserInfoDTO> animeUserInfos;
    private Set<ThreadUserStatusDTO> threadUserStatuses;
    private Set<CompletePostDTO> posts;
}
