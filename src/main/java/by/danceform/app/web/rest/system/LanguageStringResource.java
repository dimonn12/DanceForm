package by.danceform.app.web.rest.system;

import by.danceform.app.dto.system.LanguageStringDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.system.LanguageStringService;
import by.danceform.app.web.rest.util.HeaderUtil;
import by.danceform.app.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LanguageString.
 */
@RestController
@RequestMapping("/api/system/language-strings")
@Secured(AuthoritiesConstants.ADMIN)
public class LanguageStringResource {

    private final Logger log = LoggerFactory.getLogger(LanguageStringResource.class);

    @Inject
    private LanguageStringService languageStringService;

    /**
     * POST  /language-strings : Create a new languageString.
     *
     * @param languageStringDTO the languageStringDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new languageStringDTO, or with status 400 (Bad Request) if the languageString has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LanguageStringDTO> createLanguageString(
        @Valid @RequestBody LanguageStringDTO languageStringDTO) throws URISyntaxException {
        log.debug("REST request to save LanguageString : {}", languageStringDTO);
        if(languageStringDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("languageString",
                    "idexists",
                    "A new languageString cannot already have an ID"))
                .body(null);
        }
        LanguageStringDTO result = languageStringService.save(languageStringDTO);
        return ResponseEntity.created(new URI("/api/system/language-strings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("languageString", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /language-strings : Updates an existing languageString.
     *
     * @param languageStringDTO the languageStringDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated languageStringDTO,
     * or with status 400 (Bad Request) if the languageStringDTO is not valid,
     * or with status 500 (Internal Server Error) if the languageStringDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
                    method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LanguageStringDTO> updateLanguageString(
        @Valid @RequestBody LanguageStringDTO languageStringDTO) throws URISyntaxException {
        log.debug("REST request to update LanguageString : {}", languageStringDTO);
        if(languageStringDTO.getId() == null) {
            return createLanguageString(languageStringDTO);
        }
        LanguageStringDTO result = languageStringService.save(languageStringDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("languageString", languageStringDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /language-strings : get all the languageStrings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of languageStrings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LanguageStringDTO>> getAllLanguageStrings(HttpServletRequest request, Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of LanguageStrings");
        Page<LanguageStringDTO> page = languageStringService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system/language-strings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /language-strings/:id : get the "id" languageString.
     *
     * @param id the id of the languageStringDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the languageStringDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LanguageStringDTO> getLanguageString(@PathVariable Long id) {
        log.debug("REST request to get LanguageString : {}", id);
        LanguageStringDTO languageStringDTO = languageStringService.findOne(id);
        return Optional.ofNullable(languageStringDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /language-strings/:id : delete the "id" languageString.
     *
     * @param id the id of the languageStringDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLanguageString(@PathVariable Long id) {
        log.debug("REST request to delete LanguageString : {}", id);
        languageStringService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("languageString", id.toString()))
            .build();
    }

}
