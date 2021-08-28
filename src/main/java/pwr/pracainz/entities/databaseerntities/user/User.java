package pwr.pracainz.entities.databaseerntities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.forum.ThreadUserStatus;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "Users")
@Entity
public class User {
    @Id
    @NotEmpty
    private String userId;

    @Min(value = 0)
    @ColumnDefault("0")
    private int nrOfPosts;

    @Min(value = 0)
    @ColumnDefault("0")
    private int watchTime;

    @Min(value = 0)
    @ColumnDefault("0")
    private long achievementPoints;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Achievement> achievements;

    @OneToMany(
            mappedBy = "animeUserInfoId.user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AnimeUserInfo> animeUserInfo;

    @OneToMany(
            mappedBy = "threadUserStatusId.user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ThreadUserStatus> threadUserStatuses;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;

    public User(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return nrOfPosts == user.nrOfPosts && watchTime == user.watchTime && achievementPoints == user.achievementPoints && Objects.equals(userId, user.userId) && Objects.equals(achievements, user.achievements) && Objects.equals(animeUserInfo, user.animeUserInfo) && Objects.equals(threadUserStatuses, user.threadUserStatuses) && Objects.equals(posts, user.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, nrOfPosts, watchTime, achievementPoints, achievements, animeUserInfo, threadUserStatuses, posts);
    }
}
