package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}
