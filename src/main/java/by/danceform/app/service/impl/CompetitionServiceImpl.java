package by.danceform.app.service.impl;

import by.danceform.app.domain.Competition;
import by.danceform.app.repository.CompetitionRepository;
import by.danceform.app.service.CompetitionService;
import by.danceform.app.service.dto.CompetitionDTO;
import by.danceform.app.service.mapper.CompetitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Competition.
 */
@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    private final Logger log = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private CompetitionMapper competitionMapper;

    /**
     * Save a competition.
     *
     * @param competitionDTO the entity to save
     * @return the persisted entity
     */
    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        log.debug("Request to save Competition : {}", competitionDTO);
        Competition competition = competitionMapper.competitionDTOToCompetition(competitionDTO);
        competition = competitionRepository.save(competition);
        CompetitionDTO result = competitionMapper.competitionToCompetitionDTO(competition);
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
        return result.map(competition -> competitionMapper.competitionToCompetitionDTO(competition));
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
        CompetitionDTO competitionDTO = competitionMapper.competitionToCompetitionDTO(competition);
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
