package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import pwr.pracainz.entities.databaseerntities.user.User;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "Posts")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Length(max = 45)
    private String title;

    @Length(max = 600)
    private String postText;

    @ColumnDefault("false")
    private boolean isBlocked;

    @ColumnDefault("0")
    @Positive
    private int nrOfPlus;

    @ColumnDefault("0")
    @Positive
    private int nrOfMinus;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "threadId", nullable = false)
    private Thread thread;
}
