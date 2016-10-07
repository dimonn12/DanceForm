package by.danceform.app.repository.competition;

import by.danceform.app.domain.competition.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Competition entity.
 */
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

}
