package by.danceform.app.repository.couple;

import by.danceform.app.domain.competition.CompetitionCategory;
import by.danceform.app.domain.couple.RegisteredCouple;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the RegisteredCouple entity.
 */
public interface RegisteredCoupleRepository extends JpaRepository<RegisteredCouple, Long> {

    @Query("SELECT COUNT(*) FROM RegisteredCouple rc WHERE competitionCategory.id = :competitionCategoryId")
    int countByCompetitionCategory(@Param("competitionCategoryId") Long competitionCategoryId);

    @Query(
        "SELECT COUNT(*) FROM RegisteredCouple rc WHERE competitionId = :competitionId AND competitionCategory.active IN (TRUE, :onlyActive)")
    int countByCompetition(@Param("competitionId") Long competitionId, @Param("onlyActive") Boolean onlyActive);

    @Query(
        "select registeredCouple from RegisteredCouple registeredCouple where registeredCouple.competitionCategory.id=:categoryId")
    List<RegisteredCouple> findAllByCategoryId(@Param("categoryId") Long categoryId);

}
