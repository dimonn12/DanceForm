package by.danceform.app.service.config;

import by.danceform.app.converter.config.DanceClassConverter;
import by.danceform.app.domain.config.DanceClass;
import by.danceform.app.dto.config.DanceClassDTO;
import by.danceform.app.repository.config.DanceClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DanceClass.
 */
@Service
@Transactional
public class DanceClassService {

    private final Logger log = LoggerFactory.getLogger(DanceClassService.class);

    @Inject
    private DanceClassRepository danceClassRepository;

    @Inject
    private DanceClassConverter danceClassConverter;

    /**
     * Save a danceClass.
     *
     * @param danceClassDTO the entity to save
     * @return the persisted entity
     */
    public DanceClassDTO save(DanceClassDTO danceClassDTO) {
        log.debug("Request to save DanceClass : {}", danceClassDTO);
        DanceClass danceClass = danceClassConverter.convertToEntity(danceClassDTO);
        danceClass = danceClassRepository.save(danceClass);
        DanceClassDTO result = danceClassConverter.convertToDto(danceClass);
        return result;
    }

    /**
     * Get all the danceClasses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DanceClassDTO> findAll() {
        log.debug("Request to get all DanceClasses");
        List<DanceClassDTO> result = danceClassRepository.findAll()
            .stream()
            .map(danceClassConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one danceClass by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DanceClassDTO findOne(Long id) {
        log.debug("Request to get DanceClass : {}", id);
        DanceClass danceClass = danceClassRepository.findOne(id);
        DanceClassDTO danceClassDTO = danceClassConverter.convertToDto(danceClass);
        return danceClassDTO;
    }

    /**
     * Delete the  danceClass by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DanceClass : {}", id);
        danceClassRepository.delete(id);
    }
}
