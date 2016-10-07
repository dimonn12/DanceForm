package by.danceform.app.repository;

import by.danceform.app.domain.AgeCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AgeCategory entity.
 */
@SuppressWarnings("unused")
public interface AgeCategoryRepository extends JpaRepository<AgeCategory,Long> {

}
