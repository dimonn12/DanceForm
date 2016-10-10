package by.danceform.app.converter.competition;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.competition.CompetitionCategoryWithDetails;
import by.danceform.app.dto.competition.CompetitionCategoryWithDetailsDTO;

/**
 * Created by Dmitry_Shanko on 10/10/2016.
 */
@Component("competitionCategoryWithDetailsConverter")
public class CompetitionCategoryWithDetailsConverter
        extends AbstractConverter<CompetitionCategoryWithDetails, CompetitionCategoryWithDetailsDTO, Long> {

    @Inject
    private CompetitionCategoryConverter competitionCategoryConverter;

    @Override
    protected CompetitionCategoryWithDetailsDTO convertEntityToDto(CompetitionCategoryWithDetails entity,
            CompetitionCategoryWithDetailsDTO dto) {
        dto = (CompetitionCategoryWithDetailsDTO) competitionCategoryConverter
                .convertEntityToDto(entity.getCompetitionCategory(), dto);
        dto.setRegisteredCouplesCount(entity.getRegisteredCouplesCount());
        return dto;
    }

    @Override
    protected CompetitionCategoryWithDetails convertDtoToEntity(CompetitionCategoryWithDetailsDTO dto,
            CompetitionCategoryWithDetails entity) {
        return new CompetitionCategoryWithDetails(competitionCategoryConverter.convertToEntity(dto), null);
    }

    @Override
    protected CompetitionCategoryWithDetailsDTO getNewDTO() {
        return new CompetitionCategoryWithDetailsDTO();
    }

    @Override
    protected CompetitionCategoryWithDetails getNewEntity() {
        return new CompetitionCategoryWithDetails(null, null);
    }
}
