package pwr.pracainz.entities.databaseerntities.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pwr.pracainz.entities.databaseerntities.forum.Enums.TagImportance;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return TagId == tag.TagId && Objects.equals(tagName, tag.tagName) && tagImportance == tag.tagImportance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(TagId, tagName, tagImportance);
    }
}
