package by.danceform.app.service.config;

import by.danceform.app.converter.config.TrainerConverter;
import by.danceform.app.dto.config.TrainerDTO;
import by.danceform.app.repository.config.TrainerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Trainer.
 */
@Service
@Transactional
public class TrainerService {

    private final Logger log = LoggerFactory.getLogger(TrainerService.class);

    @Inject
    private TrainerRepository trainerRepository;

    @Inject
    private TrainerConverter trainerConverter;

    /**
     * Save a trainer.
     *
     * @param trainer the entity to save
     * @return the persisted entity
     */
    public TrainerDTO save(TrainerDTO trainer) {
        log.debug("Request to save Trainer : {}", trainer);
        return trainerConverter.convertToDto(trainerRepository.save(trainerConverter.convertToEntity(trainer)));
    }

    /**
     * Get all the trainers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TrainerDTO> findAll() {
        log.debug("Request to get all Trainers");
        return trainerConverter.convertToDtos(trainerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<TrainerDTO> findVisible() {
        log.debug("Request to get all visible Trainers");
        return trainerConverter.convertToDtos(trainerRepository.findVisible());
    }


    /**
     * Get one trainer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TrainerDTO findOne(Long id) {
        log.debug("Request to get Trainer : {}", id);
        return trainerConverter.convertToDto(trainerRepository.findOne(id));
    }

    /**
     * Delete the  trainer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Trainer : {}", id);
        trainerRepository.delete(id);
    }
}
