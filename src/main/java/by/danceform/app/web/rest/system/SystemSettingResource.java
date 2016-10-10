package by.danceform.app.web.rest.system;

import by.danceform.app.dto.system.SystemSettingDTO;
import by.danceform.app.security.AuthoritiesConstants;
import by.danceform.app.service.system.SystemSettingService;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SystemSetting.
 */
@RestController
@RequestMapping("/api/system/system-settings")
@Secured(AuthoritiesConstants.ADMIN)
public class SystemSettingResource {

    private final Logger log = LoggerFactory.getLogger(SystemSettingResource.class);

    @Inject
    private SystemSettingService systemSettingService;

    /**
     * POST  /system-settings : Create a new systemSetting.
     *
     * @param systemSettingDTO the systemSettingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemSettingDTO, or with status 400 (Bad Request) if the systemSetting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SystemSettingDTO> createSystemSetting(@Valid @RequestBody SystemSettingDTO systemSettingDTO)
        throws URISyntaxException {
        log.debug("REST request to save SystemSetting : {}", systemSettingDTO);
        if(systemSettingDTO.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("systemSetting",
                    "idexists",
                    "A new systemSetting cannot already have an ID"))
                .body(null);
        }
        SystemSettingDTO result = systemSettingService.save(systemSettingDTO);
        return ResponseEntity.created(new URI("/api/system-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("systemSetting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-settings : Updates an existing systemSetting.
     *
     * @param systemSettingDTO the systemSettingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemSettingDTO,
     * or with status 400 (Bad Request) if the systemSettingDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemSettingDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SystemSettingDTO> updateSystemSetting(@Valid @RequestBody SystemSettingDTO systemSettingDTO)
        throws URISyntaxException {
        log.debug("REST request to update SystemSetting : {}", systemSettingDTO);
        if(systemSettingDTO.getId() == null) {
            return createSystemSetting(systemSettingDTO);
        }
        SystemSettingDTO result = systemSettingService.save(systemSettingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("systemSetting", systemSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-settings : get all the systemSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of systemSettings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SystemSettingDTO>> getAllSystemSettings(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get a page of SystemSettings");
        Page<SystemSettingDTO> page = systemSettingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /system-settings/:id : get the "id" systemSetting.
     *
     * @param id the id of the systemSettingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemSettingDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SystemSettingDTO> getSystemSetting(@PathVariable Long id) {
        log.debug("REST request to get SystemSetting : {}", id);
        SystemSettingDTO systemSettingDTO = systemSettingService.findOne(id);
        return Optional.ofNullable(systemSettingDTO)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /system-settings/:id : delete the "id" systemSetting.
     *
     * @param id the id of the systemSettingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/{id}",
                    method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSystemSetting(@PathVariable Long id) {
        log.debug("REST request to delete SystemSetting : {}", id);
        systemSettingService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("systemSetting", id.toString()))
            .build();
    }

}
