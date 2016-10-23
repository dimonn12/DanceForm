package by.danceform.app.converter.competition;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.converter.document.UploadedDocumentConverter;
import by.danceform.app.domain.competition.CompetitionWithDetails;
import by.danceform.app.dto.competition.CompetitionWithDetailsDTO;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimonn12 on 07.10.2016.
 */
@Component("сompetitionWithDetailsConverter")
public class CompetitionWithDetailsConverter
    extends AbstractConverter<CompetitionWithDetails, CompetitionWithDetailsDTO, Long> {

    @Inject
    private CompetitionConverter competitionConverter;

    @Inject
    private UploadedDocumentConverter uploadedDocumentConverter;

    @Override
    protected CompetitionWithDetailsDTO convertEntityToDto(CompetitionWithDetails entity,
                                                           CompetitionWithDetailsDTO dto) {
        dto = (CompetitionWithDetailsDTO)competitionConverter.convertEntityToDto(entity.getCompetition(), dto);
        dto.setUploadedDocumentDTO(uploadedDocumentConverter.convertToDto(entity.getUploadedDocument()));
        return dto;
    }

    @Override
    public CompetitionWithDetails convertDtoToEntity(CompetitionWithDetailsDTO dto, CompetitionWithDetails entity) {
        return new CompetitionWithDetails(competitionConverter.convertToEntity(dto), null);
    }

    @Override
    protected CompetitionWithDetailsDTO getNewDTO() {
        return new CompetitionWithDetailsDTO();
    }

    @Override
    protected CompetitionWithDetails getNewEntity() {
        return new CompetitionWithDetails(null, null);
    }
}
