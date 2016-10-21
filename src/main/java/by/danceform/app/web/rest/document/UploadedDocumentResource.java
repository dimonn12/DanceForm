package by.danceform.app.web.rest.document;

import by.danceform.app.dto.document.UploadedDocumentDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.document.UploadedDocumentService;
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
 * REST controller for managing UploadedDocument.
 */
@RestController
@RequestMapping("/api/uploaded-documents")
@Secured(AuthoritiesConstants.ADMIN)
public class UploadedDocumentResource {

    private final Logger log = LoggerFactory.getLogger(UploadedDocumentResource.class);

    @Inject
    private UploadedDocumentService uploadedDocumentService;

    /**
     * POST  /uploaded-documents : Create a new uploadedDocument.
     *
     * @param uploadedDocumentDTO the uploadedDocumentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uploadedDocumentDTO, or with status 400 (Bad Request) if the uploadedDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UploadedDocumentDTO> createUploadedDocument(
        @Valid @RequestBody UploadedDocumentDTO uploadedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save UploadedDocument : {}", uploadedDocumentDTO);
        if(uploadedDocumentDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("uploadedDocument",
                    "idexists",
                    "A new uploadedDocument cannot already have an ID"))
                .body(null);
        }
        UploadedDocumentDTO result = uploadedDocumentService.save(uploadedDocumentDTO);
        return ResponseEntity.created(new URI("/api/uploaded-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("uploadedDocument", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uploaded-documents : Updates an existing uploadedDocument.
     *
     * @param uploadedDocumentDTO the uploadedDocumentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uploadedDocumentDTO,
     * or with status 400 (Bad Request) if the uploadedDocumentDTO is not valid,
     * or with status 500 (Internal Server Error) if the uploadedDocumentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UploadedDocumentDTO> updateUploadedDocument(
        @Valid @RequestBody UploadedDocumentDTO uploadedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update UploadedDocument : {}", uploadedDocumentDTO);
        if(uploadedDocumentDTO.getId() == null) {
            return createUploadedDocument(uploadedDocumentDTO);
        }
        UploadedDocumentDTO result = uploadedDocumentService.save(uploadedDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("uploadedDocument", uploadedDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uploaded-documents : get all the uploadedDocuments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uploadedDocuments in body
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UploadedDocumentDTO> getAllUploadedDocuments() {
        log.debug("REST request to get all UploadedDocuments");
        return uploadedDocumentService.findAll();
    }

    /**
     * GET  /uploaded-documents/:id : get the "id" uploadedDocument.
     *
     * @param id the id of the uploadedDocumentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uploadedDocumentDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured({ AuthoritiesConstants.USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.ANONYMOUS })
    public ResponseEntity<UploadedDocumentDTO> getUploadedDocument(@PathVariable Long id) {
        log.debug("REST request to get UploadedDocument : {}", id);
        UploadedDocumentDTO uploadedDocumentDTO = uploadedDocumentService.findOne(id);
        return Optional.ofNullable(uploadedDocumentDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /uploaded-documents/:id : delete the "id" uploadedDocument.
     *
     * @param id the id of the uploadedDocumentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUploadedDocument(@PathVariable Long id) {
        log.debug("REST request to delete UploadedDocument : {}", id);
        uploadedDocumentService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("uploadedDocument", id.toString()))
            .build();
    }

}
