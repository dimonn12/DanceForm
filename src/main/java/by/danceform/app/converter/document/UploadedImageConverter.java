package by.danceform.app.converter.document;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.domain.document.UploadedImage;
import by.danceform.app.dto.document.UploadedDocumentDTO;
import by.danceform.app.dto.document.UploadedImageDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry_Shanko on 10/21/2016.
 */
@Component("uploadedImageConverter")
public class UploadedImageConverter extends AbstractConverter<UploadedImage, UploadedImageDTO, Long> {

    @Override
    protected UploadedImageDTO convertEntityToDto(UploadedImage entity, UploadedImageDTO dto) {
        dto.setFullName(entity.getFullName());
        dto.setContentContentType(entity.getContentContentType());
        dto.setExternalPath(entity.getExternalPath());
        dto.setPath(entity.getPath());
        dto.setUploadedBy(entity.getUploadedBy());
        dto.setUploadedDate(entity.getUploadedDate());
        return dto;
    }

    @Override
    protected UploadedImage convertDtoToEntity(UploadedImageDTO dto, UploadedImage entity) {
        entity.setExternalPath(dto.getExternalPath());
        entity.setPath(dto.getPath());
        entity.setContentContentType(dto.getContentContentType());
        entity.setFullName(dto.getFullName());
        return entity;
    }

    @Override
    protected UploadedImageDTO getNewDTO() {
        return new UploadedImageDTO();
    }

    @Override
    protected UploadedImage getNewEntity() {
        return new UploadedImage();
    }
}
