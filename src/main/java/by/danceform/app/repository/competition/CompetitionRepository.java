package by.danceform.app.repository.competition;

import by.danceform.app.domain.competition.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Competition entity.
 */
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query("SELECT competition FROM Competition competition WHERE competition.visible = true")
    List<Competition> findVisible();
}
