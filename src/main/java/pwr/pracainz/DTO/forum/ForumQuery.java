package pwr.pracainz.DTO.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pwr.pracainz.entities.databaseerntities.forum.ForumCategory;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ForumQuery {
    private ForumCategory category;
}
