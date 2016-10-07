package by.danceform.app.web.rest;

import by.danceform.app.service.DanceClassService;
import by.danceform.app.service.dto.DanceClassDTO;
import by.danceform.app.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 * REST controller for managing DanceClass.
 */
@RestController
@RequestMapping("/api")
public class DanceClassResource {

    private final Logger log = LoggerFactory.getLogger(DanceClassResource.class);

    @Inject
    private DanceClassService danceClassService;

    /**
     * POST  /dance-classes : Create a new danceClass.
     *
     * @param danceClassDTO the danceClassDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new danceClassDTO, or with status 400 (Bad Request) if the danceClass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dance-classes",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DanceClassDTO> createDanceClass(@Valid @RequestBody DanceClassDTO danceClassDTO)
        throws URISyntaxException {
        log.debug("REST request to save DanceClass : {}", danceClassDTO);
        if(danceClassDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("danceClass",
                    "idexists",
                    "A new danceClass cannot already have an ID"))
                .body(null);
        }
        DanceClassDTO result = danceClassService.save(danceClassDTO);
        return ResponseEntity.created(new URI("/api/dance-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("danceClass", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dance-classes : Updates an existing danceClass.
     *
     * @param danceClassDTO the danceClassDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated danceClassDTO,
     * or with status 400 (Bad Request) if the danceClassDTO is not valid,
     * or with status 500 (Internal Server Error) if the danceClassDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dance-classes",
                    method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DanceClassDTO> updateDanceClass(@Valid @RequestBody DanceClassDTO danceClassDTO)
        throws URISyntaxException {
        log.debug("REST request to update DanceClass : {}", danceClassDTO);
        if(danceClassDTO.getId() == null) {
            return createDanceClass(danceClassDTO);
        }
        DanceClassDTO result = danceClassService.save(danceClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("danceClass", danceClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dance-classes : get all the danceClasses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of danceClasses in body
     */
    @RequestMapping(value = "/dance-classes",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DanceClassDTO> getAllDanceClasses() {
        log.debug("REST request to get all DanceClasses");
        return danceClassService.findAll();
    }

    /**
     * GET  /dance-classes/:id : get the "id" danceClass.
     *
     * @param id the id of the danceClassDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the danceClassDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/dance-classes/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DanceClassDTO> getDanceClass(@PathVariable Long id) {
        log.debug("REST request to get DanceClass : {}", id);
        DanceClassDTO danceClassDTO = danceClassService.findOne(id);
        return Optional.ofNullable(danceClassDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dance-classes/:id : delete the "id" danceClass.
     *
     * @param id the id of the danceClassDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/dance-classes/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDanceClass(@PathVariable Long id) {
        log.debug("REST request to delete DanceClass : {}", id);
        danceClassService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("danceClass", id.toString())).build();
    }

}
