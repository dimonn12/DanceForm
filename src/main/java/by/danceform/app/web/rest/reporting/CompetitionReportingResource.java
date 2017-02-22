package by.danceform.app.web.rest.reporting;

import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.reporting.CompetitionReportingService;
import by.danceform.app.web.rest.util.DownloadUtil;
import com.codahale.metrics.annotation.Timed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
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

/**
 * Created by USER on 22.02.2017.
 */
@RestController
@RequestMapping("/api/reports/competition")
@Secured({AuthoritiesConstants.ADMIN})
public class CompetitionReportingResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionReportingResource.class);

    @Inject
    private CompetitionReportingService competitionReportingService;

    @RequestMapping(value = "/{id}/registered-couples",
        method = RequestMethod.GET)
    @Timed
    public HttpEntity<byte[]> download(@PathVariable("id") Long competitionId, HttpServletResponse response) {
        log.debug("REST request to download registered couples report for competition with id=" + competitionId);
        Object doc = competitionReportingService.getCompetitionReport(competitionId);
        if (null != doc) {
            return DownloadUtil.download(response, null);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
