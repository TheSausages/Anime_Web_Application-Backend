package pwr.PracaInz.Entities.Anime.Query.Parameters;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public class FieldString {
    private final String field;

    public FieldString(String field) {
        this.field = field;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(field.split("[ (]")[0]).toHashCode();
    }

    @Override
    public String toString() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FieldString that = (FieldString) o;

        return new EqualsBuilder().append(field.split("[ (]")[0], that.getField().split("[ (]")[0]).isEquals();
    }
}
