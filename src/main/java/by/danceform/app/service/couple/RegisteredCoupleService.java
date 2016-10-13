package by.danceform.app.service.couple;

import by.danceform.app.converter.couple.RegisteredCoupleConverter;
import by.danceform.app.domain.couple.RegisteredCouple;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.repository.competition.CompetitionCategoryRepository;
import by.danceform.app.repository.couple.RegisteredCoupleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RegisteredCouple.
 */
@Service
@Transactional
public class RegisteredCoupleService {

    private final Logger log = LoggerFactory.getLogger(RegisteredCoupleService.class);

    @Inject
    private RegisteredCoupleRepository registeredCoupleRepository;

    @Inject
    private RegisteredCoupleConverter registeredCoupleConverter;

    @Inject
    private CompetitionCategoryRepository competitionCategoryRepository;

    /**
     * Save a registeredCouple.
     *
     * @param registeredCoupleDTO the entity to save
     * @return the persisted entity
     */
    public RegisteredCoupleDTO save(RegisteredCoupleDTO registeredCoupleDTO) {
        log.debug("Request to save RegisteredCouple : {}", registeredCoupleDTO);
        RegisteredCoupleDTO result = null;
        if(null != registeredCoupleDTO.getCompetitionCategoryIds()) {
            for(Long competitionCategoryId : registeredCoupleDTO.getCompetitionCategoryIds()) {
                RegisteredCouple registeredCouple = registeredCoupleConverter.convertToEntity(registeredCoupleDTO);
                registeredCouple.setCompetitionCategory(competitionCategoryRepository.findOne(competitionCategoryId));
                registeredCouple = registeredCoupleRepository.save(registeredCouple);
                result = registeredCoupleConverter.convertToDto(registeredCouple);
            }
        }
        return result;
    }

    /**
     * Get all the registeredCouples.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RegisteredCoupleDTO> findAll() {
        log.debug("Request to get all RegisteredCouples");
        List<RegisteredCoupleDTO> result = registeredCoupleRepository.findAll()
            .stream()
            .map(registeredCoupleConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one registeredCouple by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RegisteredCoupleDTO findOne(Long id) {
        log.debug("Request to get RegisteredCouple : {}", id);
        RegisteredCouple registeredCouple = registeredCoupleRepository.findOne(id);
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);
        return registeredCoupleDTO;
    }

    /**
     * Get one registeredCouple by id.
     *
     * @param categoryId the id of the category
     * @return the entity
     */
    @Transactional(readOnly = true)
    public List<RegisteredCoupleDTO> findByCategoryId(Long categoryId) {
        log.debug("Request to get RegisteredCouple : {}", categoryId);
        List<RegisteredCoupleDTO> result = registeredCoupleRepository.findAllByCategoryId(categoryId)
            .stream()
            .map(registeredCoupleConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Delete the  registeredCouple by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RegisteredCouple : {}", id);
        registeredCoupleRepository.delete(id);
    }
}
