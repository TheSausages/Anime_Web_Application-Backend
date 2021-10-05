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
    private int tagId;

    @NotEmpty
    private String tagName;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("LOW")
    private TagImportance tagImportance;

    private String tagColor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagId == tag.tagId && Objects.equals(tagName, tag.tagName) && tagImportance == tag.tagImportance && Objects.equals(tagColor, tag.tagColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, tagName, tagImportance, tagColor);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tagImportance=" + tagImportance +
                ", tagColor='" + tagColor + '\'' +
                '}';
    }
}
