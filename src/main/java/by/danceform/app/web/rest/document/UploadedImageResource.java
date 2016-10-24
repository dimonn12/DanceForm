package by.danceform.app.web.rest.document;

import by.danceform.app.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import by.danceform.app.service.document.UploadedImageService;
import by.danceform.app.web.rest.util.HeaderUtil;
import by.danceform.app.web.rest.util.PaginationUtil;
import by.danceform.app.dto.document.UploadedImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UploadedImage.
 */
@RestController
@RequestMapping("/api/config/uploaded-images")
@Secured(AuthoritiesConstants.ADMIN)
public class UploadedImageResource {

    private final Logger log = LoggerFactory.getLogger(UploadedImageResource.class);

    @Inject
    private UploadedImageService uploadedImageService;

    /**
     * POST  /uploaded-images : Create a new uploadedImage.
     *
     * @param uploadedImageDTO the uploadedImageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uploadedImageDTO, or with status 400 (Bad Request) if the uploadedImage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UploadedImageDTO> createUploadedImage(@Valid @RequestBody UploadedImageDTO uploadedImageDTO)
        throws URISyntaxException {
        log.debug("REST request to save UploadedImage : {}", uploadedImageDTO);
        if(uploadedImageDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("uploadedImage",
                    "idexists",
                    "A new uploadedImage cannot already have an ID"))
                .body(null);
        }
        UploadedImageDTO result = uploadedImageService.save(uploadedImageDTO);
        return ResponseEntity.created(new URI("/api/uploaded-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("uploadedImage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uploaded-images : Updates an existing uploadedImage.
     *
     * @param uploadedImageDTO the uploadedImageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uploadedImageDTO,
     * or with status 400 (Bad Request) if the uploadedImageDTO is not valid,
     * or with status 500 (Internal Server Error) if the uploadedImageDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UploadedImageDTO> updateUploadedImage(@Valid @RequestBody UploadedImageDTO uploadedImageDTO)
        throws URISyntaxException {
        log.debug("REST request to update UploadedImage : {}", uploadedImageDTO);
        if(uploadedImageDTO.getId() == null) {
            return createUploadedImage(uploadedImageDTO);
        }
        UploadedImageDTO result = uploadedImageService.save(uploadedImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("uploadedImage", uploadedImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uploaded-images : get all the uploadedImages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uploadedImages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UploadedImageDTO>> getAllUploadedImages(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of UploadedImages");
        Page<UploadedImageDTO> page = uploadedImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/uploaded-images");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /uploaded-images/:id : get the "id" uploadedImage.
     *
     * @param id the id of the uploadedImageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uploadedImageDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UploadedImageDTO> getUploadedImage(@PathVariable Long id) {
        log.debug("REST request to get UploadedImage : {}", id);
        UploadedImageDTO uploadedImageDTO = uploadedImageService.findOne(id);
        return Optional.ofNullable(uploadedImageDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /uploaded-images/:id : delete the "id" uploadedImage.
     *
     * @param id the id of the uploadedImageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUploadedImage(@PathVariable Long id) {
        log.debug("REST request to delete UploadedImage : {}", id);
        uploadedImageService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("uploadedImage", id.toString()))
            .build();
    }

}
