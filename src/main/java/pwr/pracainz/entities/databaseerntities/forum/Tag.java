package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TagId;

    @NotEmpty
    private String tagName;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("LOW")
    private TagImportance tagImportance;
}
