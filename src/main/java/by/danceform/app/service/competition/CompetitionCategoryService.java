package by.danceform.app.service.competition;

import by.danceform.app.converter.competition.CompetitionCategoryConverter;
import by.danceform.app.domain.competition.CompetitionCategory;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.repository.competition.CompetitionCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
        List<CompetitionCategoryDTO> result = competitionCategoryRepository.findAllByCompetitionId(competitionId)
            .stream()
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

    @Transactional(readOnly = true)
    public List<CompetitionCategoryDTO> findAvailableByCompetitionId(RegisteredCoupleDTO registeredCoupleDTO, Long competitionId) {
        log.debug("Request to get CompetitionCategory : {}", competitionId);
        List<CompetitionCategoryDTO> allCategories = findByCompetitionId(competitionId);
        List<CompetitionCategoryDTO> availableCategories = new ArrayList<>();
        return availableCategories;
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
}
