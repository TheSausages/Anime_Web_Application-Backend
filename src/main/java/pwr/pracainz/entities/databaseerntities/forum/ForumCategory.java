package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ForumCategories")
public class ForumCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @NotEmpty
    private String categoryName;

    private String categoryDescription;

    @OneToMany(mappedBy = "category")
    private Set<Thread> threads;
}
