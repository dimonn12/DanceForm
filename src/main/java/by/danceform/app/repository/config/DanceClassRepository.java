package by.danceform.app.repository.config;

import by.danceform.app.domain.config.DanceClass;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the DanceClass entity.
 */
public interface DanceClassRepository extends JpaRepository<DanceClass, Long> {

}
