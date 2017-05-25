package by.danceform.app.validator.competition;

import by.danceform.app.domain.competition.Competition;
import by.danceform.app.dto.competition.CompetitionNotificationDTO;
import by.danceform.app.exception.ApplicationException;
import by.danceform.app.exception.ApplicationExceptionCodes;
import by.danceform.app.repository.competition.CompetitionRepository;
import by.danceform.app.validator.AbstractValidator;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimonn12 on 25.05.2017.
 */
@Component
public class CompetitionNotificationValidator extends AbstractValidator<Long, CompetitionNotificationDTO> {

    @Inject
    private CompetitionRepository competitionRepository;

    @Override
    public void validate(CompetitionNotificationDTO dto) {
        if(null == dto.getCompetition() || null == dto.getCompetition().getId()) {
            throw new ApplicationException(ApplicationExceptionCodes.COMPETITION_ID_IS_REQUIRED);
        }
        Long competitionId = dto.getCompetition().getId();
        Competition comp = competitionRepository.findOne(competitionId);
        if(null == comp) {
            throw new ApplicationException(ApplicationExceptionCodes.COMPETITION_ID_IS_NOT_VALID);
        }
    }
}
