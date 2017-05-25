package by.danceform.app.repository.competition;

import by.danceform.app.domain.competition.CompetitionNotification;
import by.danceform.app.repository.SoftDeletedEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CompetitionNotification entity.
 */
@SuppressWarnings("unused")
public interface CompetitionNotificationRepository extends SoftDeletedEntityRepository<CompetitionNotification, Long> {

    @Query("SELECT competitionNotification FROM CompetitionNotification competitionNotification " +
           "JOIN competitionNotification.competition comp WHERE competitionNotification.isActive = true AND comp.visible = true")
    List<CompetitionNotification> findActiveForVisibleCompetitions();

    @Query("SELECT competitionNotification FROM CompetitionNotification competitionNotification " +
           "JOIN competitionNotification.competition comp WHERE competitionNotification.isActive = true AND comp.id = :competitionId")
    List<CompetitionNotification> findActiveForByCompetitionId(@Param("competitionId") Long competitionId);

}
