package by.danceform.app.repository.couple;

import by.danceform.app.domain.couple.RegisteredCouple;
import by.danceform.app.repository.SoftDeletedEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the RegisteredCouple entity.
 */
public interface RegisteredCoupleRepository extends SoftDeletedEntityRepository<RegisteredCouple, Long> {

    @Query("SELECT COUNT(couples.id) FROM RegisteredCouple couples WHERE couples.competitionCategory.id IN " +
           "(SELECT id FROM CompetitionCategory WHERE competitionId = :competitionId AND active IN (TRUE))" +
           "GROUP BY couples.partner1Name, couples.partner1Surname, couples.partner2Name, couples.partner2Surname")
    List<Integer> groupUniqueByCompetitionId(@Param("competitionId") Long competitionId);

    @Query(
        "select registeredCouple from RegisteredCouple registeredCouple where registeredCouple.competitionCategory.id=:categoryId")
    List<RegisteredCouple> findAllByCategoryId(@Param("categoryId") Long categoryId);

}
