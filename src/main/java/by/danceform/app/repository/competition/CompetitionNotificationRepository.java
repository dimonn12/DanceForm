package by.danceform.app.repository.competition;

import by.danceform.app.domain.competition.CompetitionNotification;
import by.danceform.app.repository.SoftDeletedEntityRepository;

/**
 * Spring Data JPA repository for the CompetitionNotification entity.
 */
@SuppressWarnings("unused")
public interface CompetitionNotificationRepository extends SoftDeletedEntityRepository<CompetitionNotification, Long> {

}
