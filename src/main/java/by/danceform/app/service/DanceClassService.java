package by.danceform.app.service;

import by.danceform.app.service.dto.DanceClassDTO;

import java.util.List;

/**
 * Service Interface for managing DanceClass.
 */
public interface DanceClassService {

    /**
     * Save a danceClass.
     *
     * @param danceClassDTO the entity to save
     * @return the persisted entity
     */
    DanceClassDTO save(DanceClassDTO danceClassDTO);

    /**
     * Get all the danceClasses.
     *
     * @return the list of entities
     */
    List<DanceClassDTO> findAll();

    /**
     * Get the "id" danceClass.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DanceClassDTO findOne(Long id);

    /**
     * Delete the "id" danceClass.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
