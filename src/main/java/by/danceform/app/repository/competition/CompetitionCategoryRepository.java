package by.danceform.app.repository.competition;

import by.danceform.app.domain.competition.CompetitionCategory;
import by.danceform.app.domain.competition.CompetitionCategoryWithDetails;
import by.danceform.app.repository.AbstractEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CompetitionCategory entity.
 */
@SuppressWarnings("unused")
public interface CompetitionCategoryRepository extends AbstractEntityRepository<CompetitionCategory, Long> {

    @Query(
        "select distinct competitionCategory from CompetitionCategory competitionCategory left join fetch competitionCategory.ageCategories")
    List<CompetitionCategory> findAllWithEagerRelationships();

    @Query(
        "select distinct competitionCategory from CompetitionCategory competitionCategory left join fetch competitionCategory.ageCategories where competitionCategory.id =:id")
    CompetitionCategory findOneWithEagerRelationships(@Param("id") Long id);

    @Query(
        "select distinct competitionCategory from CompetitionCategory competitionCategory left join fetch competitionCategory.ageCategories where competitionCategory.competitionId=:competitionId")
    List<CompetitionCategory> findAllByCompetitionId(@Param("competitionId") Long competitionId);

    @Query(
        "select distinct competitionCategory from CompetitionCategory competitionCategory left join fetch competitionCategory.ageCategories where competitionCategory.competitionId=:competitionId AND competitionCategory.active = true")
    List<CompetitionCategory> findAvailableByCompetitionId(@Param("competitionId") Long competitionId);

    @Query(
        "SELECT NEW CompetitionCategoryWithDetails(category, (SELECT COUNT(couples.id) FROM RegisteredCouple couples WHERE couples.competitionCategory.id = category.id))" +
        " FROM CompetitionCategory category WHERE category.competitionId = :competitionId AND category.active = true")
    List<CompetitionCategoryWithDetails> findWithDetailsByCompetitionId(@Param("competitionId") Long competitionId);

    @Query(
        "SELECT COUNT(category.id) > 0 FROM CompetitionCategory category WHERE category.competitionId = :competitionId AND category.active = true")
    boolean isRegistrationAvailable(@Param("competitionId") Long competitionId);

    @Query(
        "SELECT NEW CompetitionCategoryWithDetails(category, (SELECT COUNT(couples.id) FROM RegisteredCouple couples WHERE couples.competitionCategory.id = category.id))" +
        " FROM CompetitionCategory category WHERE category.id = :categoryId")
    CompetitionCategoryWithDetails findWithDetailsByCategoryId(@Param("categoryId") Long categoryId);

}
