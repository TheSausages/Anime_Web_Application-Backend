package pwr.pracainz.entities.databaseerntities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "Achievements")
@Entity
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int achievementId;

    @NotEmpty
    private String name;

    @NotNull
    private String description;

    @NotEmpty
    private String iconPath;

    @Positive
    private int achievementPoints;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "UserAchievements",
            joinColumns = { @JoinColumn(name = "AchievementID") },
            inverseJoinColumns = { @JoinColumn(name = "UserID") }
    )
    private Set<User> users;
}
