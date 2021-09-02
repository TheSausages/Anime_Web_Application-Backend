package pwr.pracainz.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDTO<T> {
    List<T> content;
    int numberOfElements;
    int pageSize;
    int pageNumber;
    long totalPages;
    boolean last;
    boolean empty;
}
