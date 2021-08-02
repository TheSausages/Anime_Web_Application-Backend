package pwr.pracainz.repositories.animeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pwr.pracainz.entities.databaseerntities.animeInfo.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
