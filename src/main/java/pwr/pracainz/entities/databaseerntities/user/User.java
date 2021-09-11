package pwr.pracainz.entities.databaseerntities.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.forum.PostUserStatus;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.forum.ThreadUserStatus;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Users")
@Entity
public class User {
    @Id
    @NotEmpty
    private String userId;

    @NotBlank
    @ColumnDefault("Default Username")
    private String username;

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

    @OneToMany(
            mappedBy = "postUserStatusId.user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PostUserStatus> postUserStatuses;

    @OneToMany(mappedBy = "creator")
    private Set<Thread> threads;

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
        return Objects.hash(userId, nrOfPosts, watchTime, achievementPoints, posts);
    }
}
