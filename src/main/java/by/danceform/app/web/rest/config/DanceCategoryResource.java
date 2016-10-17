package by.danceform.app.web.rest.config;

import by.danceform.app.dto.config.DanceCategoryDTO;
import by.danceform.app.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import by.danceform.app.domain.config.DanceCategory;
import by.danceform.app.service.config.DanceCategoryService;
import by.danceform.app.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DanceCategory.
 */
@RestController
@RequestMapping("/api/config/dance-categories")
@Secured(AuthoritiesConstants.ADMIN)
public class DanceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(DanceCategoryResource.class);

    @Inject
    private DanceCategoryService danceCategoryService;

    /**
     * POST  /dance-categories : Create a new danceCategory.
     *
     * @param danceCategory the danceCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new danceCategory, or with status 400 (Bad Request) if the danceCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DanceCategoryDTO> createDanceCategory(@Valid @RequestBody DanceCategoryDTO danceCategory)
        throws URISyntaxException {
        log.debug("REST request to save DanceCategory : {}", danceCategory);
        if(danceCategory.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("danceCategory",
                    "idexists",
                    "A new danceCategory cannot already have an ID"))
                .body(null);
        }
        DanceCategoryDTO result = danceCategoryService.save(danceCategory);
        return ResponseEntity.created(new URI("/api/config/dance-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("danceCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dance-categories : Updates an existing danceCategory.
     *
     * @param danceCategory the danceCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated danceCategory,
     * or with status 400 (Bad Request) if the danceCategory is not valid,
     * or with status 500 (Internal Server Error) if the danceCategory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DanceCategoryDTO> updateDanceCategory(@Valid @RequestBody DanceCategoryDTO danceCategory)
        throws URISyntaxException {
        log.debug("REST request to update DanceCategory : {}", danceCategory);
        if(danceCategory.getId() == null) {
            return createDanceCategory(danceCategory);
        }
        DanceCategoryDTO result = danceCategoryService.save(danceCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("danceCategory", danceCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dance-categories : get all the danceCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of danceCategories in body
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public List<DanceCategoryDTO> getAllDanceCategories() {
        log.debug("REST request to get all DanceCategories");
        return danceCategoryService.findAll();
    }

    /**
     * GET  /dance-categories/:id : get the "id" danceCategory.
     *
     * @param id the id of the danceCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the danceCategory, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public ResponseEntity<DanceCategoryDTO> getDanceCategory(@PathVariable Long id) {
        log.debug("REST request to get DanceCategory : {}", id);
        DanceCategoryDTO danceCategory = danceCategoryService.findOne(id);
        return Optional.ofNullable(danceCategory)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dance-categories/:id : delete the "id" danceCategory.
     *
     * @param id the id of the danceCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDanceCategory(@PathVariable Long id) {
        log.debug("REST request to delete DanceCategory : {}", id);
        danceCategoryService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("danceCategory", id.toString()))
            .build();
    }

}
