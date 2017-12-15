package by.danceform.app.web.rest.reporting;

import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.reporting.CompetitionReportingService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by USER on 22.02.2017.
 */
@RestController
@RequestMapping("/api/reports/competition")
@Secured({ AuthoritiesConstants.ADMIN })
public class CompetitionReportingResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionReportingResource.class);

    @Inject
    private CompetitionReportingService competitionReportingService;

    @RequestMapping(value = "/{id}/registered-couples", method = RequestMethod.GET)
    @Timed
    public HttpEntity<byte[]> download(@PathVariable("id") Long competitionId, HttpServletRequest request) {
        log.debug("REST request to download registered couples report for competition with id=" + competitionId);
        try {
            HttpEntity<byte[]> result = competitionReportingService.getCompetitionReport(request, competitionId);
            if(null != result) {
                return result;
            }
        } catch(IOException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
