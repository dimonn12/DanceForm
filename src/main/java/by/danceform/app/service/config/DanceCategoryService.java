package by.danceform.app.service.config;

import by.danceform.app.converter.config.DanceCategoryConverter;
import by.danceform.app.domain.config.DanceCategory;
import by.danceform.app.dto.config.DanceCategoryDTO;
import by.danceform.app.repository.config.DanceCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DanceCategory.
 */
@Service
@Transactional
public class DanceCategoryService {

    private final Logger log = LoggerFactory.getLogger(DanceCategoryService.class);

    @Inject
    private DanceCategoryRepository danceCategoryRepository;

    @Inject
    private DanceCategoryConverter danceCategoryConverter;

    /**
     * Save a danceCategory.
     *
     * @param danceCategoryDto the entity to save
     * @return the persisted entity
     */
    public DanceCategoryDTO save(DanceCategoryDTO danceCategoryDto) {
        log.debug("Request to save DanceCategory : {}", danceCategoryDto);
        DanceCategory danceCategory = danceCategoryConverter.convertToEntity(danceCategoryDto);
        danceCategory = danceCategoryRepository.save(danceCategory);
        return danceCategoryConverter.convertToDto(danceCategory);
    }

    /**
     * Get all the danceCategories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DanceCategoryDTO> findAll() {
        log.debug("Request to get all DanceCategories");
        List<DanceCategoryDTO> result = danceCategoryRepository.findAll()
            .stream()
            .map(danceCategoryConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one danceCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DanceCategoryDTO findOne(Long id) {
        log.debug("Request to get DanceCategory : {}", id);
        DanceCategory danceCategory = danceCategoryRepository.findOne(id);
        return danceCategoryConverter.convertToDto(danceCategory);
    }

    /**
     * Delete the  danceCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DanceCategory : {}", id);
        danceCategoryRepository.delete(id);
    }
}
