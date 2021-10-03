package pwr.pracainz.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDTO<T> {
    @NotNull(message = "Content of a page cannot be null")
    List<T> content;

    @Min(value = 0, message = "Number of elements cannot be negative")
    int numberOfElements;

    @Min(value = 0, message = "Page size cannot be negative")
    int pageSize;

    @Min(value = 0, message = "Page number cannot be negative")
    int pageNumber;

    @Min(value = 0, message = "Total pages cannot be negative")
    long totalPages;

    boolean last;

    boolean empty;
}
