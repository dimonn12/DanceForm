package by.danceform.app.service.impl;

import by.danceform.app.service.AgeCategoryService;
import by.danceform.app.domain.AgeCategory;
import by.danceform.app.repository.AgeCategoryRepository;
import by.danceform.app.service.dto.AgeCategoryDTO;
import by.danceform.app.service.mapper.AgeCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AgeCategory.
 */
@Service
@Transactional
public class AgeCategoryServiceImpl implements AgeCategoryService{

    private final Logger log = LoggerFactory.getLogger(AgeCategoryServiceImpl.class);
    
    @Inject
    private AgeCategoryRepository ageCategoryRepository;

    @Inject
    private AgeCategoryMapper ageCategoryMapper;

    /**
     * Save a ageCategory.
     *
     * @param ageCategoryDTO the entity to save
     * @return the persisted entity
     */
    public AgeCategoryDTO save(AgeCategoryDTO ageCategoryDTO) {
        log.debug("Request to save AgeCategory : {}", ageCategoryDTO);
        AgeCategory ageCategory = ageCategoryMapper.ageCategoryDTOToAgeCategory(ageCategoryDTO);
        ageCategory = ageCategoryRepository.save(ageCategory);
        AgeCategoryDTO result = ageCategoryMapper.ageCategoryToAgeCategoryDTO(ageCategory);
        return result;
    }

    /**
     *  Get all the ageCategories.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AgeCategoryDTO> findAll() {
        log.debug("Request to get all AgeCategories");
        List<AgeCategoryDTO> result = ageCategoryRepository.findAll().stream()
            .map(ageCategoryMapper::ageCategoryToAgeCategoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one ageCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AgeCategoryDTO findOne(Long id) {
        log.debug("Request to get AgeCategory : {}", id);
        AgeCategory ageCategory = ageCategoryRepository.findOne(id);
        AgeCategoryDTO ageCategoryDTO = ageCategoryMapper.ageCategoryToAgeCategoryDTO(ageCategory);
        return ageCategoryDTO;
    }

    /**
     *  Delete the  ageCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AgeCategory : {}", id);
        ageCategoryRepository.delete(id);
    }
}
