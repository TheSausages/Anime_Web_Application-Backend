package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "ThreadUserStatus")
@Entity
public class ThreadUserStatus {
    @EmbeddedId
    private ThreadUserStatusId threadUserStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("threadId")
    private Thread thread;

    @ColumnDefault("false")
    private boolean isWatching;

    @ColumnDefault("false")
    private boolean isBlocked;
}

@Embeddable
class ThreadUserStatusId implements Serializable {
    private String userId;
    private int threadId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadUserStatusId that = (ThreadUserStatusId) o;
        return threadId == that.threadId && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, threadId);
    }
}
