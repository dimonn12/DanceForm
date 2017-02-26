package by.danceform.app.web.rest.document;

import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.dto.competition.CompetitionDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.competition.CompetitionService;
import by.danceform.app.service.document.UploadedDocumentService;
import by.danceform.app.web.rest.util.DownloadUtil;
import com.codahale.metrics.annotation.Timed;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dimonn12 on 24.10.2016.
 */
@RestController
@RequestMapping("/uploads")
@Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
public class DownloadResource {

    private final Logger log = LoggerFactory.getLogger(UploadedDocumentResource.class);

    @Inject
    private UploadedDocumentService uploadedDocumentService;

    @Inject
    private CompetitionService competitionService;

    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public HttpEntity<byte[]> download(@PathVariable("id") Long id) {
        log.debug("REST request to download file");
        UploadedDocument doc = uploadedDocumentService.findById(id);
        if(null != doc) {
            return DownloadUtil.download(doc);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/competition/{competitionId}/details",
                    method = RequestMethod.GET)
    @Timed
    public HttpEntity<byte[]> downloadCompetitionDetails(@PathVariable("competitionId") Long competitionId) {
        log.debug("REST request to download file");
        CompetitionDTO competition = competitionService.findOne(competitionId);
        if(null != competition && competition.isVisible() && null != competition.getDetailsDocumentId()) {
            UploadedDocument doc = uploadedDocumentService.findById(competition.getDetailsDocumentId());
            if(null != doc) {
                return DownloadUtil.download(doc);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/competition/{competitionId}/banner",
                    method = RequestMethod.GET)
    @Timed
    public HttpEntity<byte[]> downloadCompetitionBanner(@PathVariable("competitionId") Long competitionId) {
        log.debug("REST request to download file");
        CompetitionDTO competition = competitionService.findOne(competitionId);
        if(null != competition && competition.isVisible() && null != competition.getBannerImageId()) {
            UploadedDocument doc = uploadedDocumentService.findById(competition.getBannerImageId());
            if(null != doc) {
                return DownloadUtil.download(doc);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
