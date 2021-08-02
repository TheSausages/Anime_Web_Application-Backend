package pwr.pracainz.entities.anime.tagFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Setter
@Getter
@NoArgsConstructor
public class Tag {
    private int id;
    private String name;
    private String description;
    private String category;
    private boolean isGeneralSpoiler;
    private boolean isMediaSpoiler;
    private boolean isAdult;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return new EqualsBuilder().append(id, tag.id).append(isGeneralSpoiler, tag.isGeneralSpoiler).append(isMediaSpoiler, tag.isMediaSpoiler).append(isAdult, tag.isAdult).append(name, tag.name).append(description, tag.description).append(category, tag.category).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(description).append(category).append(isGeneralSpoiler).append(isMediaSpoiler).append(isAdult).toHashCode();
    }

    @Override
    public String toString() {
        return name.replaceAll("[ -]", "_").replaceAll("['\"]","") + "( " + id + ", \"" + name + "\", \"" + description + "\", \"" + category + "\", " + isGeneralSpoiler + ", " + isMediaSpoiler + ", " + isGeneralSpoiler + "), \n";
    }
}
