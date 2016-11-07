package by.danceform.app.web.rest.config;

import by.danceform.app.dto.config.OrganizationDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.security.SecurityUtils;
import by.danceform.app.service.config.OrganizationService;
import by.danceform.app.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Organization.
 */
@RestController
@RequestMapping("/api/config/organizations")
@Secured(AuthoritiesConstants.ADMIN)
public class OrganizationResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationResource.class);

    @Inject
    private OrganizationService organizationService;

    /**
     * POST  /organizations : Create a new organization.
     *
     * @param organization the organization to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organization, or with status 400 (Bad Request) if the organization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationDTO> createOrganization(@Valid @RequestBody OrganizationDTO organization)
        throws URISyntaxException {
        log.debug("REST request to save Organization : {}", organization);
        if(organization.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("organization",
                    "idexists",
                    "A new organization cannot already have an ID"))
                .body(null);
        }
        OrganizationDTO result = organizationService.save(organization);
        return ResponseEntity.created(new URI("/api/config/organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("organization", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organizations : Updates an existing organization.
     *
     * @param organization the organization to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organization,
     * or with status 400 (Bad Request) if the organization is not valid,
     * or with status 500 (Internal Server Error) if the organization couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrganizationDTO> updateOrganization(@Valid @RequestBody OrganizationDTO organization)
        throws URISyntaxException {
        log.debug("REST request to update Organization : {}", organization);
        if(organization.getId() == null) {
            return createOrganization(organization);
        }
        OrganizationDTO result = organizationService.save(organization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("organization", organization.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organizations : get all the organizations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of organizations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() throws URISyntaxException {
        log.debug("REST request to get a page of Organizations");
        List<OrganizationDTO> result;
        if(SecurityUtils.isAdmin()) {
            result = organizationService.findAll();
        } else {
            result = organizationService.findVisible();
        }
        Collections.sort(result, (o1, o2) -> ObjectUtils.compare(o1.getName(), o2.getName()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * GET  /organizations/:id : get the "id" organization.
     *
     * @param id the id of the organization to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organization, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable Long id) {
        log.debug("REST request to get Organization : {}", id);
        OrganizationDTO organization = organizationService.findOne(id);
        return Optional.ofNullable(organization)
            .filter(result -> {
                if(SecurityUtils.isAdmin()) {
                    return true;
                }
                return result.isVisible();
            })
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /organizations/:id : delete the "id" organization.
     *
     * @param id the id of the organization to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        log.debug("REST request to delete Organization : {}", id);
        organizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organization", id.toString())).build();
    }

}
