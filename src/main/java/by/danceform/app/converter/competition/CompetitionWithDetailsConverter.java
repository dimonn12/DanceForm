package by.danceform.app.converter.competition;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.competition.Competition;
import by.danceform.app.dto.competition.CompetitionWithDetailsDTO;

/**
 * Created by dimonn12 on 07.10.2016.
 */
@Component("сompetitionWithDetailsConverter")
public class CompetitionWithDetailsConverter extends AbstractConverter<Competition, CompetitionWithDetailsDTO, Long> {

    @Inject
    private CompetitionConverter competitionConverter;

    @Override
    protected CompetitionWithDetailsDTO convertEntityToDto(Competition entity, CompetitionWithDetailsDTO dto) {
        dto = (CompetitionWithDetailsDTO) competitionConverter.convertEntityToDto(entity, dto);
        return dto;
    }

    @Override
    public Competition convertDtoToEntity(CompetitionWithDetailsDTO dto, Competition entity) {
        return competitionConverter.convertToEntity(dto);
    }

    @Override
    protected CompetitionWithDetailsDTO getNewDTO() {
        return new CompetitionWithDetailsDTO();
    }

    @Override
    protected Competition getNewEntity() {
        return new Competition();
    }
}
