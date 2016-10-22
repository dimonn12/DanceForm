package by.danceform.app.service.config;

import by.danceform.app.converter.config.OrganizationConverter;
import by.danceform.app.domain.config.Organization;
import by.danceform.app.dto.config.OrganizationDTO;
import by.danceform.app.repository.config.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Organization.
 */
@Service
@Transactional
public class OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationService.class);

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private OrganizationConverter organizationConverter;

    /**
     * Save a organization.
     *
     * @param organization the entity to save
     * @return the persisted entity
     */
    public OrganizationDTO save(OrganizationDTO organization) {
        log.debug("Request to save Organization : {}", organization);
        return organizationConverter.convertToDto(organizationRepository.save(organizationConverter.convertToEntity(
            organization)));
    }

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organizations");
        Page<Organization> result = organizationRepository.findAll(pageable);
        return result.map(organization -> organizationConverter.convertToDto(organization));
    }

    @Transactional(readOnly = true)
    public List<OrganizationDTO> findVisible() {
        log.debug("Request to get all Organizations");
        return organizationConverter.convertToDtos(organizationRepository.findVisible());
    }

    /**
     * Get one organization by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrganizationDTO findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        return organizationConverter.convertToDto(organizationRepository.findOne(id));
    }

    /**
     * Delete the  organization by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.delete(id);
    }
}
