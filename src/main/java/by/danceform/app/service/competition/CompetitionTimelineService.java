package by.danceform.app.service.competition;

import by.danceform.app.converter.competition.CompetitionCategoryConverter;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dimonn12 on 08.10.2016.
 */
@Service
@Transactional
public class CompetitionTimelineService {

    private final Logger log = LoggerFactory.getLogger(CompetitionTimelineService.class);

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

    @Inject
    private CompetitionCategoryConverter competitionCategoryConverter;

    @Transactional(readOnly = true)
    public List<CompetitionDTO> findForTimeline() {
        log.debug("Request to get all Competitions");
        List<Competition> result = competitionRepository.findVisible();
        List<CompetitionDTO> competitions = result.stream()
            .map(competition -> competitionConverter.convertToDto(competition))
            .collect(Collectors.toList());
        Collections.sort(competitions, (c1, c2) -> ObjectUtils.compare(c1.getDate(), c2.getDate()));
        SystemSetting daysBeforeRegistrationClosesSetting = systemSettingService.findByName(SystemSettingNames.DAYS_BEFORE_REGISTRATION_CLOSES);
        int daysBeforeRegistrationCloses = (null != daysBeforeRegistrationClosesSetting ?
            NumberUtils.toInt(daysBeforeRegistrationClosesSetting.getValue(), 1) :
            1);
        LocalDate registrationCloses = LocalDate.now().minusDays(daysBeforeRegistrationCloses);
        for(CompetitionDTO compDto : competitions) {
            if(!registrationCloses.isBefore(compDto.getDate())) {
                compDto.setRegistrationClosed(true);
            }
        }
        return competitions;
    }

    @Transactional(readOnly = true)
    public CompetitionWithDetailsDTO findCompetitionWithDetails(Long id) {
        CompetitionWithDetails compWithDetails = competitionRepository.findOneWithDetails(id);
        Competition comp = compWithDetails.getCompetition();
        if(null != comp) {
            if(!comp.isVisible()) {
                return null;
            }
        }
        CompetitionWithDetailsDTO dto = competitionWithDetailsConverter.convertToDto(compWithDetails);
        dto.setAmountOfUniqueRegisteredPairs(registeredCoupleRepository.groupUniqueByCompetitionId(dto.getId()).size());
        List<CompetitionCategoryWithDetails> competitionCategoryWithDetailses = competitionCategoryRepository.findWithDetailsByCompetitionId(
            id);
        dto.setCompetitionCategoryDTOs(competitionCategoryWithDetailsConverter.convertToDtos(
            competitionCategoryWithDetailses));
        return dto;
    }

    @Transactional(readOnly = true)
    public CompetitionCategoryWithDetailsDTO findCategoryWithDetails(Long id) {
        return competitionCategoryWithDetailsConverter.convertToDto(competitionCategoryRepository.findWithDetailsByCategoryId(
            id));
    }

}
