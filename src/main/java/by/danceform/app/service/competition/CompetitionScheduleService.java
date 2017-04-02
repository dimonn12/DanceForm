package by.danceform.app.service.competition;

import by.danceform.app.converter.competition.CompetitionCategoryWithDetailsConverter;
import by.danceform.app.converter.competition.CompetitionConverter;
import by.danceform.app.converter.competition.CompetitionWithDetailsConverter;
import by.danceform.app.domain.competition.Competition;
import by.danceform.app.domain.competition.CompetitionCategoryWithDetails;
import by.danceform.app.domain.competition.CompetitionWithDetails;
import by.danceform.app.domain.system.SystemSetting;
import by.danceform.app.dto.competition.CompetitionCategoryWithDetailsDTO;
import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.dto.competition.CompetitionWithDetailsDTO;
import by.danceform.app.repository.competition.CompetitionCategoryRepository;
import by.danceform.app.repository.competition.CompetitionRepository;
import by.danceform.app.repository.couple.RegisteredCoupleRepository;
import by.danceform.app.service.system.SystemSettingNames;
import by.danceform.app.service.system.SystemSettingService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dimonn12 on 08.10.2016.
 */
@Service
@Transactional
public class CompetitionScheduleService {

    private static final int HOURS_TIMEZONE = 3;

    private final Logger log = LoggerFactory.getLogger(CompetitionScheduleService.class);

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private CompetitionCategoryRepository competitionCategoryRepository;

    @Inject
    private RegisteredCoupleRepository registeredCoupleRepository;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private CompetitionConverter competitionConverter;

    @Inject
    private CompetitionWithDetailsConverter competitionWithDetailsConverter;

    @Inject
    private CompetitionCategoryWithDetailsConverter competitionCategoryWithDetailsConverter;

    @Transactional(readOnly = true)
    public List<CompetitionDTO> findForSchedule() {
        log.debug("Request to get all Competitions");
        List<Competition> result = competitionRepository.findVisible();
        List<CompetitionDTO> competitions = result.stream()
            .map(competition -> competitionConverter.convertToDto(competition))
            .collect(Collectors.toList());
        Collections.sort(competitions, (c1, c2) -> ObjectUtils.compare(c1.getStartDate(), c2.getStartDate()));

        competitions.stream().forEach(compDto -> {
            compDto.setRegistrationOpen(isRegistrationAvailable(compDto.getId()));
            compDto.setRegistrationClosed(isClosedCompetition(compDto.getRegistrationClosesTime(),
                compDto.getStartDate()));
            compDto.setResultsAvailable(isResultsAvailable(compDto));
        });
        return competitions;
    }

    @Transactional(readOnly = true)
    public CompetitionWithDetailsDTO findCompetitionWithDetails(Long id) {
        CompetitionWithDetails compWithDetails = competitionRepository.findOneWithDetails(id);
        if(!isCompetitionAvailable(compWithDetails.getCompetition())) {
            return null;
        }
        CompetitionWithDetailsDTO dto = competitionWithDetailsConverter.convertToDto(compWithDetails);
        dto.setAmountOfUniqueRegisteredPairs(registeredCoupleRepository.groupUniqueByCompetitionId(dto.getId()).size());
        List<CompetitionCategoryWithDetails> competitionCategoryWithDetails = competitionCategoryRepository.findWithDetailsByCompetitionId(
            id);
        Collections.sort(competitionCategoryWithDetails);
        dto.setRegistrationOpen(isRegistrationAvailable(compWithDetails.getCompetition()));
        dto.setRegistrationClosed(isClosedCompetition(compWithDetails.getCompetition()));
        dto.setCompetitionCategoryDTOs(competitionCategoryWithDetailsConverter.convertToDtos(
            competitionCategoryWithDetails));
        return dto;
    }

    @Transactional(readOnly = true)
    public CompetitionCategoryWithDetailsDTO findCategoryWithDetails(Long id) {
        return competitionCategoryWithDetailsConverter.convertToDto(competitionCategoryRepository.findWithDetailsByCategoryId(
            id));
    }

    public boolean isCompetitionAvailable(Competition comp) {
        if(null != comp && comp.isVisible()) {
            return true;
        }
        return false;
    }

    public boolean isRegistrationAvailable(Competition competition) {
        return isRegistrationAvailable(competition.getId());
    }

    public boolean isRegistrationAvailable(Long competitionId) {
        return competitionCategoryRepository.isRegistrationAvailable(competitionId);
    }

    public boolean isClosedCompetition(Competition competition) {
        return isClosedCompetition(competition.getRegistrationClosesTime(), competition.getStartDate());
    }

    private boolean isClosedCompetition(LocalDateTime registrationClosesTime, LocalDate startDate) {
        return isClosedCompetition(getRegistrationClosesTime(registrationClosesTime, startDate));
    }

    public LocalDateTime getRegistrationClosesTime(LocalDateTime registrationClosesTime, LocalDate startDate) {
        if(null == registrationClosesTime) {
            SystemSetting daysBeforeRegistrationClosesSetting = systemSettingService.findByName(SystemSettingNames.DAYS_BEFORE_REGISTRATION_CLOSES);
            int daysBeforeRegistrationCloses = (null != daysBeforeRegistrationClosesSetting ?
                NumberUtils.toInt(daysBeforeRegistrationClosesSetting.getValue(), 1) :
                1);
            registrationClosesTime = startDate.minusDays(daysBeforeRegistrationCloses).atTime(LocalTime.NOON);
        }
        return registrationClosesTime;
    }

    private boolean isClosedCompetition(LocalDateTime registrationClosesTime) {
        return !LocalDateTime.now(ZoneId.of("UTC")).isBefore(registrationClosesTime);
    }

    private boolean isResultsAvailable(CompetitionDTO competition) {
        return false;
    }

}
