package pwr.pracainz.entities.databaseerntities.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "Threads")
@Entity
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int threadId;

    private String title;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("OPEN")
    private ThreadStatus status;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    private ForumCategory category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ThreadTags",
            joinColumns = {@JoinColumn(name = "TagId")},
            inverseJoinColumns = { @JoinColumn(name = "ThreadId") }
    )
    private Set<Tag> tags;

    @OneToMany(
            mappedBy = "threadUserStatusId.thread",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ThreadUserStatus> threadUserStatuses;

    @OneToMany(
            mappedBy = "thread",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Post> posts;
}
