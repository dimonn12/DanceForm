package by.danceform.app.repository.config;

import by.danceform.app.domain.config.Trainer;
import by.danceform.app.repository.AbstractEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Trainer entity.
 */
public interface TrainerRepository extends AbstractEntityRepository<Trainer, Long> {

    @Query(
        "SELECT tr FROM Trainer tr WHERE tr.visible IN (true)")
    List<Trainer> findVisible();
}
