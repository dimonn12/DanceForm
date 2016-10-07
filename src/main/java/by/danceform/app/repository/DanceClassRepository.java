package by.danceform.app.repository;

import by.danceform.app.domain.DanceClass;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DanceClass entity.
 */
@SuppressWarnings("unused")
public interface DanceClassRepository extends JpaRepository<DanceClass,Long> {

}
