package by.danceform.app.repository.couple;

import by.danceform.app.domain.couple.RegisteredCouple;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the RegisteredCouple entity.
 */
public interface RegisteredCoupleRepository extends JpaRepository<RegisteredCouple,Long> {

}
