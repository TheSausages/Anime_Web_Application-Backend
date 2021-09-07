package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "ThreadUserStatus")
@Entity
public class ThreadUserStatus {
    @EmbeddedId
    private ThreadUserStatusId threadUserStatusId;

    @ColumnDefault("false")
    private boolean isWatching;

    @ColumnDefault("false")
    private boolean isBlocked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThreadUserStatus that = (ThreadUserStatus) o;
        return isWatching == that.isWatching && isBlocked == that.isBlocked && Objects.equals(threadUserStatusId, that.threadUserStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isWatching, isBlocked);
    }
}
