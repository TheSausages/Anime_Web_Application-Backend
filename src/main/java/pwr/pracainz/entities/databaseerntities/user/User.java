package pwr.pracainz.entities.databaseerntities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.forum.Post;
import pwr.pracainz.entities.databaseerntities.forum.ThreadUserStatus;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ThreadUserStatus> threadUserStatuses;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;
}
