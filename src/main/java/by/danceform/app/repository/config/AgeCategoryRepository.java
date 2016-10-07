package by.danceform.app.repository.config;

import by.danceform.app.domain.config.AgeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AgeCategory entity.
 */
public interface AgeCategoryRepository extends JpaRepository<AgeCategory, Long> {

}
