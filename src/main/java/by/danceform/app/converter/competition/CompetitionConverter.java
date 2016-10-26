package by.danceform.app.converter.competition;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.competition.Competition;
import by.danceform.app.dto.competition.CompetitionDTO;
import org.springframework.stereotype.Component;

/**
 * Created by dimonn12 on 07.10.2016.
 */
@Component("competitionConverter")
public class CompetitionConverter extends AbstractConverter<Competition, CompetitionDTO, Long> {

    @Override
    protected CompetitionDTO convertEntityToDto(Competition entity, CompetitionDTO dto) {
        dto.setName(entity.getName());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setLocation(entity.getLocation());
        dto.setOrganizer(entity.getOrganizer());
        dto.setVisible(entity.isVisible());
        dto.setDetailsDocumentId(entity.getDetailsDocumentId());
        dto.setBannerImageId(entity.getBannerImageId());
        return dto;
    }

    @Override
    public Competition convertDtoToEntity(CompetitionDTO dto, Competition entity) {
        entity.setName(dto.getName());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setVisible(dto.isVisible());
        entity.setOrganizer(dto.getOrganizer());
        entity.setLocation(dto.getLocation());
        return entity;
    }

    @Override
    protected CompetitionDTO getNewDTO() {
        return new CompetitionDTO();
    }

    @Override
    protected Competition getNewEntity() {
        return new Competition();
    }
}
