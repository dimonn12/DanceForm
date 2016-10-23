package by.danceform.app.repository.couple;

import by.danceform.app.domain.couple.RegisteredCouple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the RegisteredCouple entity.
 */
public interface RegisteredCoupleRepository extends JpaRepository<RegisteredCouple, Long> {

    @Query("SELECT COUNT(couples.id) FROM RegisteredCouple couples WHERE couples.competitionCategory.id IN " +
           "(SELECT id FROM CompetitionCategory WHERE competitionId = :competitionId AND active IN (TRUE))" +
           "GROUP BY couples.partner1Name, couples.partner1Surname, couples.partner2Name, couples.partner2Surname")
    List<Integer> groupUniqueByCompetitionId(@Param("competitionId") Long competitionId);

    @Query("SELECT COUNT(*) FROM RegisteredCouple rc WHERE competitionCategory.id = :competitionCategoryId")
    int countByCompetitionCategory(@Param("competitionCategoryId") Long competitionCategoryId);

    @Query(
        "SELECT COUNT(*) FROM RegisteredCouple rc WHERE competitionId = :competitionId AND competitionCategory.active IN (TRUE, :onlyActive)")
    int countByCompetition(@Param("competitionId") Long competitionId, @Param("onlyActive") Boolean onlyActive);

    @Query(
        "select registeredCouple from RegisteredCouple registeredCouple where registeredCouple.competitionCategory.id=:categoryId")
    List<RegisteredCouple> findAllByCategoryId(@Param("categoryId") Long categoryId);

}
