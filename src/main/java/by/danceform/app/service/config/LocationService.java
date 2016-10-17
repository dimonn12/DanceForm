package by.danceform.app.service.config;

import by.danceform.app.converter.config.LocationConverter;
import by.danceform.app.domain.config.Location;
import by.danceform.app.dto.config.LocationDTO;
import by.danceform.app.repository.config.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Service Implementation for managing Location.
 */
@Service
@Transactional
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationService.class);

    @Inject
    private LocationRepository locationRepository;

    @Inject
    private LocationConverter locationConverter;

    /**
     * Save a location.
     *
     * @param location the entity to save
     * @return the persisted entity
     */
    public LocationDTO save(LocationDTO location) {
        log.debug("Request to save Location : {}", location);
        Location result = locationRepository.save(locationConverter.convertToEntity(location));
        return locationConverter.convertToDto(result);
    }

    /**
     * Get all the locations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Locations");
        Page<Location> result = locationRepository.findAll(pageable);
        return result.map(location -> locationConverter.convertToDto(location));
    }

    /**
     * Get one location by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public LocationDTO findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        return locationConverter.convertToDto(locationRepository.findOne(id));
    }

    /**
     * Delete the  location by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.delete(id);
    }
}
