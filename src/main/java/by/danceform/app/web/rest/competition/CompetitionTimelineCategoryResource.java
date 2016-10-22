package by.danceform.app.web.rest.competition;

import by.danceform.app.dto.competition.CompetitionCategoryWithDetailsDTO;
import by.danceform.app.security.AuthoritiesConstants;
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
import java.util.Objects;
import java.util.Optional;

/**
 * Created by dimonn12 on 08.10.2016.
 */
@RestController
@RequestMapping("/api/competition-timeline/competitions/{competitionId}/category")
@Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
public class CompetitionTimelineCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionTimelineCategoryResource.class);

    @Inject
    private CompetitionTimelineService competitionService;

    @RequestMapping(value = "/{categoryId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompetitionCategoryWithDetailsDTO> getCompetitionCategoryWithDetails(
        @PathVariable Long competitionId, @PathVariable Long categoryId) {
        log.debug("REST request to get competition with detai;s : {}", categoryId);
        CompetitionCategoryWithDetailsDTO competitionCategoryWithDetailsDTO = competitionService.findCategoryWithDetails(
            categoryId);
        return Optional.ofNullable(competitionCategoryWithDetailsDTO).map(result -> {
            if(!Objects.equals(result.getCompetitionId(), competitionId)) {
                return new ResponseEntity<CompetitionCategoryWithDetailsDTO>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
