package by.danceform.app.service.competition;

import by.danceform.app.converter.NamedEntityConverter;
import by.danceform.app.converter.competition.CompetitionCategoryConverter;
import by.danceform.app.domain.competition.Competition;
import by.danceform.app.domain.competition.CompetitionCategory;
import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.domain.config.DanceClass;
import by.danceform.app.domain.config.enums.DanceCategoryEnum;
import by.danceform.app.dto.NamedReferenceDTO;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.repository.competition.CompetitionCategoryRepository;
import by.danceform.app.repository.competition.CompetitionRepository;
import by.danceform.app.repository.config.DanceClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CompetitionCategory.
 */
@Service
@Transactional
public class CompetitionCategoryService {

    private final Logger log = LoggerFactory.getLogger(CompetitionCategoryService.class);

    @Inject
    private CompetitionCategoryRepository competitionCategoryRepository;

    @Inject
    private CompetitionCategoryConverter competitionCategoryConverter;

    @Inject
    private DanceClassRepository danceClassRepository;

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private NamedEntityConverter<DanceClass> danceClassNamedEntityConverter;

    /**
     * Save a competitionCategory.
     *
     * @param competitionCategoryDTO the entity to save
     * @return the persisted entity
     */
    public CompetitionCategoryDTO save(CompetitionCategoryDTO competitionCategoryDTO) {
        log.debug("Request to save CompetitionCategory : {}", competitionCategoryDTO);
        CompetitionCategory competitionCategory = competitionCategoryConverter.convertToEntity(competitionCategoryDTO);
        competitionCategory = competitionCategoryRepository.save(competitionCategory);
        CompetitionCategoryDTO result = competitionCategoryConverter.convertToDto(competitionCategory);
        return result;
    }

    /**
     * Get all the competitionCategories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CompetitionCategoryDTO> findAll() {
        log.debug("Request to get all CompetitionCategories");
        List<CompetitionCategoryDTO> result = competitionCategoryRepository.findAllWithEagerRelationships()
            .stream()
            .map(competitionCategoryConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get all the competitionCategories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CompetitionCategoryDTO> findByCompetitionId(Long competitionId) {
        log.debug("Request to get all CompetitionCategories");
        List<CompetitionCategoryDTO> result = sortAvailableCategories(competitionCategoryRepository.findAllByCompetitionId(
            competitionId)).stream()
            .map(competitionCategoryConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one competitionCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CompetitionCategoryDTO findOne(Long id) {
        log.debug("Request to get CompetitionCategory : {}", id);
        CompetitionCategory competitionCategory = competitionCategoryRepository.findOneWithEagerRelationships(id);
        CompetitionCategoryDTO competitionCategoryDTO = competitionCategoryConverter.convertToDto(competitionCategory);
        return competitionCategoryDTO;
    }

    /**
     * Delete the  competitionCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompetitionCategory : {}", id);
        competitionCategoryRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<CompetitionCategoryDTO> findAvailableByCompetitionId(RegisteredCoupleDTO registeredCoupleDTO,
                                                                     Long competitionId,
                                                                     Boolean isSoloCouple, Boolean isHobbyCouple) {
        log.debug("Request to get CompetitionCategory : {}", competitionId);
        List<CompetitionCategory> allCategories = competitionCategoryRepository.findAvailableByCompetitionId(
            competitionId);
        Set<CompetitionCategory> availableCategories = new HashSet<>();
        Competition competition = competitionRepository.findOne(competitionId);
        for(CompetitionCategory existingCategory : allCategories) {
            if(isSoloCouple && !existingCategory.isAllowSolo()) {
                continue;
            }
            if(existingCategory.isCheckMinAge()) {
                if(!checkMinAgeCategory(isSoloCouple,
                    existingCategory,
                    competition,
                    registeredCoupleDTO.getPartner1DateOfBirth(),
                    registeredCoupleDTO.getPartner2DateOfBirth())) {
                    continue;
                }
            }
            if(existingCategory.isCheckMaxAge()) {
                if(!checkMaxAgeCategory(isSoloCouple,
                    existingCategory,
                    competition,
                    registeredCoupleDTO.getPartner1DateOfBirth(),
                    registeredCoupleDTO.getPartner2DateOfBirth())) {
                    continue;
                }
            }
            if(null == existingCategory.getMaxDanceClass()) {
                availableCategories.add(existingCategory);
                continue;
            }
            if(null == existingCategory.getDanceCategory()) {
                if(checkDanceClasses(isSoloCouple,
                    existingCategory.getMaxDanceClass(),
                    registeredCoupleDTO.getPartner1DanceClassLA(),
                    registeredCoupleDTO.getPartner2DanceClassLA()) &&
                   checkDanceClasses(isSoloCouple,
                       existingCategory.getMaxDanceClass(),
                       registeredCoupleDTO.getPartner1DanceClassST(),
                       registeredCoupleDTO.getPartner2DanceClassST())) {
                    availableCategories.add(existingCategory);
                }
            } else {
                if(checkDanceClasses(isSoloCouple,
                    existingCategory.getMaxDanceClass(),
                    Objects.equals(existingCategory.getDanceCategory().getId(), DanceCategoryEnum.LA.getValue()) ?
                        registeredCoupleDTO.getPartner1DanceClassLA() :
                        registeredCoupleDTO.getPartner1DanceClassST(),
                    Objects.equals(existingCategory.getDanceCategory().getId(), DanceCategoryEnum.LA.getValue()) ?
                        registeredCoupleDTO.getPartner2DanceClassLA() :
                        registeredCoupleDTO.getPartner2DanceClassST())) {
                    availableCategories.add(existingCategory);
                }
            }
        }
        return competitionCategoryConverter.convertToDtos(sortAvailableCategories(availableCategories));
    }

    private boolean checkDanceClasses(Boolean isSoloCouple,
                                      DanceClass maxCategoryClass,
                                      NamedReferenceDTO partner1DanceClass,
                                      NamedReferenceDTO partner2DanceClass) {
        DanceClass partner1Class = danceClassRepository.findOne(partner1DanceClass.getId());
        DanceClass partner2Class = isSoloCouple ? null : danceClassRepository.findOne(partner2DanceClass.getId());
        return partner1Class.getWeight() <= maxCategoryClass.getWeight() &&
               (isSoloCouple || partner2Class.getWeight() <= maxCategoryClass.getWeight());
    }

    private boolean checkMinAgeCategory(Boolean isSoloCouple,
                                        CompetitionCategory existingCategory,
                                        Competition competition,
                                        LocalDate partner1Date,
                                        LocalDate partner2Date) {
        Set<AgeCategory> availableAgeCategories = existingCategory.getAgeCategories();
        LocalDate date = existingCategory.getDate();
        if(null == date) {
            date = competition.getEndDate();
        }
        for(AgeCategory ageCategory : availableAgeCategories) {
            if(!isDateBigger(date, partner1Date, ageCategory.getMinAge()) &&
               (isSoloCouple || !isDateBigger(date, partner2Date, ageCategory.getMinAge()))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkMaxAgeCategory(Boolean isSoloCouple,
                                        CompetitionCategory existingCategory,
                                        Competition competition,
                                        LocalDate partner1Date,
                                        LocalDate partner2Date) {
        Set<AgeCategory> availableAgeCategories = existingCategory.getAgeCategories();
        LocalDate date = existingCategory.getDate();
        if(null == date) {
            date = competition.getStartDate();
        }
        for(AgeCategory ageCategory : availableAgeCategories) {
            if(!isDateSmaller(date, partner1Date, ageCategory.getMaxAge() + 1) &&
               (isSoloCouple || !isDateSmaller(date, partner2Date, ageCategory.getMaxAge() + 1))) {
                return true;
            }
        }
        return false;
    }

    private boolean isDateSmaller(LocalDate date, LocalDate birthday, int years) {
        LocalDate newDate = birthday.plusYears(years);
        return newDate.isBefore(date);
    }

    private boolean isDateBigger(LocalDate date, LocalDate birthday, int years) {
        LocalDate newDate = birthday.plusYears(years);
        return newDate.isAfter(date);
    }

    private List<CompetitionCategory> sortAvailableCategories(Collection<CompetitionCategory> availableCategories) {
        List<CompetitionCategory> competitionCategories = new ArrayList<>(availableCategories);
        Collections.sort(competitionCategories);
        return competitionCategories;
    }

}
