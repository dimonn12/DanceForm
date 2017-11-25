package by.danceform.app.converter.competition;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.converter.NamedEntityConverter;
import by.danceform.app.domain.competition.Competition;
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

    @Inject
    private NamedEntityConverter<Competition> competitionNamedEntityConverter;

    @Override
    protected CompetitionNotificationDTO convertEntityToDto(CompetitionNotification entity,
                                                            CompetitionNotificationDTO dto) {
        dto.setCompetition(competitionNamedEntityConverter.convertToDto(entity.getCompetition()));
        dto.setMessage(entity.getMessage());
        dto.setIsActive(entity.isActive());
        dto.setIsShowOnMainPage(entity.isShowOnMainPage());
        dto.setIsBold(entity.isBold());
        return dto;
    }

    @Override
    protected CompetitionNotification convertDtoToEntity(CompetitionNotificationDTO dto,
                                                         CompetitionNotification entity) {
        entity.setCompetition(competitionRepository.findOne(dto.getCompetition().getId()));
        entity.setIsActive(dto.isActive());
        entity.setMessage(trimIfNull(dto.getMessage()));
        entity.setIsShowOnMainPage(entity.isShowOnMainPage());
        entity.setIsBold(dto.isBold());
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
