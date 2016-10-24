package by.danceform.app.service.document;

import by.danceform.app.converter.document.UploadedImageConverter;
import by.danceform.app.domain.document.UploadedImage;
import by.danceform.app.dto.document.UploadedImageDTO;
import by.danceform.app.repository.document.UploadedImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing UploadedImage.
 */
@Service
@Transactional
public class UploadedImageService {

    private final Logger log = LoggerFactory.getLogger(UploadedImageService.class);

    @Inject
    private UploadedImageRepository uploadedImageRepository;

    @Inject
    private UploadedImageConverter uploadedImageConverter;

    /**
     * Save a uploadedImage.
     *
     * @param uploadedImageDTO the entity to save
     * @return the persisted entity
     */
    public UploadedImageDTO save(UploadedImageDTO uploadedImageDTO) {
        log.debug("Request to save UploadedImage : {}", uploadedImageDTO);
        UploadedImage uploadedImage = uploadedImageConverter.convertToEntity(uploadedImageDTO);
        uploadedImage = uploadedImageRepository.save(uploadedImage);
        UploadedImageDTO result = uploadedImageConverter.convertToDto(uploadedImage);
        return result;
    }

    /**
     *  Get all the uploadedImages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UploadedImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UploadedImages");
        Page<UploadedImage> result = uploadedImageRepository.findAll(pageable);
        return result.map(uploadedImage -> uploadedImageConverter.convertToDto(uploadedImage));
    }

    /**
     *  Get one uploadedImage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UploadedImageDTO findOne(Long id) {
        log.debug("Request to get UploadedImage : {}", id);
        UploadedImage uploadedImage = uploadedImageRepository.findOne(id);
        UploadedImageDTO uploadedImageDTO = uploadedImageConverter.convertToDto(uploadedImage);
        return uploadedImageDTO;
    }

    /**
     *  Delete the  uploadedImage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UploadedImage : {}", id);
        uploadedImageRepository.delete(id);
    }
}
