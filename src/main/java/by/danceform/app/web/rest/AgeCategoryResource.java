package by.danceform.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import by.danceform.app.service.AgeCategoryService;
import by.danceform.app.web.rest.util.HeaderUtil;
import by.danceform.app.service.dto.AgeCategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing AgeCategory.
 */
@RestController
@RequestMapping("/api")
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
    @RequestMapping(value = "/age-categories",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AgeCategoryDTO> createAgeCategory(@Valid @RequestBody AgeCategoryDTO ageCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save AgeCategory : {}", ageCategoryDTO);
        if (ageCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ageCategory", "idexists", "A new ageCategory cannot already have an ID")).body(null);
        }
        AgeCategoryDTO result = ageCategoryService.save(ageCategoryDTO);
        return ResponseEntity.created(new URI("/api/age-categories/" + result.getId()))
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
    @RequestMapping(value = "/age-categories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AgeCategoryDTO> updateAgeCategory(@Valid @RequestBody AgeCategoryDTO ageCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update AgeCategory : {}", ageCategoryDTO);
        if (ageCategoryDTO.getId() == null) {
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
    @RequestMapping(value = "/age-categories",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
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
    @RequestMapping(value = "/age-categories/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AgeCategoryDTO> getAgeCategory(@PathVariable Long id) {
        log.debug("REST request to get AgeCategory : {}", id);
        AgeCategoryDTO ageCategoryDTO = ageCategoryService.findOne(id);
        return Optional.ofNullable(ageCategoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /age-categories/:id : delete the "id" ageCategory.
     *
     * @param id the id of the ageCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/age-categories/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAgeCategory(@PathVariable Long id) {
        log.debug("REST request to delete AgeCategory : {}", id);
        ageCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ageCategory", id.toString())).build();
    }

}
