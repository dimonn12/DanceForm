package by.danceform.app.repository.config;

import by.danceform.app.domain.config.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Organization entity.
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(
        "SELECT org FROM Organization org WHERE org.visible IN (true)")
    List<Organization> findVisible();

}
