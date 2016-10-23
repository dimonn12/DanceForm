package by.danceform.app.service.document;

import by.danceform.app.converter.document.UploadedDocumentConverter;
import by.danceform.app.domain.competition.Competition;
import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.dto.document.AttachedDocumentDTO;
import by.danceform.app.dto.document.UploadedDocumentDTO;
import by.danceform.app.repository.competition.CompetitionRepository;
import by.danceform.app.repository.document.UploadedDocumentRepository;
import by.danceform.app.service.util.UploadUtil;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UploadedDocument.
 */
@Service
@Transactional
public class UploadedDocumentService {

    private final Logger log = LoggerFactory.getLogger(UploadedDocumentService.class);

    @Inject
    private UploadedDocumentRepository uploadedDocumentRepository;

    @Inject
    private UploadedDocumentConverter uploadedDocumentConverter;

    @Inject
    private CompetitionRepository competitionRepository;

    @Inject
    private UploadUtil uploadUtil;

    /**
     * Save a uploadedDocument.
     *
     * @param uploadedDocumentDTO the entity to save
     * @return the persisted entity
     */
    public UploadedDocumentDTO save(HttpServletRequest request, AttachedDocumentDTO uploadedDocumentDTO) {
        log.debug("Request to save UploadedDocument : {}", uploadedDocumentDTO);
        UploadedDocument uploadedDocument = uploadUtil.uploadFile(request, uploadedDocumentDTO);
        uploadedDocument = uploadedDocumentRepository.save(uploadedDocument);
        UploadedDocumentDTO result = uploadedDocumentConverter.convertToDto(uploadedDocument);
        return result;
    }

    public UploadedDocumentDTO uploadCompetitionDetailsDocument(HttpServletRequest request,
                                                                AttachedDocumentDTO attachedDocumentDTO) {
        log.debug("Request to save UploadedDocument : {}", attachedDocumentDTO);
        UploadedDocument uploadedDocument = uploadUtil.uploadFile(request, attachedDocumentDTO);
        uploadedDocument = uploadedDocumentRepository.save(uploadedDocument);
        Competition comp = competitionRepository.findOne(attachedDocumentDTO.getEntityId());
        comp.setDetailsDocumentId(uploadedDocument.getId());
        competitionRepository.save(comp);
        UploadedDocumentDTO result = uploadedDocumentConverter.convertToDto(uploadedDocument);
        return result;
    }

    public UploadedDocument findById(Long id) {
        return uploadedDocumentRepository.findOne(id);
    }

    /**
     * Get all the uploadedDocuments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UploadedDocumentDTO> findAll() {
        log.debug("Request to get all UploadedDocuments");
        List<UploadedDocumentDTO> result = uploadedDocumentRepository.findAll()
            .stream()
            .map(uploadedDocumentConverter::convertToDto)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     * Get one uploadedDocument by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UploadedDocumentDTO findOne(Long id) {
        log.debug("Request to get UploadedDocument : {}", id);
        UploadedDocument uploadedDocument = uploadedDocumentRepository.findOne(id);
        UploadedDocumentDTO uploadedDocumentDTO = uploadedDocumentConverter.convertToDto(uploadedDocument);
        return uploadedDocumentDTO;
    }

    /**
     * Delete the  uploadedDocument by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UploadedDocument : {}", id);
        //competitionRepository.updateDocumentAttachment(id);
        uploadedDocumentRepository.delete(id);
    }
}
