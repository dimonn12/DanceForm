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
        dto.setFullName(trimIfNull(entity.getFullName()));
        dto.setContentContentType(trimIfNull(entity.getContentContentType()));
        dto.setExternalPath(trimIfNull(entity.getExternalPath()));
        dto.setPath(trimIfNull(entity.getPath()));
        dto.setUploadedBy(trimIfNull(entity.getUploadedBy()));
        dto.setUploadedDate(entity.getUploadedDate());
        return dto;
    }

    @Override
    protected UploadedDocument convertDtoToEntity(UploadedDocumentDTO dto, UploadedDocument entity) {
        entity.setExternalPath(trimIfNull(dto.getExternalPath()));
        entity.setPath(trimIfNull(dto.getPath()));
        entity.setContentContentType(trimIfNull(dto.getContentContentType()));
        entity.setFullName(trimIfNull(dto.getFullName()));
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
