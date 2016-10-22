package by.danceform.app.repository.config;

import by.danceform.app.domain.config.DanceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the DanceCategory entity.
 */
public interface DanceCategoryRepository extends JpaRepository<DanceCategory, Long> {

}
