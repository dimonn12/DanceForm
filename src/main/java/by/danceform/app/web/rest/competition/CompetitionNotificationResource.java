package by.danceform.app.web.rest.competition;

import by.danceform.app.dto.competition.CompetitionNotificationDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.competition.CompetitionNotificationService;
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
 * REST controller for managing CompetitionNotification.
 */
@RestController
@RequestMapping("/api/competition-notifications")
@Secured(AuthoritiesConstants.ADMIN)
public class CompetitionNotificationResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionNotificationResource.class);

    @Inject
    private CompetitionNotificationService competitionNotificationService;

    /**
     * POST  /competition-notifications : Create a new competitionNotification.
     *
     * @param competitionNotificationDTO the competitionNotificationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new competitionNotificationDTO, or with status 400 (Bad Request) if the competitionNotification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompetitionNotificationDTO> createCompetitionNotification(
        @Valid @RequestBody CompetitionNotificationDTO competitionNotificationDTO) throws URISyntaxException {
        log.debug("REST request to save CompetitionNotification : {}", competitionNotificationDTO);
        if(competitionNotificationDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("competitionNotification",
                    "idexists",
                    "A new competitionNotification cannot already have an ID"))
                .body(null);
        }
        CompetitionNotificationDTO result = competitionNotificationService.save(competitionNotificationDTO);
        return ResponseEntity.created(new URI("/api/competition-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("competitionNotification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /competition-notifications : Updates an existing competitionNotification.
     *
     * @param competitionNotificationDTO the competitionNotificationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated competitionNotificationDTO,
     * or with status 400 (Bad Request) if the competitionNotificationDTO is not valid,
     * or with status 500 (Internal Server Error) if the competitionNotificationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompetitionNotificationDTO> updateCompetitionNotification(
        @Valid @RequestBody CompetitionNotificationDTO competitionNotificationDTO) throws URISyntaxException {
        log.debug("REST request to update CompetitionNotification : {}", competitionNotificationDTO);
        if(competitionNotificationDTO.getId() == null) {
            return createCompetitionNotification(competitionNotificationDTO);
        }
        CompetitionNotificationDTO result = competitionNotificationService.save(competitionNotificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("competitionNotification",
                competitionNotificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /competition-notifications : get all the competitionNotifications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of competitionNotifications in body
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CompetitionNotificationDTO> getAllCompetitionNotifications() {
        log.debug("REST request to get all CompetitionNotifications");
        return competitionNotificationService.findAll();
    }

    /**
     * GET  /competition-notifications/:id : get the "id" competitionNotification.
     *
     * @param id the id of the competitionNotificationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the competitionNotificationDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompetitionNotificationDTO> getCompetitionNotification(@PathVariable Long id) {
        log.debug("REST request to get CompetitionNotification : {}", id);
        CompetitionNotificationDTO competitionNotificationDTO = competitionNotificationService.findOne(id);
        return Optional.ofNullable(competitionNotificationDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /competition-notifications/:id : delete the "id" competitionNotification.
     *
     * @param id the id of the competitionNotificationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCompetitionNotification(@PathVariable Long id) {
        log.debug("REST request to delete CompetitionNotification : {}", id);
        competitionNotificationService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("competitionNotification", id.toString()))
            .build();
    }

    @RequestMapping(value = "/available",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public ResponseEntity<List<CompetitionNotificationDTO>> getAvailableCompetitionNotifications()
        throws URISyntaxException {
        log.debug("REST request to get a competition notifications for schedule");
        List<CompetitionNotificationDTO> competitionNotifications = competitionNotificationService.findVisibleForSchedule();
        return new ResponseEntity<>(competitionNotifications, HttpStatus.OK);
    }

}
