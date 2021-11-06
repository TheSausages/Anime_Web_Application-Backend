package pwr.pracainz.DTO.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.forum.Post.CompletePostDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;

import javax.validation.Valid;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompleteUserDTO extends SimpleUserDTO {
	@Valid
	private Set<AchievementDTO> achievements;

	@Valid
	private Set<AnimeUserInfoDTO> animeUserInfos;

	@Valid
	private Set<ThreadUserStatusDTO> threadUserStatuses;

	@Valid
	private Set<SimpleThreadDTO> threads;

	@Valid
	private Set<CompletePostDTO> posts;

	public CompleteUserDTO(String userId, String username, int nrOfPosts, int watchTime, long achievementPoints, Set<AchievementDTO> achievements,
	                       Set<AnimeUserInfoDTO> animeUserInfos, Set<ThreadUserStatusDTO> threadUserStatuses, Set<SimpleThreadDTO> threads, Set<CompletePostDTO> posts) {
		super(userId, username, nrOfPosts, watchTime, achievementPoints);

		this.achievements = achievements;
		this.animeUserInfos = animeUserInfos;
		this.threadUserStatuses = threadUserStatuses;
		this.posts = posts;
	}

}
