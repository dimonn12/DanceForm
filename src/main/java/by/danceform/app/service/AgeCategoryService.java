package by.danceform.app.service;

import by.danceform.app.service.dto.AgeCategoryDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing AgeCategory.
 */
public interface AgeCategoryService {

    /**
     * Save a ageCategory.
     *
     * @param ageCategoryDTO the entity to save
     * @return the persisted entity
     */
    AgeCategoryDTO save(AgeCategoryDTO ageCategoryDTO);

    /**
     *  Get all the ageCategories.
     *  
     *  @return the list of entities
     */
    List<AgeCategoryDTO> findAll();

    /**
     *  Get the "id" ageCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AgeCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" ageCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
