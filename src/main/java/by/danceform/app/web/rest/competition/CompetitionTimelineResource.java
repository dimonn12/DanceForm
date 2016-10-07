package by.danceform.app.web.rest.competition;

import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.service.competition.CompetitionService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by dimonn12 on 08.10.2016.
 */
@RestController
@RequestMapping("/api/competition-timeline")
public class CompetitionTimelineResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionTimelineResource.class);

    @Inject
    private CompetitionService competitionService;

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
        List<CompetitionDTO> competitions = competitionService.findVisible();
        return new ResponseEntity<>(competitions, HttpStatus.OK);
    }
}
