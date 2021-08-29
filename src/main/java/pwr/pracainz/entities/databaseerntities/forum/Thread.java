package pwr.pracainz.entities.databaseerntities.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.forum.Enums.ThreadStatus;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
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

    @NotEmpty
    private String title;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("OPEN")
    private ThreadStatus status;

    @ColumnDefault("0")
    @Min(value = 0)
    private int NrOfPosts;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    private ForumCategory category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ThreadTags",
            joinColumns = {@JoinColumn(name = "TagId")},
            inverseJoinColumns = {@JoinColumn(name = "ThreadId")}
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thread thread = (Thread) o;
        return threadId == thread.threadId && Objects.equals(title, thread.title) && status == thread.status && Objects.equals(category, thread.category) && Objects.equals(tags, thread.tags) && Objects.equals(threadUserStatuses, thread.threadUserStatuses) && Objects.equals(posts, thread.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threadId, title, status, category, tags, threadUserStatuses, posts);
    }
}
