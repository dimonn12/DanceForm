package by.danceform.app.web.rest.couple;

import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.couple.RegisteredCoupleService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RegisteredCouple.
 */
@RestController
@RequestMapping("/api/registered-couples")
@Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
public class RegisteredCoupleResource {

    private final Logger log = LoggerFactory.getLogger(RegisteredCoupleResource.class);

    @Inject
    private RegisteredCoupleService registeredCoupleService;

    /**
     * POST  /registered-couples : Create a new registeredCouple.
     *
     * @param registeredCoupleDTO the registeredCoupleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registeredCoupleDTO, or with status 400 (Bad Request) if the registeredCouple has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegisteredCoupleDTO> createRegisteredCouple(
        @Valid @RequestBody RegisteredCoupleDTO registeredCoupleDTO) throws URISyntaxException {
        log.debug("REST request to save RegisteredCouple : {}", registeredCoupleDTO);
        if(registeredCoupleDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("registeredCouple",
                    "idexists",
                    "A new registeredCouple cannot already have an ID"))
                .body(null);
        }
        RegisteredCoupleDTO result = registeredCoupleService.save(registeredCoupleDTO);
        return ResponseEntity.created(new URI("/api/registered-couples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("registeredCouple", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /registered-couples : Updates an existing registeredCouple.
     *
     * @param registeredCoupleDTO the registeredCoupleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registeredCoupleDTO,
     * or with status 400 (Bad Request) if the registeredCoupleDTO is not valid,
     * or with status 500 (Internal Server Error) if the registeredCoupleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<RegisteredCoupleDTO> updateRegisteredCouple(
        @Valid @RequestBody RegisteredCoupleDTO registeredCoupleDTO) throws URISyntaxException {
        log.debug("REST request to update RegisteredCouple : {}", registeredCoupleDTO);
        if(registeredCoupleDTO.getId() == null) {
            return createRegisteredCouple(registeredCoupleDTO);
        }
        RegisteredCoupleDTO result = registeredCoupleService.save(registeredCoupleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("registeredCouple", registeredCoupleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /registered-couples : get all the registeredCouples.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of registeredCouples in body
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<RegisteredCoupleDTO> getAllRegisteredCouples() {
        log.debug("REST request to get all RegisteredCouples");
        return registeredCoupleService.findAll();
    }

    /**
     * GET  /registered-couples : get all the registeredCouples.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of registeredCouples in body
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE, params = { "categoryId" })
    @Timed
    public List<RegisteredCoupleDTO> getAllRegisteredCouplesByCategoryId(@RequestParam("categoryId") Long categoryId) {
        log.debug("REST request to get all RegisteredCouples");
        return registeredCoupleService.findByCategoryId(categoryId);
    }

    /**
     * GET  /registered-couples/:id : get the "id" registeredCouple.
     *
     * @param id the id of the registeredCoupleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registeredCoupleDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RegisteredCoupleDTO> getRegisteredCouple(@PathVariable Long id) {
        log.debug("REST request to get RegisteredCouple : {}", id);
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleService.findOne(id);
        return Optional.ofNullable(registeredCoupleDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /registered-couples/:id : delete the "id" registeredCouple.
     *
     * @param id the id of the registeredCoupleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteRegisteredCouple(@PathVariable Long id) {
        log.debug("REST request to delete RegisteredCouple : {}", id);
        registeredCoupleService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("registeredCouple", id.toString()))
            .build();
    }

}
