package by.danceform.app.service.competition;

import by.danceform.app.converter.competition.CompetitionConverter;
import by.danceform.app.domain.competition.Competition;
import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.repository.competition.CompetitionRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Competition.
 */
@Service
@Transactional
public class CompetitionService {

    private final Logger log = LoggerFactory.getLogger(CompetitionService.class);

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private CompetitionConverter competitionConverter;

    /**
     * Save a competition.
     *
     * @param competitionDTO the entity to save
     * @return the persisted entity
     */
    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        log.debug("Request to save Competition : {}", competitionDTO);
        Competition competition = competitionConverter.convertToEntity(competitionDTO);
        competition = competitionRepository.save(competition);
        CompetitionDTO result = competitionConverter.convertToDto(competition);
        return result;
    }

    /**
     * Get all the competitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompetitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competitions");
        Page<Competition> result = competitionRepository.findAll(pageable);
        return result.map(competition -> competitionConverter.convertToDto(competition));
    }

    /**
     * Get one competition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CompetitionDTO findOne(Long id) {
        log.debug("Request to get Competition : {}", id);
        Competition competition = competitionRepository.findOne(id);
        CompetitionDTO competitionDTO = competitionConverter.convertToDto(competition);
        return competitionDTO;
    }

    /**
     * Delete the  competition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Competition : {}", id);
        competitionRepository.delete(id);
    }

}
