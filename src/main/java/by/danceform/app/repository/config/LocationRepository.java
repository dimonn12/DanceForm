package by.danceform.app.repository.config;

import by.danceform.app.domain.config.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Location entity.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

}
