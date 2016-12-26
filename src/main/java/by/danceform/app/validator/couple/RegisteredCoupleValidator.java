package by.danceform.app.validator.couple;

import by.danceform.app.domain.competition.Competition;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.exception.ApplicationException;
import by.danceform.app.exception.ApplicationExceptionCodes;
import by.danceform.app.repository.competition.CompetitionRepository;
import by.danceform.app.service.competition.CompetitionCategoryService;
import by.danceform.app.service.competition.CompetitionScheduleService;
import by.danceform.app.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dimonn12 on 29.10.2016.
 */
@Component
public class RegisteredCoupleValidator extends AbstractValidator<Long, RegisteredCoupleDTO> {

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private CompetitionCategoryService competitionCategoryService;

    @Inject
    private CompetitionScheduleService competitionScheduleService;

    @Override
    public void validate(RegisteredCoupleDTO dto) {
        validateRequiredField(dto);
        Long competitionId = dto.getCompetitionId();
        Competition comp = competitionRepository.findOne(competitionId);
        if(!competitionScheduleService.isCompetitionAvailable(comp) ||
           competitionScheduleService.isClosedCompetition(comp) ||
           !competitionScheduleService.isRegistrationAvailable(comp)) {
            throw new ApplicationException(ApplicationExceptionCodes.REGISTRATION_CLOSED);
        }
        List<CompetitionCategoryDTO> availableCategories = competitionCategoryService.findAvailableByCompetitionId(dto,
            competitionId,
            dto.isSoloCouple());
        List<Long> availableIds = availableCategories.stream()
            .map(availableCategory -> availableCategory.getId())
            .collect(Collectors.toList());
        for(Long categoryToRegister : dto.getCompetitionCategoryIds()) {
            if(!availableIds.contains(categoryToRegister)) {
                throw new ApplicationException(ApplicationExceptionCodes.REGISTRATION_CLOSED);
            }
        }
    }

    private void validateRequiredField(RegisteredCoupleDTO dto) {
        if(!dto.isSoloCouple()) {
            if(StringUtils.isBlank(dto.getPartner2Name()) ||
               StringUtils.isBlank(dto.getPartner2Surname()) ||
               null == dto.getPartner2DateOfBirth() ||
               null == dto.getPartner2DanceClassLA() ||
               null == dto.getPartner2DanceClassLA().getId() ||
               null == dto.getPartner2DanceClassST() ||
               null == dto.getPartner2DanceClassST().getId()) {
                throw new ApplicationException(ApplicationExceptionCodes.FIELD_NOT_VALID);
            }
        }
    }

}
