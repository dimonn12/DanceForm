package by.danceform.app.repository;

import by.danceform.app.domain.Competition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Competition entity.
 */
@SuppressWarnings("unused")
public interface CompetitionRepository extends JpaRepository<Competition,Long> {

}
