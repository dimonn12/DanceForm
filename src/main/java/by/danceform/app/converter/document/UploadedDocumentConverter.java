package by.danceform.app.converter.document;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.dto.document.UploadedDocumentDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry_Shanko on 10/21/2016.
 */
@Component("uploadedDocumentConverter")
public class UploadedDocumentConverter extends AbstractConverter<UploadedDocument, UploadedDocumentDTO, Long> {

    @Override
    protected UploadedDocumentDTO convertEntityToDto(UploadedDocument entity, UploadedDocumentDTO dto) {
        dto.setFullName(entity.getFullName());
        dto.setContentContentType(entity.getContentContentType());
        dto.setExternalPath(entity.getExternalPath());
        dto.setPath(entity.getPath());
        dto.setUploadedBy(entity.getUploadedBy());
        dto.setUploadedDate(entity.getUploadedDate());
        return dto;
    }

    @Override
    protected UploadedDocument convertDtoToEntity(UploadedDocumentDTO dto, UploadedDocument entity) {
        entity.setExternalPath(dto.getExternalPath());
        entity.setPath(dto.getPath());
        entity.setContentContentType(dto.getContentContentType());
        entity.setFullName(dto.getFullName());
        return entity;
    }

    @Override
    protected UploadedDocumentDTO getNewDTO() {
        return new UploadedDocumentDTO();
    }

    @Override
    protected UploadedDocument getNewEntity() {
        return new UploadedDocument();
    }
}
