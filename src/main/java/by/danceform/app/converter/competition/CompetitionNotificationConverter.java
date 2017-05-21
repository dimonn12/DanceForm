package by.danceform.app.converter.competition;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.competition.CompetitionNotification;
import by.danceform.app.dto.competition.CompetitionNotificationDTO;
import by.danceform.app.repository.competition.CompetitionRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimonn12 on 22.05.2017.
 */
@Component("сompetitionNotificationConverter")
public class CompetitionNotificationConverter
    extends AbstractConverter<CompetitionNotification, CompetitionNotificationDTO, Long> {

    @Inject
    private CompetitionRepository competitionRepository;

    @Override
    protected CompetitionNotificationDTO convertEntityToDto(CompetitionNotification entity,
                                                            CompetitionNotificationDTO dto) {
        dto.setCompetitionId(entity.getCompetitionId());
        dto.setMessage(entity.getMessage());
        dto.setIsActive(entity.isActive());
        return dto;
    }

    @Override
    protected CompetitionNotification convertDtoToEntity(CompetitionNotificationDTO dto,
                                                         CompetitionNotification entity) {
        entity.setCompetitionId(dto.getCompetitionId());
        entity.setIsActive(dto.isActive());
        entity.setMessage(trimIfNull(dto.getMessage()));
        return entity;
    }

    @Override
    protected CompetitionNotificationDTO getNewDTO() {
        return new CompetitionNotificationDTO();
    }

    @Override
    protected CompetitionNotification getNewEntity() {
        return new CompetitionNotification();
    }
}
