package by.danceform.app.service.competition;

import by.danceform.app.converter.competition.CompetitionNotificationConverter;
import by.danceform.app.domain.competition.CompetitionNotification;
import by.danceform.app.dto.competition.CompetitionNotificationDTO;
import by.danceform.app.repository.competition.CompetitionNotificationRepository;
import by.danceform.app.validator.competition.CompetitionNotificationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CompetitionNotification.
 */
@Service
@Transactional
public class CompetitionNotificationService {

    private final Logger log = LoggerFactory.getLogger(CompetitionNotificationService.class);

    @Inject
    private CompetitionNotificationRepository competitionNotificationRepository;

    @Inject
    private CompetitionNotificationConverter competitionNotificationConverter;

    @Inject
    private CompetitionNotificationValidator competitionNotificationValidator;

    /**
     * Save a competitionNotification.
     *
     * @param competitionNotificationDTO the entity to save
     * @return the persisted entity
     */
    public CompetitionNotificationDTO save(CompetitionNotificationDTO competitionNotificationDTO) {
        log.debug("Request to save CompetitionNotification : {}", competitionNotificationDTO);
        competitionNotificationValidator.validate(competitionNotificationDTO);
        CompetitionNotification competitionNotification = competitionNotificationConverter.convertToEntity(
            competitionNotificationDTO);
        competitionNotification = competitionNotificationRepository.save(competitionNotification);
        CompetitionNotificationDTO result = competitionNotificationConverter.convertToDto(competitionNotification);
        return result;
    }

    /**
     * Get all the competitionNotifications.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CompetitionNotificationDTO> findAll() {
        log.debug("Request to get all CompetitionNotifications");
        List<CompetitionNotificationDTO> result = competitionNotificationRepository.findAll()
            .stream()
            .map(competitionNotificationConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Transactional(readOnly = true)
    public List<CompetitionNotificationDTO> findVisibleForSchedule() {
        log.debug("Request to get active CompetitionNotifications for visible competitions");
        List<CompetitionNotificationDTO> result = competitionNotificationRepository.findActiveForVisibleCompetitions()
            .stream()
            .map(competitionNotificationConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    @Transactional(readOnly = true)
    public List<CompetitionNotificationDTO> findActiveForCompetition(Long competitionId) {
        log.debug("Request to get active CompetitionNotifications for competition with id={}", competitionId);
        List<CompetitionNotificationDTO> result = competitionNotificationRepository.findActiveForByCompetitionId(
            competitionId)
            .stream()
            .map(competitionNotificationConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one competitionNotification by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CompetitionNotificationDTO findOne(Long id) {
        log.debug("Request to get CompetitionNotification : {}", id);
        CompetitionNotification competitionNotification = competitionNotificationRepository.findOne(id);
        CompetitionNotificationDTO competitionNotificationDTO = competitionNotificationConverter.convertToDto(
            competitionNotification);
        return competitionNotificationDTO;
    }

    /**
     * Delete the  competitionNotification by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompetitionNotification : {}", id);
        competitionNotificationRepository.delete(id);
    }
}
