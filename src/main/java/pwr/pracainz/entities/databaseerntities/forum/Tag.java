package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TagId;

    private String tagName;

    private TagImportance tagImportance;
}
