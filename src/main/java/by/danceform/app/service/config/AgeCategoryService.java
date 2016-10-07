package by.danceform.app.service.config;

import by.danceform.app.converter.config.AgeCategoryConverter;
import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.dto.config.AgeCategoryDTO;
import by.danceform.app.repository.config.AgeCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AgeCategory.
 */
@Service
@Transactional
public class AgeCategoryService {

    private final Logger log = LoggerFactory.getLogger(AgeCategoryService.class);

    @Inject
    private AgeCategoryRepository ageCategoryRepository;

    @Inject
    private AgeCategoryConverter ageCategoryConverter;

    /**
     * Save a ageCategory.
     *
     * @param ageCategoryDTO the entity to save
     * @return the persisted entity
     */
    public AgeCategoryDTO save(AgeCategoryDTO ageCategoryDTO) {
        log.debug("Request to save AgeCategory : {}", ageCategoryDTO);
        AgeCategory ageCategory = ageCategoryConverter.convertToEntity(ageCategoryDTO);
        ageCategory = ageCategoryRepository.save(ageCategory);
        AgeCategoryDTO result = ageCategoryConverter.convertToDto(ageCategory);
        return result;
    }

    /**
     * Get all the ageCategories.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AgeCategoryDTO> findAll() {
        log.debug("Request to get all AgeCategories");
        List<AgeCategoryDTO> result = ageCategoryRepository.findAll()
            .stream()
            .map(ageCategoryConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one ageCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AgeCategoryDTO findOne(Long id) {
        log.debug("Request to get AgeCategory : {}", id);
        AgeCategory ageCategory = ageCategoryRepository.findOne(id);
        AgeCategoryDTO ageCategoryDTO = ageCategoryConverter.convertToDto(ageCategory);
        return ageCategoryDTO;
    }

    /**
     * Delete the  ageCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AgeCategory : {}", id);
        ageCategoryRepository.delete(id);
    }
}
