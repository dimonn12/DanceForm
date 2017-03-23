package by.danceform.app.web.rest.competition;

import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.competition.CompetitionCategoryService;
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
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing CompetitionCategory.
 */
@RestController
@RequestMapping("/api/competition/{competitionId}/competition-categories")
@Secured(AuthoritiesConstants.ADMIN)
public class CompetitionCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionCategoryResource.class);

    @Inject
    private CompetitionCategoryService competitionCategoryService;

    /**
     * POST  /competition-categories : Create a new competitionCategory.
     *
     * @param competitionCategoryDTO the competitionCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new competitionCategoryDTO, or with status 400 (Bad Request) if the competitionCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompetitionCategoryDTO> createCompetitionCategory(
        @Valid @RequestBody CompetitionCategoryDTO competitionCategoryDTO, @PathVariable Long competitionId)
        throws URISyntaxException {
        log.debug("REST request to save CompetitionCategory : {}", competitionCategoryDTO);
        if(competitionCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("competitionCategory",
                    "idexists",
                    "A new competitionCategory cannot already have an ID"))
                .body(null);
        }
        competitionCategoryDTO.setCompetitionId(competitionId);
        CompetitionCategoryDTO result = competitionCategoryService.save(competitionCategoryDTO);
        return ResponseEntity.created(new URI("/api/competition-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("competitionCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /competition-categories : Updates an existing competitionCategory.
     *
     * @param competitionCategoryDTO the competitionCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated competitionCategoryDTO,
     * or with status 400 (Bad Request) if the competitionCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the competitionCategoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompetitionCategoryDTO> updateCompetitionCategory(
        @Valid @RequestBody CompetitionCategoryDTO competitionCategoryDTO, @PathVariable Long competitionId)
        throws URISyntaxException {
        log.debug("REST request to update CompetitionCategory : {}", competitionCategoryDTO);
        if(competitionCategoryDTO.getId() == null) {
            return createCompetitionCategory(competitionCategoryDTO, competitionId);
        }
        competitionCategoryDTO.setCompetitionId(competitionId);
        CompetitionCategoryDTO result = competitionCategoryService.save(competitionCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("competitionCategory",
                competitionCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /competition-categories : get all the competitionCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of competitionCategories in body
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public List<CompetitionCategoryDTO> getAllCompetitionCategories(@PathVariable Long competitionId) {
        log.debug("REST request to get all CompetitionCategories");
        return competitionCategoryService.findByCompetitionId(competitionId);
    }

    /**
     * GET  /competition-categories : get all the competitionCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of competitionCategories in body
     */
    @RequestMapping(value = "/available",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public List<CompetitionCategoryDTO> getAllAvailableCompetitionCategories(@PathVariable Long competitionId,
                                                                             @RequestParam(name = "soloCouple",
                                                                                           required = false)
                                                                                 Boolean isSoloCouple,
                                                                             @RequestParam(name = "hobbyCouple",
                                                                                           required = false)
                                                                                 Boolean isHobbyCouple,
                                                                             @RequestBody
                                                                                 RegisteredCoupleDTO registeredCoupleDTO) {
        log.debug("REST request to get all CompetitionCategories");
        return competitionCategoryService.findAvailableByCompetitionId(registeredCoupleDTO,
            competitionId,
            isSoloCouple,
            isHobbyCouple);
    }

    /**
     * GET  /competition-categories/:id : get the "id" competitionCategory.
     *
     * @param id the id of the competitionCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the competitionCategoryDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public ResponseEntity<CompetitionCategoryDTO> getCompetitionCategory(@PathVariable Long competitionId,
                                                                         @PathVariable Long id) {
        log.debug("REST request to get CompetitionCategory : {}", id);
        CompetitionCategoryDTO competitionCategoryDTO = competitionCategoryService.findOne(id);
        if(!Objects.equals(competitionCategoryDTO.getCompetitionId(), competitionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return Optional.ofNullable(competitionCategoryDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /competition-categories/:id : delete the "id" competitionCategory.
     *
     * @param id the id of the competitionCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCompetitionCategory(@PathVariable Long competitionId, @PathVariable Long id) {
        log.debug("REST request to delete CompetitionCategory : {}", id);
        CompetitionCategoryDTO competitionCategoryDTO = competitionCategoryService.findOne(id);
        if(!Objects.equals(competitionCategoryDTO.getCompetitionId(), competitionId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        competitionCategoryService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("competitionCategory", id.toString()))
            .build();
    }

}
