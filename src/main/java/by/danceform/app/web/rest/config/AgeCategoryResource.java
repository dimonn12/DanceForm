package by.danceform.app.web.rest.config;

import by.danceform.app.dto.config.AgeCategoryDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.config.AgeCategoryService;
import by.danceform.app.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AgeCategory.
 */
@RestController
@RequestMapping("/api/config/age-categories")
@Secured(AuthoritiesConstants.ADMIN)
public class AgeCategoryResource {

    private final Logger log = LoggerFactory.getLogger(AgeCategoryResource.class);

    @Inject
    private AgeCategoryService ageCategoryService;

    /**
     * POST  /age-categories : Create a new ageCategory.
     *
     * @param ageCategoryDTO the ageCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ageCategoryDTO, or with status 400 (Bad Request) if the ageCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AgeCategoryDTO> createAgeCategory(@Valid @RequestBody AgeCategoryDTO ageCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save AgeCategory : {}", ageCategoryDTO);
        if(ageCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("ageCategory",
                    "idexists",
                    "A new ageCategory cannot already have an ID"))
                .body(null);
        }
        AgeCategoryDTO result = ageCategoryService.save(ageCategoryDTO);
        return ResponseEntity.created(new URI("/api/config/age-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ageCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /age-categories : Updates an existing ageCategory.
     *
     * @param ageCategoryDTO the ageCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ageCategoryDTO,
     * or with status 400 (Bad Request) if the ageCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the ageCategoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AgeCategoryDTO> updateAgeCategory(@Valid @RequestBody AgeCategoryDTO ageCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to update AgeCategory : {}", ageCategoryDTO);
        if(ageCategoryDTO.getId() == null) {
            return createAgeCategory(ageCategoryDTO);
        }
        AgeCategoryDTO result = ageCategoryService.save(ageCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ageCategory", ageCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /age-categories : get all the ageCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ageCategories in body
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public List<AgeCategoryDTO> getAllAgeCategories() {
        log.debug("REST request to get all AgeCategories");
        return ageCategoryService.findAll();
    }

    /**
     * GET  /age-categories/:id : get the "id" ageCategory.
     *
     * @param id the id of the ageCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ageCategoryDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public ResponseEntity<AgeCategoryDTO> getAgeCategory(@PathVariable Long id) {
        log.debug("REST request to get AgeCategory : {}", id);
        AgeCategoryDTO ageCategoryDTO = ageCategoryService.findOne(id);
        return Optional.ofNullable(ageCategoryDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /age-categories/:id : delete the "id" ageCategory.
     *
     * @param id the id of the ageCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAgeCategory(@PathVariable Long id) {
        log.debug("REST request to delete AgeCategory : {}", id);
        ageCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ageCategory", id.toString())).build();
    }

}
