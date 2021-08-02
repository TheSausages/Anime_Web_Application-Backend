package pwr.pracainz.DTO.animeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GradeDTO {
    private int gradeId;
    private int scale;
    private String gradeName;
}
