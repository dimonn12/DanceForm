package by.danceform.app.repository.competition;

import by.danceform.app.domain.competition.CompetitionNotification;
import by.danceform.app.repository.SoftDeletedEntityRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the CompetitionNotification entity.
 */
@SuppressWarnings("unused")
public interface CompetitionNotificationRepository extends SoftDeletedEntityRepository<CompetitionNotification, Long> {

    @Query("SELECT competitionNotification FROM CompetitionNotification competitionNotification"
        + " JOIN competitionNotification.competition comp WHERE competitionNotification.isActive = true"
        + " AND competitionNotification.showOnMainPage = true AND comp.visible = true"
        + " ORDER BY competitionNotification.priority asc")
    List<CompetitionNotification> findActiveForVisibleCompetitions();

    @Query("SELECT competitionNotification FROM CompetitionNotification competitionNotification"
        + " JOIN competitionNotification.competition comp"
        + " WHERE competitionNotification.isActive = true AND comp.id = :competitionId"
        + " ORDER BY competitionNotification.priority asc")
    List<CompetitionNotification> findActiveForByCompetitionId(@Param("competitionId") Long competitionId);

}
