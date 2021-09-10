package pwr.pracainz.entities.databaseerntities.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "PostUserStatus")
@Entity
public class PostUserStatus {
    @EmbeddedId
    private PostUserStatusId postUserStatusId;

    @ColumnDefault("false")
    private boolean isLiked;

    @ColumnDefault("false")
    private boolean isDisliked;

    @ColumnDefault("false")
    private boolean isReported;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostUserStatus that = (PostUserStatus) o;
        return isLiked == that.isLiked && isDisliked == that.isDisliked && isReported == that.isReported && Objects.equals(postUserStatusId, that.postUserStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postUserStatusId, isLiked, isDisliked, isReported);
    }

    public static PostUserStatus getEmptyPostUserStatus(Post post, User user, boolean isLiked, boolean isDisliked, boolean isReported) {
        PostUserStatusId id = new PostUserStatusId(user, post);

        return new PostUserStatus(
                id, isLiked, isDisliked, isReported
        );
    }
}
