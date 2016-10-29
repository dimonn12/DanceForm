package by.danceform.app.validator.couple;

import by.danceform.app.domain.competition.Competition;
import by.danceform.app.domain.system.SystemSetting;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.exception.ApplicationException;
import by.danceform.app.exception.ApplicationExceptionCodes;
import by.danceform.app.repository.competition.CompetitionRepository;
import by.danceform.app.service.competition.CompetitionCategoryService;
import by.danceform.app.service.system.SystemSettingNames;
import by.danceform.app.service.system.SystemSettingService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
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
    private SystemSettingService systemSettingService;

    @Inject
    private CompetitionCategoryService competitionCategoryService;

    @Override
    public void validate(RegisteredCoupleDTO dto) {
        Long competitionId = dto.getCompetitionId();
        Competition comp = competitionRepository.findOne(competitionId);
        if(null == comp || !comp.isVisible()) {
            throw new ApplicationException(ApplicationExceptionCodes.REGISTRATION_CLOSED);
        }
        SystemSetting daysBeforeRegistrationClosesSetting = systemSettingService.findByName(SystemSettingNames.DAYS_BEFORE_REGISTRATION_CLOSES);
        int daysBeforeRegistrationCloses = (null != daysBeforeRegistrationClosesSetting ?
            NumberUtils.toInt(daysBeforeRegistrationClosesSetting.getValue(), 1) :
            1);
        LocalDate registrationCloses = LocalDate.now().minusDays(daysBeforeRegistrationCloses);
        if(!registrationCloses.isBefore(comp.getStartDate())) {
            throw new ApplicationException(ApplicationExceptionCodes.REGISTRATION_CLOSED);
        }
        List<CompetitionCategoryDTO> availableCategories = competitionCategoryService.findAvailableByCompetitionId(dto,
            competitionId);
        List<Long> availableIds = availableCategories.stream()
            .map(availableCategory -> availableCategory.getId())
            .collect(Collectors.toList());
        for(Long categoryToRegister : dto.getCompetitionCategoryIds()) {
            if(!availableIds.contains(categoryToRegister)) {
                throw new ApplicationException(ApplicationExceptionCodes.REGISTRATION_CLOSED);
            }
        }
    }

}
