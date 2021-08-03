package pwr.pracainz.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompleteUserDTO {
    private SimpleUserDTO userInformation;
    private Set<AchievementDTO> achievements;
    private Set<AnimeUserInfoDTO> animeUserInfos;
    private Set<ThreadUserStatusDTO> threadUserStatuses;
    private Set<CompletePostDTO> posts;
}
