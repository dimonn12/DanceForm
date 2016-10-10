package by.danceform.app.web.rest.competition;

import by.danceform.app.domain.competition.CompetitionCategoryWithDetails;
import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.dto.competition.CompetitionWithDetailsDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.competition.CompetitionService;
import by.danceform.app.service.competition.CompetitionTimelineService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by dimonn12 on 08.10.2016.
 */
@RestController
@RequestMapping("/api/competition-timeline")
@Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
public class CompetitionTimelineResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionTimelineResource.class);

    @Inject
    private CompetitionTimelineService competitionService;

    /**
     * GET  /competition-timeline/competitions : get all visible competitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of competitions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/competitions",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CompetitionDTO>> getAllCompetitions() throws URISyntaxException {
        log.debug("REST request to get a competitions for timeline");
        List<CompetitionDTO> competitions = competitionService.findForTimeline();
        return new ResponseEntity<>(competitions, HttpStatus.OK);
    }

    @RequestMapping(value = "/competitions/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompetitionWithDetailsDTO> getCompetitionWithDetails(@PathVariable Long id) {
        log.debug("REST request to get competition with detai;s : {}", id);
        CompetitionWithDetailsDTO competitionDTO = competitionService.findWithDetails(id);
        return Optional.ofNullable(competitionDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
